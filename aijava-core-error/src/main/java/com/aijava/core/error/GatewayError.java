package com.aijava.core.error;

import java.text.MessageFormat;
import java.util.Map;

/**
 * 
 * @ClassName: GatewayError
 * @Description: Gateway的错误码定义
 * @author xiegr
 * @date 2021-12-08 06:00:57
 */
public enum GatewayError implements BaseErrorCode {

	CODE_SUCCESS(200), SERVICE_ERROR(998), SERVICE_NOT_AVAILBLE(999);

	static String DEFAULT_ERROR_MSG = "操作失败";

	static int DEFAULT_ERROR_CODE = 400;

	private final int code;

	private String message;

	GatewayError(int code) {
		this.code = code;
	}

	/**
	 * 根据code查找错误
	 * 
	 * @param code code
	 * @return error info
	 */
	public static GatewayError getGatewayErrorByCode(int code) {
		for (GatewayError error : GatewayError.values()) {
			if (error.code == code) {
				return error;
			}
		}
		return null;
	}

	public String getMessage(Object... param) {
		if (param != null && param.length > 0) {
			return MessageFormat.format(this.message, param);
		} else {
			return message;
		}
	}

	@Override
	public void setErrorContent(Map<String, Object> content) {
		this.message = (String) content.get("message");
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

}
