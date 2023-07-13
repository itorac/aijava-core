/**  
 * @Title: BaseOpenAPIController.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-27 02:58:15 
 */
package com.aijava.core.common.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: BaseOpenAPIController
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-27 02:58:15
 */
public class BaseOpenAPIController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 成功码 */
	static final int SUCCESS_CODE = 200;
	/** 错误码：传入参数格式无效 */
	static final int ERROR_CODE_INPUT_PARAM_FORMAT = 400;
	/** 错误码：传入参数格式无效 */
	static final int ERROR_CODE_INPUT_PARAM_LOGIC = 405;
	/** 错误码：服务端异常 */
	static final int ERROR_CODE_SERVER_EXCEPTION = 500;
	/** 错误码：核心数据不存在 */
	static final int ERROR_CODE_NO_RECORD = 504;
	/** 成功消息 */
	static final String MSG_SUCCESS = "SUCCESS";
	/** 错误消息：传入参数格式无效 */
	static final String MSG_INPUT_PARAM_FORMAT = "INPUT PARAMS FORMAT ERROR";
	/** 错误消息：传入参数不符合业务场景 */
	static final String MSG_INPUT_PARAM_LOGIC = "INPUT PARAMS LOGIC ERROR";
	/** 错误消息：服务端异常 */
	static final String MSG_SERVER_EXCEPTION = "SERVER EXCEPTION";
	/** 错误消息：无查询结果 */
	static final String MSG_NO_RECORD = "NO RECORD";
}
