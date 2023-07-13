/**  
 * @Title: BaseDataSource.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-12 11:27:22 
 */
package com.aijava.core.dal.datasource.entity;

import java.io.Serializable;

/**
 * @ClassName: BaseDataSource
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-12 11:27:22
 */
public class BaseDataSource implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-12 11:28:28
	 */
	private static final long serialVersionUID = 5178831449905499393L;

	/**
	 * 是否默认数据源
	 */
	private boolean isDefault = false;

	/**
	 * 数据库驱动名称
	 */
	private String driverClassName = "com.mysql.jdbc.Driver";

	/**
	 * beanId
	 */
	private String beanId;

	/**
	 * 数据库schema
	 */
	private String schema;

	/**
	 * host
	 */
	private String host;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 连接池类型
	 */
	private String type = "com.zaxxer.hikari.HikariDataSource";

	/**
	 * 最小空闲连接
	 */
	private int minIdle = 2;

	/**
	 * 池中最大连接数，包括闲置和使用中的连接
	 */
	private int maxPoolSize = 100;

	/**
	 * 连接池名称
	 */
	private String poolName = "JAVA_HikariCP";

	/**
	 * 检测语句
	 */
	private String connectionTestQuery = "SELECT 1";

	/**
	 * 自动提交从池中返回的连接
	 */
	private boolean autoCommit = true;

	/**
	 * 等待来自池的连接的最大毫秒数
	 */
	private int connectionTimeout = 30000;

	/**
	 * 池中连接最长生命周期
	 */
	private int maxLifetime = 1800000;

	/**
	 * 连接允许在池中闲置的最长时间
	 */
	private int idleTimeout = 60000;

	/**
	 * 连接将被测试活动的最大时间量
	 */
	private int validationTimeout = 10000;

	/**
	 * @return the isDefault
	 */
	public boolean isDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName the driverClassName to set
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the beanId
	 */
	public String getBeanId() {
		return beanId;
	}

	/**
	 * @param beanId the beanId to set
	 */
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the minIdle
	 */
	public int getMinIdle() {
		return minIdle;
	}

	/**
	 * @param minIdle the minIdle to set
	 */
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * @return the maxPoolSize
	 */
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * @return the poolName
	 */
	public String getPoolName() {
		return poolName;
	}

	/**
	 * @param poolName the poolName to set
	 */
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	/**
	 * @return the connectionTestQuery
	 */
	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}

	/**
	 * @param connectionTestQuery the connectionTestQuery to set
	 */
	public void setConnectionTestQuery(String connectionTestQuery) {
		this.connectionTestQuery = connectionTestQuery;
	}

	/**
	 * @return the autoCommit
	 */
	public boolean isAutoCommit() {
		return autoCommit;
	}

	/**
	 * @param autoCommit the autoCommit to set
	 */
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	/**
	 * @return the connectionTimeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * @param connectionTimeout the connectionTimeout to set
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * @return the maxLifetime
	 */
	public int getMaxLifetime() {
		return maxLifetime;
	}

	/**
	 * @param maxLifetime the maxLifetime to set
	 */
	public void setMaxLifetime(int maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	/**
	 * @return the idleTimeout
	 */
	public int getIdleTimeout() {
		return idleTimeout;
	}

	/**
	 * @param idleTimeout the idleTimeout to set
	 */
	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	/**
	 * @return the validationTimeout
	 */
	public int getValidationTimeout() {
		return validationTimeout;
	}

	/**
	 * @param validationTimeout the validationTimeout to set
	 */
	public void setValidationTimeout(int validationTimeout) {
		this.validationTimeout = validationTimeout;
	}

}
