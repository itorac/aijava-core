package com.aijava.core.alarm.listener;

import java.util.Map;

import com.aijava.core.alarm.event.DatabaseAccessEvent;

/**
 * 
 * @ClassName: DatabaseAccessEventListener
 * @Description: 数据库访问事件 所有的事件
 * @author xiegr
 * @date 2021-12-17 02:58:07
 */
public class DatabaseAccessEventListener extends AbstractEventListener<DatabaseAccessEvent> {

	/**
	 * 设置参数
	 */
	@Override
	protected void addArgs(DatabaseAccessEvent event, Map<String, Object> args) {
		// 调用延时
		args.put("cost", event.getCost());
	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * 设置tags
	 */
	@Override
	protected void addTags(DatabaseAccessEvent event, Map<String, String> tags) {
		tags.put("statement", event.getStatement());
		tags.put("db", event.getDatasource());
	}

	@Override
	String getCatalog(DatabaseAccessEvent event) {
		return "db_access";
	}

	@Override
	protected String getDatabase() {
		return DB_EVENT;
	}

	/**
	 * 忽略某个事件. 只处理>50ms的数据库事件
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final DatabaseAccessEvent event) {

		if (event.getThrowable() != null) {
			return true;
		}
		return false;
	}

}
