/**  
 * @Title: DataSourceBeanFactory.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-02 11:36:54 
 */
package com.aijava.core.dal.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.aijava.core.dal.datasource.entity.BaseDataSource;
import com.aijava.core.dal.datasource.router.MultiDataSourceRouter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @ClassName: DataSourceBeanFactory
 * @Description:数据源构建工厂
 * @author xiegr
 * @date 2021-12-02 11:36:54
 */
@Component
@SuppressWarnings("unchecked")
public class DataSourceBeanFactory implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceBeanFactory.class);

	/**
	 * 默认的用户名
	 */
	private String defaultDBUser;

	/**
	 * 默认密码
	 */
	private String defaultDBPassword;

	/**
	 * 读取配置文件
	 */
	@Resource
	private Map<String, Object> databasesMap;

	/**
	 * 保存所有DataSource的bean对象
	 */
	private Map<Object, Object> targetDataSources = Maps.newLinkedHashMap();

	/**
	 * BeanDefinitionBuilder为spring框架提供的创建bean的工具，等同于在xml配置中注入bean
	 */
	private BeanDefinitionBuilder dynamicDataSourceBean;

	/**
	 * 当前的类通过spring注入，获取当前类型spring上下文，从而向spring的bean工厂注册datasource、sqlsessionfactory等bean，从而建立数据库连接池
	 */
	private ConfigurableApplicationContext context;

	// 默认数据源
	private final static String DEFAULT_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

	/**
	 * 扫描包
	 */
	@Value(value = "${spring.mybatis.aliases-package:com.cig.*.pojo}")
	private String aliasesPackage;

	/**
	 * @param targetDataResources the targetDataResources to set
	 */
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(ConfigurableApplicationContext context) {
		this.context = context;
	}

	/**
	 * @Title: setApplicationContext
	 * @Description: TODO(描述)
	 * @param applicationContext
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 * @author xiegr
	 * @date 2021-12-03 11:27:09
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (ConfigurableApplicationContext) applicationContext;
	}

	@PostConstruct
	public void init() throws Exception {
		// 解析数据库连接配置
		decodeDatabases();

		// 解析配置，“是否只在从节点读取，不在master读取”
		decodeReadOnlyInSlave();

		// 解析排除sql校验的statement
		decodeCheckSqlExclude();
	}

	/*
	 * 解析数据库连接配置，并注入bean、创建数据库连接
	 */
	private void decodeDatabases() throws Exception {

		// 数据库列表
		Map<String, Object> databases = (Map<String, Object>) databasesMap.get("datasources");

		// 数据源列表
		List<BaseDataSource> dataSourceList = Lists.newArrayList();
		/**
		 * 解析数据库配置
		 */
		initDataSourceConfig(dataSourceList, databases);

		/**
		 * 注入所有数据源的bean
		 */
		buildDynamicDataSourceBean(dataSourceList);

		/**
		 * sessionFactoryBean
		 */
		buildSqlSessionFactoryBean();

		/**
		 * 事务
		 */
		buildTransactionManagerBean();

		/**
		 * 事务模板
		 */
		buildTransactionTemplateBean();

	}

	/**
	 * 
	 * @Title: buildDynDataSourceBean
	 * @Description:注入所有数据源的bean
	 * @param dataSources
	 * @author xiegr
	 * @date 2021-12-02 09:17:26
	 */
	private void buildDynamicDataSourceBean(List<BaseDataSource> dataSourceList) {

		dynamicDataSourceBean = BeanDefinitionBuilder.genericBeanDefinition(DynamicDataSource.class);
		String beanId = "dynamicDataSource";
		dynamicDataSourceBean.getBeanDefinition().setAttribute("id", beanId);
		/**
		 * 创建数据源bean，创建数据库连接池
		 */
		for (BaseDataSource bds : dataSourceList) {
			DataSource ds = buildDataSource(bds);
			targetDataSources.put(bds.getBeanId(), ds);
			if (bds.isDefault()) {
				dynamicDataSourceBean.addPropertyReference("defaultTargetDataSource", bds.getBeanId());
			}
		}
		/**
		 * 向spring的AbstractRoutingDataSource注入所有的数据源
		 */
		dynamicDataSourceBean.addPropertyValue("targetDataSources", targetDataSources);
		/**
		 * 向spring工厂注册dynamicDataSource，可以被其他bean使用
		 */
		this.registerBean(beanId, dynamicDataSourceBean.getBeanDefinition());
	}

	/**
	 * 创建sqlsessionFactory，并把dynamicDataSource和mapper文件路径
	 */
	private void buildSqlSessionFactoryBean() {
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildSqlSessionFactoryBean() begin");
		/**
		 * 代替spring中以下配置
		 * 
		 * <pre>
		 <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		 <property name="dataSource" ref="dynamicDataSource"/>
		 <property name="mapperLocations" value=
		"classpath*:META-INF/mybatis/*.xml"></property>
		 </bean>
		 * </pre>
		 */
		
		//BeanDefinitionBuilder sqlSessionFactoryBean = BeanDefinitionBuilder.rootBeanDefinition("org.mybatis.spring.SqlSessionFactoryBean");
		BeanDefinitionBuilder sqlSessionFactoryBean =BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
		String beanId = "sqlSessionFactory";
		sqlSessionFactoryBean.getBeanDefinition().setAttribute("id", beanId);
		sqlSessionFactoryBean.addPropertyReference("dataSource", "dynamicDataSource");
		sqlSessionFactoryBean.addPropertyValue("mapperLocations", "classpath*:META-INF/mapper/**/*.xml");
		sqlSessionFactoryBean.addPropertyValue("configLocation", "classpath:META-INF/spring/mybatis-config.xml");
		sqlSessionFactoryBean.addPropertyValue("typeAliasesPackage", aliasesPackage);
		this.registerBean(beanId, sqlSessionFactoryBean.getBeanDefinition());
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildSqlSessionFactoryBean() end");
	}

	/**
	 * 事务管理
	 */
	private void buildTransactionManagerBean() {
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildTransactionManagerBean()	 begin");
		//BeanDefinitionBuilder dataSourceTransactionManagerBean = BeanDefinitionBuilder.rootBeanDefinition("org.springframework.jdbc.datasource.DataSourceTransactionManager");
		BeanDefinitionBuilder dataSourceTransactionManagerBean = BeanDefinitionBuilder.genericBeanDefinition(DataSourceTransactionManager.class);
		String beanId = "transactionManager";
		dataSourceTransactionManagerBean.getBeanDefinition().setAttribute("id", beanId);
		dataSourceTransactionManagerBean.addPropertyReference("dataSource", "dynamicDataSource");
		this.registerBean(beanId, dataSourceTransactionManagerBean.getBeanDefinition());
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildTransactionManagerBean() end");
	}

	/**
	 * 事务模板
	 */
	private void buildTransactionTemplateBean() {
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildTransactionTemplateBean() begin");
		//BeanDefinitionBuilder transactionTemplateBean = BeanDefinitionBuilder.rootBeanDefinition("org.springframework.transaction.support.TransactionTemplate");
		BeanDefinitionBuilder transactionTemplateBean = BeanDefinitionBuilder.genericBeanDefinition(TransactionTemplate.class);
		String beanId = "transactionTemplate";
		transactionTemplateBean.getBeanDefinition().setAttribute("id", beanId);
		transactionTemplateBean.addPropertyReference("transactionManager", "transactionManager");
		this.registerBean(beanId, transactionTemplateBean.getBeanDefinition());
		logger.info("method com.cig.core.dal.datasource.DataSourceBeanFactory.buildTransactionTemplateBean() end");
	}

	/**
	 * 构建datasource
	 * 
	 * @param ds
	 */
	/**
	 * public void buildDataSourceBean(BaseDataSource ds) { BeanDefinitionBuilder
	 * basicDataSourceBean =
	 * BeanDefinitionBuilder.rootBeanDefinition(DEFAULT_DATASOURCE);
	 * basicDataSourceBean.getBeanDefinition().setAttribute("id", ds.getBeanId());
	 * basicDataSourceBean.addPropertyValue("driverClassName",
	 * ds.getDriverClassName());
	 * 
	 * basicDataSourceBean.addPropertyValue("jdbcUrl", "jdbc:mysql://" +
	 * ds.getHost() + "/" + ds.getSchema() +
	 * "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true&useCursorFetch=true");
	 * basicDataSourceBean.addPropertyValue("username", ds.getUsername());
	 * basicDataSourceBean.addPropertyValue("password", ds.getPassword());
	 * basicDataSourceBean.addPropertyValue("readOnly", false);
	 * basicDataSourceBean.addPropertyValue("connectionTimeout",
	 * ds.getConnectionTimeout());
	 * basicDataSourceBean.addPropertyValue("idleTimeout", ds.getIdleTimeout());
	 * basicDataSourceBean.addPropertyValue("maximumPoolSize", ds.getMaxPoolSize());
	 * basicDataSourceBean.addPropertyValue("maxLifetime", ds.getMaxLifetime());
	 * basicDataSourceBean.addPropertyValue("minimumIdle", ds.getMinIdle());
	 * basicDataSourceBean.addPropertyValue("connectionTestQuery",
	 * ds.getConnectionTestQuery());
	 * basicDataSourceBean.addPropertyValue("poolName", ds.getPoolName());
	 * basicDataSourceBean.addPropertyValue("autoCommit", ds.isAutoCommit());
	 * this.registerBean(ds.getBeanId(), basicDataSourceBean.getBeanDefinition());
	 * targetDataSources.put(ds.getBeanId(), context.getBean(ds.getBeanId()));
	 * 
	 * if (ds.isDefault()) {
	 * dynamicDataSourceBean.addPropertyReference("defaultTargetDataSource",
	 * ds.getBeanId()); } }
	 **/
	
	/**
	 * 
	 * @Title: buildDataSourceBean
	 * @Description:构建数据源bean
	 * @param ds
	 * @author xiegr
	 * @date 2021-12-03 09:20:20
	 */
	private DataSource buildDataSource(BaseDataSource bds) {

		String url = "jdbc:mysql://" + bds.getHost() + "/" + bds.getSchema()
				+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true&useCursorFetch=true";
		DataSourceBuilder<?> dataSourceBuild = DataSourceBuilder.create().url(url)
				.driverClassName(bds.getDriverClassName()).username(bds.getUsername()).password(bds.getPassword());
		try {
			String type = bds.getType();
			if (type == null) {
				bds.setType(DEFAULT_DATASOURCE);
			}
			// 可以读取配置替换连接池
			Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(bds.getType());
			if (dataSourceType.getName().contains("HikariDataSource")) { // HikariDataSource,可以置换其他数据连接池
				HikariDataSource hds = dataSourceBuild.type(HikariDataSource.class).build();
				hds.setConnectionTestQuery(bds.getConnectionTestQuery());
				hds.setAutoCommit(bds.isAutoCommit());
				hds.setPoolName(bds.getPoolName());
				hds.setMinimumIdle(bds.getMinIdle());
				hds.setMaximumPoolSize(bds.getMaxPoolSize());
				hds.setConnectionTimeout(bds.getConnectionTimeout());
				hds.setMaxLifetime(bds.getMaxLifetime());
				hds.setIdleTimeout(bds.getIdleTimeout());
				hds.setValidationTimeout(bds.getValidationTimeout());
				return hds;
			}
			return dataSourceBuild.type(dataSourceType).build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dataSourceBuild.build();
	}

	/**
	 * 公共函数，向spring注入bean
	 *
	 * @param beanId
	 * @param beanDefinition
	 */
	private void registerBean(String beanName, BeanDefinition beanDefinition) {
		getBeanFactory().registerBeanDefinition(beanName, beanDefinition);
	}

	/**
	 * 从spring中删除bean
	 *
	 * @param beanId
	 */
	public void removeBean(String beanName) {
		getBeanFactory().removeBeanDefinition(beanName);
	}

	/**
	 * 
	 * @Title: getBeanFactory
	 * @Description:获取bean
	 * @return
	 * @author xiegr
	 * @date 2021-12-10 10:24:33
	 */
	private DefaultListableBeanFactory getBeanFactory() {
		//ConfigurableApplicationContext configurableApplicationContext = context;
		//return (DefaultListableBeanFactory) configurableApplicationContext.getAutowireCapableBeanFactory();
		return (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
	}

	/**
	 * 
	 * @Title: decodeReadOnlyInSlave
	 * @Description:解析配置，“是否只在从节点读取，不在master读取”
	 * @author xiegr
	 * @date 2021-12-02 09:14:19
	 */
	private void decodeReadOnlyInSlave() {
		try {
			boolean readOnlyInSlave = (boolean) databasesMap.get("readOnlyInSlave");
			logger.info("find readOnlyInSlave in database.yml, use value:{} ", readOnlyInSlave);
			MultiDataSourceRouter.setReadOnlyInSlave(readOnlyInSlave);
		} catch (Exception e) {
			// use default value：false 即 master和slave都读取,如果读取不到 “readOnlyInSlave”，会抛出异常
			logger.info("can not find readOnlyInSlave in database.yml, use default value:false ");
		}
	}

	/**
	 * 
	 * @Title: decodeCheckSqlExclude
	 * @Description: 解析 checkSqlExclude
	 * @author xiegr
	 * @date 2021-12-02 09:00:19
	 */
	private void decodeCheckSqlExclude() {

		try {
			/**
			 * sql 检验 排除sql 例如:
			 * 
			 * <pre>
			 *  checkSqlExclude:
			      - com.cig.dashboard.dal.account.AccountMapper.selectAllAccount
			 * </pre>
			 * 
			 */
			List<String> list = (List<String>) databasesMap.get("checkSqlExclude");
			if (list != null && list.size() > 0) {

				Map<String, String> checkSqlMap = new HashMap<String, String>();

				for (int i = 0; i < list.size(); i++) {
					checkSqlMap.put(list.get(i), "");
				}

				MultiDataSourceRouter.setCheckSqlExclude(checkSqlMap);
			}

		} catch (Exception e) {
			logger.info("can not find checkSqlExclude in database.yml ");
		}
	}

	/**
	 * @Title: initDataSourceConfig
	 * @Description: TODO(描述)
	 * @param dataSources
	 * @param databases
	 * @author xiegr
	 * @throws Exception
	 * @date 2021-12-02 11:46:46
	 */
	private void initDataSourceConfig(List<BaseDataSource> dataSourceList, Map<String, Object> databases)
			throws Exception {

		Map<String, String[]> dbClusterSet = new LinkedHashMap<>();

		// dao与schema映射
		Map<String, String> daoDbClusterMap = new LinkedHashMap<>();

		MultiDataSourceRouter.setSchemaToDataSourceBeanIdsMap(dbClusterSet);
		// 映射Dao与Schema关系
		MultiDataSourceRouter.setDaoToSchemaMap(daoDbClusterMap);
		/**
		 * 遍历所有的数据库集群，比如 cig_dashboard、cig_product datasources:
		 * <p>
		 * cig_dashboard:---- 数据库集群 servers:192.168.1.2:3306,192.168.1.3:3306
		 * ---数据库集群中的每个数据源 cig_product:---数据库集群
		 * servers:192.168.1.5:3306,192.168.1.6:3306 --- 数据库集群中的每个数据源
		 * </p>
		 */
		int databaseIndex = 0;

		if (databases == null || databases.size() == 0) {
			logger.error("databases [{}] is null!!", databases);
			throw new RuntimeException("databases [" + databases + "] is null!!");
		}
		for (Map.Entry<String, Object> dbentry : databases.entrySet()) {

			String schema = StringUtils.trim(dbentry.getKey());
			Map<String, Object> dbinfo = (Map<String, Object>) dbentry.getValue();
			// 在dbClusterSet中保存schema到DataSource的映射
			List<String> serverList = generaterServerArray(dbinfo, schema);
			if (CollectionUtils.isNotEmpty(serverList)) {
				// 数据源
				List<BaseDataSource> dataSourcesOfSchema = generateDataSource(dbinfo, serverList, schema);
				dataSourceList.addAll(dataSourcesOfSchema);
				schemaToDataSourceBeanIds(dataSourcesOfSchema, schema, dbClusterSet);
			}
			/**
			 * dao指定访问的数据库集群，即，dao访问的表，所存在的数据库 默认情况下，dao访问数据库时，使用默认的数据库集群
			 */
			List<String> daoList = (List<String>) dbinfo.get("daos");
			daoToSchema(daoDbClusterMap, daoList, schema);
			// 默认第一个数据库
			if (databaseIndex == 0) {
				MultiDataSourceRouter.setDefaultDBSchema(schema);
			}

			databaseIndex++;
		}
	}

	/**
	 * 
	 * @Title: generateDataSource
	 * @Description: 生成基础数据源list
	 * @param dbinfo
	 * @param serverList
	 * @param schema
	 * @return
	 * @throws Exception
	 * @author xiegr
	 * @date 2021-12-03 10:40:47
	 */
	public List<BaseDataSource> generateDataSource(Map<String, Object> dbinfo, List<String> serverList, String schema)
			throws Exception {
		List<BaseDataSource> baseDataSources = Lists.newArrayList();
		/** 处理用户名和密码，如果没有填，或者密码解密失败，则使用默认的密码 **/
		String username = String.valueOf(dbinfo.get("username"));
		String password = String.valueOf(dbinfo.get("password"));
		String driverClassName = String.valueOf(dbinfo.get("driverClassName"));
		String type = String.valueOf(dbinfo.get("type"));
		// 最小空闲
		Integer minIdle = (Integer) dbinfo.get("minimumIdle");
		// 最大链接数
		Integer maxPoolSize = (Integer) dbinfo.get("maximumPoolSize");
		// 连接池名称
		String poolName = String.valueOf(dbinfo.get("poolName"));
		// 处理用户名和密码, 如果乜有传，则获取一个默认的
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			username = this.defaultDBUser;
			password = this.defaultDBPassword;
		}
		username = StringUtils.trim(username);
		password = StringUtils.trim(password);
		/**
		 * 遍历数据库集群中的每个服务器 cig_dashboard 下面的 servers cig_product:
		 * servers:192.168.1.2:9980,192.168.1.3:3306
		 */
		for (int serverIndex = 0; serverIndex < serverList.size(); serverIndex++) {
			BaseDataSource bds = new BaseDataSource();
			bds.setSchema(schema);
			bds.setUsername(username);
			bds.setPassword(password);
			bds.setHost(StringUtils.trim(serverList.get(serverIndex)));
			bds.setBeanId(bds.getSchema() + "@" + bds.getHost().replace(":", "_"));
			if (poolName != null) {
				bds.setPoolName(poolName);
				logger.info("poolName set for datasource, {}:{}", bds.getSchema(), bds.getPoolName());
			}

			if (driverClassName != null) {
				bds.setDriverClassName(driverClassName);
				logger.info("driverClassName set for datasource, {}:{}", bds.getSchema(), bds.getDriverClassName());
			}
			if (type != null) {
				bds.setType(type);
				logger.info("type set for datasource, {}:{}", bds.getSchema(), bds.getType());
			}
			// 最大连接数支持可配, 默认最大20个链接
			if (minIdle != null) {
				bds.setMinIdle(minIdle);
				logger.info("min idle set for datasource, {}:{}", bds.getSchema(), bds.getMinIdle());
			}
			if (maxPoolSize != null) {
				bds.setMaxPoolSize(maxPoolSize);
				logger.info("max pool size set for datasource, {}:{}", bds.getSchema(), bds.getMaxPoolSize());
			}
			/**
			 * 保存所有的数据源，用于后续注入datasourceBean
			 */
			baseDataSources.add(bds);
		}
		return baseDataSources;

	}

	/**
	 * 
	 * @Title: schemaToDataSourceBeanIds
	 * @Description:
	 * @param dataSourcesOfSchema
	 * @param schema
	 * @param dbClusterSet
	 * @author xiegr
	 * @date 2021-12-02 09:19:59
	 */
	private void schemaToDataSourceBeanIds(List<BaseDataSource> dataSourcesOfSchema, String schema,
			Map<String, String[]> dbClusterSet) {
		List<String> dataSourceBeanList = new ArrayList<>();
		for (BaseDataSource ds : dataSourcesOfSchema) {
			/**
			 * 拼装DbCluster中数据源，比如 cig_dashboard 数据库为一个集群，每个集群中有master和slave多个数据源
			 * <entry key="dashboardDbCluster" value=
			 * "dashboardMasterDataSource,dashboardSlave1DataSource,dashboardSlave2DataSource"/>
			 */
			dataSourceBeanList.add(ds.getBeanId());
		}
		/**
		 * 保存每个数据库集群对应的数据源列表，数据库访问时，选择路由时使用，第一个默认为master可写
		 */
		dbClusterSet.put(schema, dataSourceBeanList.toArray(new String[dataSourceBeanList.size()]));
	}

	/**
	 * 
	 * @Title: generaterServerArr
	 * @Description: 提出services
	 * @param dbinfo
	 * @param schema
	 * @return
	 * @throws Exception
	 * @author xiegr
	 * @date 2021-12-02 02:05:35
	 */
	private List<String> generaterServerArray(Map<String, Object> dbinfo, String schema) throws Exception {
		List<String> serverArray = (List<String>) dbinfo.get("servers");
		if (CollectionUtils.isEmpty(serverArray)) {
			throw new RuntimeException("not set servers in database.yml!");
		}
		return serverArray;
	}

	/**
	 * 
	 * @Title: daoToSchema
	 * @Description: 根据dao找到schema
	 * @param daoDbClusterMap
	 * @param daoList
	 * @param schema
	 * @author xiegr
	 * @date 2021-12-02 02:33:28
	 */
	private void daoToSchema(Map<String, String> daoDbClusterMap, List<String> daoList, String schema) {
		if (daoList != null && daoList.size() > 0) {
			for (String dao : daoList) {
				daoDbClusterMap.put(dao, schema);
			}
		}
	}

	// 销毁对象
	@PreDestroy
	public void destroy() {
		logger.info("cig DataSourceBeanFactory destory......");
	}
}
