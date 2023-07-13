/**  
 * @Title: GatewayAccessFailEventListener.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:35:42 
 */
package com.aijava.core.alarm.listener;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.aijava.core.alarm.event.GatewayAccessEvent;


/**
 * @ClassName: GatewayAccessFailEventListener
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:35:42
 */
@Component
public class GatewayAccessFailEventListener extends AbstractEventListener<GatewayAccessEvent> {

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
	 * 如果是2xx~ 3xx ，或者401，则不处理
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final GatewayAccessEvent event) {
		if (event.getStatusCode() <= 399 || event.getStatusCode() == 401) {
			return true;
		}
		return false;
	}

	@Override
	String getCatalog(GatewayAccessEvent event) {
		return "gateway_access_failed";
	}

	@Override
	protected String getDatabase() {
		return DB_EVENT;
	}

}
