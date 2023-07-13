/**  
 * @Title: ErrorClassLoad.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-08 05:47:01 
 */
package com.aijava.core.error.internel;

import java.util.Map;

import javax.annotation.Resource;

import com.aijava.core.error.BaseErrorCode;
import com.aijava.core.error.GatewayError;
import com.aijava.core.error.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ErrorClassLoad
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-08 05:47:01
 */
@Component
public class ErrorConfigLoader {

	private final Logger logger = LoggerFactory.getLogger(ErrorConfigLoader.class);

	/**
	 * gateway errormap
	 */
	@Resource
	private Map<String, Object> gatewayErrorMap;

	/**
	 * 服务errorMap
	 */
	@Resource
	private Map<String, Object> serviceErrorMap;

	/**
	 * 
	 * @Title: init
	 * @Description:启动
	 * @author xiegr
	 * @date 2021-12-08 05:54:54
	 */
	public void init() {

		logger.info("start to init error code info");

		for (ServiceError service_error : ServiceError.values()) {
			this.initErrorContent(service_error, this.serviceErrorMap);
		}

		for (GatewayError gateway_error : GatewayError.values()) {
			this.initErrorContent(gateway_error, this.gatewayErrorMap);
		}

		logger.info("init error code success. gateway error size {}, service error size {}",
				this.gatewayErrorMap.size(), this.serviceErrorMap.size());

	}

	@SuppressWarnings("unchecked")
	private void initErrorContent(BaseErrorCode errorCode, Map<String, Object> map) {
		int code = errorCode.getCode();
		String key = "[" + code + "]"; // yml读取的map，key为[422]
		Object messageObj = map.get(key);
		if (messageObj != null) {

			Map<String, Object> errorInfo = (Map<String, Object>) messageObj;

			errorCode.setErrorContent(errorInfo);
		}
	}
}
