/**  
 * @Title: BeenConfig.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2022-04-18 11:56:28 
 */
package com.aijava.core.security.alipay.config;

import com.aijava.core.security.alipay.bean.AliappConfig;
import com.aijava.core.security.alipay.bean.CigAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: BeenConfig
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2022-04-18 11:56:28
 */
@Configuration
public class BeenConfig {

	/**
	 * @Title: getAliappConfig
	 * @Description: 实例化AliappConfig对象
	 * @return
	 * @author xiegr
	 * @date 2022-04-18 01:58:30
	 */
	@Bean
	public AliappConfig getAliappConfig() {
		return new AliappConfig();
	}

	/**
	 * @Title: getCigAlipayClient
	 * @Description: 实例化CigAlipayClient对象
	 * @return
	 * @author xiegr
	 * @date 2022-04-18 06:05:50
	 */
	@Bean
	public CigAlipayClient getCigAlipayClient() {
		return new CigAlipayClient();
	}
}
