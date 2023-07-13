/**  
 * @Title: GatewayAccess401EventListener.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:53:48 
 */
package com.aijava.core.alarm.listener;

import java.util.Map;

import com.aijava.core.alarm.event.GatewayAccessEvent;

/**
 * @ClassName: GatewayAccess401EventListener
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:53:48
 */
public class GatewayAccess401EventListener extends AbstractEventListener<GatewayAccessEvent> {

	/**
	 * 设置参数
	 */
	@Override
	protected void addArgs(GatewayAccessEvent event, Map<String, Object> args) {
		if (event.getRequestParams() != null) {
			args.putAll(event.getRequestParams());
		}

	}

	/**
	 * 设置tags
	 */
	@Override
	protected void addTags(GatewayAccessEvent event, Map<String, String> tags) {
		tags.put("status", String.valueOf(event.getStatusCode()));
	}

	/**
	 * 忽略某个事件. 只处理401事件
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final GatewayAccessEvent event) {
		if (event.getStatusCode() == 401) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: getCatalog
	 * @Description: TODO(描述)
	 * @param event
	 * @return
	 * @see com.cig.dashboard.event.AbstractEventListener#getCatalog(com.cig.dashboard.event.CommonEvent)
	 * @author xiegr
	 * @date 2021-12-17 02:54:13
	 */
	@Override
	String getCatalog(GatewayAccessEvent event) {
		return "gateway_access_401";
	}

}
