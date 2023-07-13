/**  
 * @Title: DashBoardAccessEventListener.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 09:16:11 
 */
package com.aijava.core.alarm.listener;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.aijava.core.alarm.event.DashboardAccessEvent;

/**
 * @ClassName: DashBoardAccessEventListener
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 09:16:11
 */
@Component
public class DashboardAccessEventListener extends AbstractEventListener<DashboardAccessEvent> {

	/**
	 * 设置参数
	 */
	@Override
	protected void addArgs(DashboardAccessEvent event, Map<String, Object> args) {
		if (event.getRequestParams() != null) {
			args.putAll(event.getRequestParams());
		}

		if (event.getStack() != null) {
			args.put("stack", event.getStack());
		}

		args.put("cost", event.getCost());
	}

	/**
	 * 设置tags
	 */
	@Override
	protected void addTags(DashboardAccessEvent event, Map<String, String> tags) {
		tags.put("status", String.valueOf(event.getStatusCode()));
	}

	/**
	 * 忽略某个事件. 只处理200事件
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final DashboardAccessEvent event) {
		if (event.getStatusCode() == 200) {
			return false;
		}
		return true;
	}

	@Override
	protected String getDatabase() {
		return DB_EVENT;
	}

	@Override
	String getCatalog(DashboardAccessEvent event) {
		return "dashboard_access";
	}

}
