/**  
 * @Title: GatewayAccessEvent.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:31:24 
 */
package com.aijava.core.alarm.event;

import java.util.Map;

/**
 * @ClassName: GatewayAccessEvent
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 02:31:24
 */
public class GatewayAccessEvent extends CommonEvent {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-17 02:34:35
	 */
	private static final long serialVersionUID = -8942913113644175278L;

	// 返回的响应码
	private final int statusCode;

	// 时间
	private final long cost;

	// 请求参数
	private final Map<String, Object> requestParams;

	// 错误的异常栈
	private String stack;

	/**
	 * @Title: DashBoardAccessEvent
	 * @Description: DashBoardAccessEvent构造函数
	 * @param name
	 * @author xiegr
	 * @date 2021-12-16 09:06:10
	 */
	public GatewayAccessEvent(String name, int statusCode, long cost, Map<String, Object> requestParams, String stack) {
		super(name);
		this.statusCode = statusCode;
		this.cost = cost;
		this.requestParams = requestParams;
		this.stack = stack;
	}

	/**
	 * @return the stack
	 */
	public String getStack() {
		return stack;
	}

	/**
	 * @param stack the stack to set
	 */
	public void setStack(String stack) {
		this.stack = stack;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the cost
	 */
	public long getCost() {
		return cost;
	}

	/**
	 * @return the requestParams
	 */
	public Map<String, Object> getRequestParams() {
		return requestParams;
	}
}
