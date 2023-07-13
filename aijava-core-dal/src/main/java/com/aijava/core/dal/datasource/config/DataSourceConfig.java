/**  
 * @Title: DataSourceConfiguration.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 07:01:23 
 */
package com.aijava.core.dal.datasource.config;

import com.aijava.core.dal.datasource.DynamicDataSource;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.collect.Maps;

/**
 * @ClassName: DataSourceConfig
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 07:01:23
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	/**
	 * 
	 * @Title: databasesMap
	 * @Description: 获取databasesMap
	 * @return
	 * @author xiegr
	 * @date 2021-12-09 01:52:30
	 */
	@Bean(name = "databasesMap")
	public YamlMapFactoryBean databasesMap() {
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		yaml.setResources(new ClassPathResource("databases.yml"));
		return yaml;
	}

	/**
	 * 
	 * @Title: dynamicDataSource
	 * @Description: 动态数据源
	 * @author xiegr
	 * @date 2021-12-05 03:22:38
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	public DynamicDataSource dynamicDataSource() {
		return new DynamicDataSource(Maps.newLinkedHashMap());
	}

	/**
	 * 
	 * @Title: transactionManager
	 * @Description:配置Transaction，统一管理
	 * @return
	 * @author xiegr
	 * @date 2021-12-05 02:31:30
	 */
	/**
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}
	**/
	/**
	 * 
	 * @return
	 */
	/**
	@Bean(name = "transactionTemplate")
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(transactionManager());
	}
	**/
}
