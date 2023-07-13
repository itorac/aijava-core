/**  
 * @Title: ServiceException.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 10:05:16 
 */
package com.aijava.core.error.exception;

import com.aijava.core.error.ServiceError;

/**
 * @ClassName: ServiceException
 * @Description: 服务错误异常
 * @author xiegr
 * @date 2021-11-30 10:05:16
 */
public class ServiceException extends RuntimeException {
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-11-30 01:37:43
	 */
	private static final long serialVersionUID = -3914163135683573787L;

	// 服务错误码
	private final ServiceError serviceError;

	private final int code;

	private final String errorMessage;

	private String[] params;

	// 是否输出堆栈
	private boolean printStack = false;

	public ServiceException(int code, String message) {
		this(code, message, false);
	}

	public ServiceException(int code, String message, boolean printStack) {
		this.code = code;
		this.errorMessage = message;
		this.printStack = printStack;

		ServiceError found = ServiceError.getServiceErrorByCode(code);
		this.serviceError = found == null ? ServiceError.OTHER_ERROR : found;
	}

	@Override
	public String getMessage() {
		return "[" + this.code + ":" + this.errorMessage + "]";
	}

	/**
	 * 服务异常
	 * 
	 * @param serviceError 服务异常
	 */
	public ServiceException(ServiceError serviceError) {
		this(serviceError, false);
	}

	/**
	 * 服务异常
	 * 
	 * @param serviceError 服务异常
	 */
	public ServiceException(ServiceError serviceError, boolean printStack) {
		this.serviceError = serviceError;
		this.code = serviceError.getCode();
		this.errorMessage = serviceError.getMessage();
		this.printStack = printStack;
	}

	/**
	 * 服务异常
	 * 
	 * @param serviceError 服务异常
	 * @param cause        服务异常
	 */

	public ServiceException(ServiceError serviceError, Throwable cause) {
		super(serviceError.getMessage(), cause);
		this.serviceError = serviceError;
		this.code = serviceError.getCode();
		this.errorMessage = serviceError.getMessage();
	}

	/**
	 * 服务异常
	 *
	 * @param serviceError 服务异常
	 */
	public ServiceException(ServiceError serviceError, boolean printStack, Object... params) {
		this.serviceError = serviceError;
		this.code = serviceError.getCode();
		this.errorMessage = serviceError.getMessage(params);
		this.printStack = printStack;
	}

	/**
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

	/**
	 * @return the printStack
	 */
	public boolean isPrintStack() {
		return printStack;
	}

	/**
	 * @param printStack the printStack to set
	 */
	public void setPrintStack(boolean printStack) {
		this.printStack = printStack;
	}

	/**
	 * @return the serviceError
	 */
	public ServiceError getServiceError() {
		return serviceError;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
