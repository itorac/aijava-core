/**  
 * @Title: MultiDataSourceRouter.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-01 05:06:38 
 */
package com.aijava.core.dal.datasource.router;

import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aijava.core.dal.datasource.helpers.DatabaseForce;
import com.google.common.collect.Maps;

/**
 * @ClassName: MultiDataSourceRouter
 * @Description:多数据源路由
 * @author xiegr
 * @date 2021-12-01 05:06:38
 */
public class MultiDataSourceRouter {

	// 调试日志
	private static final Logger logger = LoggerFactory.getLogger(MultiDataSourceRouter.class);

	/**
	 * 当前数据源ThreadLocal
	 */
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<>();

	/**
	 * schema与datasource bean id的对应关系
	 */
	private volatile static Map<String, String[]> schemaToDataSourceBeanIdsMap;

	/**
	 * mapper与schema的对应关系
	 */
	private static Map<String, String> mapperToSchemaMap=Maps.newHashMap();
	
	/**
	 * 默认数据库Schema
	 */
	private static String defaultDBSchema;

	/**
	 * mybatis配置文件中，database cluster 默认第一个 datasource为master
	 * 
	 * 默认第一个数据库为主库
	 * 
	 */
	private static final int MASTER_INDEX = 0;

	/**
	 * dao中数据库 statement命名，约定为 select* insert* update*
	 */
	public static final Pattern select = Pattern.compile("^select.*");

	/**
	 * 读操作是否只在slave中
	 */
	private static boolean readOnlyInSlave = false;

	/**
	 * sql 检验 排除sql 例如:
	 * 
	 * <pre>
	 *  checkSqlExclude:
	      - com.cig.dashboard.dal.account.AccountMapper.selectAllAccount
	 * </pre>
	 * 
	 */
	private static Map<String, String> checkSqlExclude = Maps.newHashMap();

	/**
	 * 数据源路由选择
	 *
	 * @param mapperNamespace Mapping文件中的DAO名称
	 * @param statementId     数据库操作名称
	 * @return
	 */
	public static String router(String mapperNamespace, String statementId) {

		// 获取Dao找到schema
		String schema = getSchemaByDao(mapperNamespace);
		if (schema == null) {
			// 如果为空就是默认数据源
			schema = getDefaultDBSchema();
		}
		// 选择schema找到数据源bean list
		String[] dataSourceList = getDataSourceArrayBySchema(schema);
		if (dataSourceList == null || dataSourceList.length == 0) {
			logger.error("schema[{}]'datasources is null!!", schema);
			throw new RuntimeException("schema[" + schema + "]'datasources is null!!");
		}

		// 默认使用数据库集群中第一个数据源 即master
		String dataSource = dataSourceList[MASTER_INDEX];
		// 如果为读操作，随机选择一个数据源
		if (select.matcher(statementId).matches()) {
			int index = randomIndex(dataSourceList.length);
			// 读操作是否只在slave上完成
			if (getReadOnlyInSlave()) {
				if (MASTER_INDEX == index && dataSourceList.length > 1) {
					index = (index + 1) % dataSourceList.length;
				}
			}

			// 在业务逻辑中, 强制走master
			if (Boolean.TRUE.equals(DatabaseForce.isForceMaster())) {
				index = MASTER_INDEX;
				logger.debug("force router datasource to master");
			}

			logger.debug("router datasource in cluster, size[{}], datasource index[{}] in dataSourceArr[{}]",
					dataSourceList.length, index, dataSourceList);

			dataSource = dataSourceList[index];
		}

		logger.debug("datasource router result is [{}], choose by dao[{}] & statementid[{}] in cluster[{}]", dataSource,
				mapperNamespace, statementId, schema);

		return dataSource;
	}

	/**
	 * 
	 * @Title: specifyDataSource
	 * @Description:当前数据源
	 * @param dbCluster
	 * @author xiegr
	 * @date 2021-12-01 05:52:35
	 */
	public static void specifyDataSource(String dataSource) {
		String[] dataSourceArray = getSchemaToDataSourceBeanIdsMap().get(dataSource);
		setDataSourceKey(dataSourceArray[MASTER_INDEX]);
	}

	/**
	 * 
	 * @Title: getSchemaByDao
	 * @Description: 根据dao找到schema
	 * @param dao
	 * @return
	 * @author xiegr
	 * @date 2021-12-05 12:13:18
	 */
	private static String getSchemaByDao(String dao) {
		return getMapperToSchemaMap().get(dao);
	}

	/**
	 * 
	 * @Title: getDataSourceArrayBySchema
	 * @Description: 根据schema找到数据源
	 * @param schema
	 * @return
	 * @author xiegr
	 * @date 2021-12-05 12:14:13
	 */
	private static String[] getDataSourceArrayBySchema(String schema) {
		return getSchemaToDataSourceBeanIdsMap().get(schema);
	}

	/**
	 * 
	 * @Title: setDataSourceKey
	 * @Description: 设置数据源
	 * @param dataSource
	 * @author xiegr
	 * @date 2021-12-01 05:19:39
	 */
	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	/**
	 * 
	 * @Title: getCurrentDataSourceKey
	 * @Description:获取当前数据源
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 05:16:32
	 */
	public static String getCurrentDataSourceKey() {
		return dataSourceKey.get();
	}

	/**
	 * 
	 * @Title: clearDataSourceKey
	 * @Description: 清除数据源
	 * @author xiegr
	 * @date 2021-12-01 05:16:21
	 */
	public static void clearDataSourceKey() {
		dataSourceKey.remove();
	}

	/**
	 * 
	 * @Title: randomIndex
	 * @Description: 获取随机数
	 * @param dataSourceArrayLength
	 * @return
	 * @author xiegr
	 * @date 2021-12-02 09:00:36
	 */
	private static int randomIndex(int dataSourceArrayLength) {
		Random random = new Random();
		int rval = random.nextInt(dataSourceArrayLength);
		return rval % dataSourceArrayLength;
	}

	/**
	 * @param mapperToSchemaMap the mapperToSchemaMap to set
	 */
	public static void setDaoToSchemaMap(Map<String, String> mapperToSchemaMap) {
		MultiDataSourceRouter.mapperToSchemaMap = mapperToSchemaMap;
	}

	/**
	 * @return the daoToSchemaMap
	 */
	public static Map<String, String> getMapperToSchemaMap() {
		return mapperToSchemaMap;
	}

	/**
	 * @return the schemaToDatasourceBeanIdsMap
	 */
	public static Map<String, String[]> getSchemaToDataSourceBeanIdsMap() {
		return schemaToDataSourceBeanIdsMap;
	}

	/**
	 * @param schemaToDatasourceBeanIdsMap the schemaToDatasourceBeanIdsMap to set
	 */
	public static void setSchemaToDataSourceBeanIdsMap(Map<String, String[]> schemaToDataSourceBeanIdsMap) {
		MultiDataSourceRouter.schemaToDataSourceBeanIdsMap = schemaToDataSourceBeanIdsMap;
	}

	/**
	 * @return the defaultDBSchema
	 */
	public static String getDefaultDBSchema() {
		return defaultDBSchema;
	}

	/**
	 * @param defaultDBSchema the defaultDBSchema to set
	 */
	public static void setDefaultDBSchema(String defaultDBSchema) {
		MultiDataSourceRouter.defaultDBSchema = defaultDBSchema;
	}

	/**
	 * @return the readOnlyInSlave
	 */
	public static boolean getReadOnlyInSlave() {
		return readOnlyInSlave;
	}

	/**
	 * @param readOnlyInSlave the readOnlyInSlave to set
	 */
	public static void setReadOnlyInSlave(boolean readOnlyInSlave) {
		MultiDataSourceRouter.readOnlyInSlave = readOnlyInSlave;
	}

	/**
	 * 
	 * @Title: isNeedCheckSql
	 * @Description: 检测sql语句
	 * @param dao
	 * @return
	 * @author xiegr
	 * @date 2021-12-02 07:18:55
	 */
	public static boolean isNeedCheckSql(String dao) {
		return !checkSqlExclude.containsKey(dao);
	}

	/**
	 * @param checkSqlExclude the checkSqlExclude to set
	 */
	public static void setCheckSqlExclude(Map<String, String> checkSqlExclude) {
		MultiDataSourceRouter.checkSqlExclude = checkSqlExclude;
	}

}
