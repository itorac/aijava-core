/**  
 * @Title: MasterSlaveRoutingDataSource.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 08:06:44 
 */
package com.aijava.core.dal.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.aijava.core.dal.datasource.router.MultiDataSourceRouter;
import com.google.common.collect.Maps;

/**
 * @ClassName: MasterSlaveRoutingDataSource
 * @Description: 动态数据源
 * @author xiegr
 * @date 2021-11-30 08:06:44
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	
	/**
	 * 
	 * @Title: DynamicDataSource
	 * @Description: DynamicDataSource构造函数
	 * @author xiegr
	 * @date 2021-12-11 11:37:52
	 */
	public DynamicDataSource() {
		super.setTargetDataSources(Maps.newLinkedHashMap());
		super.afterPropertiesSet();
	}

	/**
	 * 
	 * @param targetDataResources
	 */
	public DynamicDataSource(Map<Object, Object> targetDataResources) {
		super.setTargetDataSources(targetDataResources);
		super.afterPropertiesSet();
	}

	/**
	 * 
	 * @Title: DynamicDataSource
	 * @Description: DynamicDataSource构造函数
	 * @param targetDataResources
	 * @author xiegr
	 * @date 2021-12-10 08:49:54
	 */
	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataResources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataResources);
		super.afterPropertiesSet();
	}

	/**
	 * 
	 * @Title: determineCurrentLookupKey
	 * @Description:重构determineCurrentLookupKey,获取当前数据库
	 * @return
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 * @author xiegr
	 * @date 2021-12-11 11:37:59
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return MultiDataSourceRouter.getCurrentDataSourceKey();
	}
	

	/**
	private volatile Map<Object, Object> targetDataSources;

	private Object defaultTargetDataSource;

	private boolean lenientFallback = true;

	private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

	private volatile Map<Object, DataSource> resolvedDataSources;

	private volatile DataSource resolvedDefaultDataSource;

	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		this.defaultTargetDataSource = defaultTargetDataSource;
	}

	public void setResolvedDataSources(Map<Object, DataSource> resolvedDataSources) {
		this.resolvedDataSources = resolvedDataSources;
	}

	public void setResolvedDefaultDataSource(DataSource resolvedDefaultDataSource) {
		this.resolvedDefaultDataSource = resolvedDefaultDataSource;
	}

	public void setLenientFallback(boolean lenientFallback) {
		this.lenientFallback = lenientFallback;
	}

	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		this.dataSourceLookup = (dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
	}
	
	@Override
	public void afterPropertiesSet() {
		if (this.targetDataSources == null) {
			throw new IllegalArgumentException("Property 'targetDataSources' is required");
		}
		this.resolvedDataSources = new HashMap<>(this.targetDataSources.size());
		for (Map.Entry<Object, Object> entry : this.targetDataSources.entrySet()) {
			Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
			DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
			this.resolvedDataSources.put(lookupKey, dataSource);
		}
		if (this.defaultTargetDataSource != null) {
			this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
		}
	}

	public void replaceDataSources(Map<Object, Object> newtargetDataSources) {
		Map<Object, DataSource> newResolvedDataSources = new HashMap<Object, DataSource>(newtargetDataSources.size());
		for (Map.Entry<Object, Object> entry : newtargetDataSources.entrySet()) {
			Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
			DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
			newResolvedDataSources.put(lookupKey, dataSource);
		}
		if (this.defaultTargetDataSource != null) {
			this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
		}
		this.resolvedDataSources = newResolvedDataSources;
		this.targetDataSources = newtargetDataSources;
	}

	protected Object resolveSpecifiedLookupKey(Object lookupKey) {
		return lookupKey;
	}

	protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
		if (dataSource instanceof DataSource) {
			return (DataSource) dataSource;
		} else if (dataSource instanceof String) {
			return this.dataSourceLookup.getDataSource((String) dataSource);
		} else {
			throw new IllegalArgumentException(
					"Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		return determineTargetDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
	}

	protected DataSource determineTargetDataSource() {
		Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
		Object lookupKey = determineCurrentLookupKey();
		DataSource dataSource = this.resolvedDataSources.get(lookupKey);
		if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
			dataSource = this.resolvedDefaultDataSource;
		}
		if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}
		return dataSource;
	}
	**/
}
