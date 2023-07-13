/**  
 * @Title: ServiceErrorCode.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 01:50:05 
 */
package com.aijava.core.error;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * @ClassName: ServiceError
 * @Description: 服务异常
 * @author xiegr
 * @date 2021-11-30 01:52:28
 */
public enum ServiceError implements BaseErrorCode {

	USER_EXIST(50100),
	USER_NOT_EXIST(50101),
	OTHER_ERROR(500),
	USER_AUTH_EXPIRED(50102),
	USER_AUTHORITY_ERROR(50103);

	private final int code;

	private String message;

	/**
	 * 可选，映射到gateway错误码
	 */
	private String gwInfo;

	/**
	 * @Title: ServiceError
	 * @Description: ServiceError构造函数
	 * @param i
	 * @author xiegr
	 * @date 2021-12-08 06:59:02
	 */
	ServiceError(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public String getMessage(Object... params) {
		return String.format(this.message, params);
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the gwInfo
	 */
	public String getGwInfo() {
		return gwInfo;
	}

	/**
	 * @param gwInfo the gwInfo to set
	 */
	public void setGwInfo(String gwInfo) {
		this.gwInfo = gwInfo;
	}

	/**
	 * @Title: getCode
	 * @Description: TODO(描述)
	 * @return
	 * @see BaseErrorCode#getCode()
	 * @author xiegr
	 * @date 2021-12-08 06:57:05
	 */
	@Override
	public int getCode() {
		return code;
	}

	/**
	 * @Title: setErrorContent
	 * @Description: TODO(描述)
	 * @param content
	 * @see BaseErrorCode#setErrorContent(java.util.Map)
	 * @author xiegr
	 * @date 2021-12-08 06:57:05
	 */
	@Override
	public void setErrorContent(Map<String, Object> content) {
		this.message = (String) content.get("message");
		this.gwInfo = (String) content.get("gwError");
	}

	/**
	 * 获取映射的Gateway错误. 1. 如果配置了gw_info, 则直接映射为gw_info中的code和message 2.
	 * 如果没有配置gw_info， 直接返回service error 中定义的东西
	 *
	 * @return Getaway错误
	 */
	public Pair<Integer, String> getMappingGatewayError() {
		Pair<Integer, String> gwError;
		if (StringUtils.isEmpty(this.gwInfo)) {
			gwError = Pair.of(GatewayError.DEFAULT_ERROR_CODE, GatewayError.DEFAULT_ERROR_MSG);
		} else {
			gwError = this.getGwErrorByMappingInfo();
		}
		return gwError;
	}

	/**
	 * 
	 * @Title: getGwErrorByMappingInfo
	 * @Description:获取gw error
	 * @return
	 * @author xiegr
	 * @date 2021-12-08 07:03:01
	 */
	private Pair<Integer, String> getGwErrorByMappingInfo() {
		String[] codeMessage = this.gwInfo.split(":", 2);
		int gwCode = Integer.parseInt(codeMessage[0]);
		String gwMsg = codeMessage[1];
		if (StringUtils.isEmpty(gwMsg)) {// 如果gwInfo只配置了code，则需要去找message
			GatewayError error = GatewayError.getGatewayErrorByCode(gwCode);
			if (error != null) {
				gwMsg = error.getMessage();
			} else { // 默认的错误
				gwMsg = GatewayError.DEFAULT_ERROR_MSG;
			}
		}
		return Pair.of(gwCode, gwMsg);
	}

	/**
	 * 根据code 获取 error
	 *
	 * @param code
	 * @return
	 */
	public static ServiceError getServiceErrorByCode(int code) {
		for (ServiceError error : values()) {
			if (error.getCode() == code) {
				return error;
			}
		}
		return null;
	}

}
