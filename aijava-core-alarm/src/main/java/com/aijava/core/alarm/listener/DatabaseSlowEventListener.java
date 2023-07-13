package com.aijava.core.alarm.listener;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.aijava.core.alarm.event.DatabaseAccessEvent;

/**
 * 
 * @ClassName: DatabaseSlowEventListener
 * @Description: 数据库访问事件. 只处理慢查询
 * @author xiegr
 * @date 2021-12-17 03:00:32
 */
public class DatabaseSlowEventListener extends AbstractEventListener<DatabaseAccessEvent> {

	/**
	 * 设置参数
	 */
	@Override
	protected void addArgs(DatabaseAccessEvent event, Map<String, Object> args) {
		// error exception
		if (event.getThrowable() != null) {
			args.put("stack", ExceptionUtils.getStackTrace(event.getThrowable()));
		}

		// 调用延时
		args.put("cost", event.getCost());
	}

	/**
	 * 设置tags
	 */
	@Override
	protected void addTags(DatabaseAccessEvent event, Map<String, String> tags) {
		tags.put("statement", event.getStatement());
		tags.put("db", event.getDatasource());
	}

	@Override
	String getCatalog(DatabaseAccessEvent event) {
		return "db_slow_query";
	}

	/**
	 * 忽略某个事件. 只处理>50ms的数据库事件
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final DatabaseAccessEvent event) {
		if (event.getCost() < 50) {
			return true;
		}
		return false;
	}

}
