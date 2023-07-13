package com.aijava.core.alarm.event;

/**
 * 
 * @ClassName: DatabaseAccessEvent
 * @Description: 数据库操作的事件
 * @author xiegr
 * @date 2021-12-17 02:55:40
 */
public class DatabaseAccessEvent extends CommonEvent {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-17 02:55:50
	 */
	private static final long serialVersionUID = -4613286877904821546L;

	/**
	 * 异常
	 */
	private final Throwable throwable;

	/**
	 * 时间
	 */
	private final long cost;

	/**
	 * 数据库
	 */
	private final String datasource;

	/**
	 * 方法
	 */
	private final String statement;

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param name        : dao name : 例如：
	 *                    com.cig.dashboard.dal.account.AccountMapper
	 * @param statement:  接口名称，例如 getAll
	 * @param throwable:  数据库操作的异常， 可能为null
	 * @param cost:       数据库操作的延迟
	 * @param datasource: 数据库名称
	 *
	 */
	public DatabaseAccessEvent(String name, String statement, Throwable throwable, long cost, String datasource) {
		super(name);
		this.statement = statement;
		this.throwable = throwable;
		this.cost = cost;
		this.datasource = datasource;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public long getCost() {
		return cost;
	}

	public String getDatasource() {
		return datasource;
	}

	public String getStatement() {
		return statement;
	}

}
