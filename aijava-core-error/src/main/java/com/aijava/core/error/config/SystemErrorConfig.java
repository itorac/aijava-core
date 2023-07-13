/**  
 * @Title: PropertySourcesPlaceholderConfig.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-08 04:15:18 
 */
package com.aijava.core.error.config;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.aijava.core.error.internel.ErrorConfigLoader;

/**
 * @ClassName: PropertySourcesPlaceholderConfig
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-08 04:15:18
 */
@Configuration
public class SystemErrorConfig {

	/**
	 * 
	 * @Title: gatewayError
	 * @Description:gateway异常
	 * @return
	 * @author xiegr
	 * @date 2021-12-08 05:31:26
	 */
	@Bean(name = "gatewayErrorMap")
	public YamlMapFactoryBean gatewayErrorMap() {
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		yaml.setResources(new ClassPathResource("gateway-error.yml"));
		return yaml;
	}

	/**
	 * 
	 * @Title: serviceErrorMap
	 * @Description:服务异常
	 * @return
	 * @author xiegr
	 * @date 2021-12-08 07:12:40
	 */
	@Bean(name = "serviceErrorMap")
	public YamlMapFactoryBean serviceErrorMap() {
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		ClassPathResource[] resources = { new ClassPathResource("service-error.yml"),
				new ClassPathResource("service-error-user.yml") };
		yaml.setResources(resources);
		return yaml;
	}

	/**
	 * 
	 * @Title: initConfigLoader
	 * @Description: 加载error config loader
	 * @return
	 * @author xiegr
	 * @date 2021-12-08 07:30:59
	 */
	@Bean(initMethod = "init")
	public ErrorConfigLoader initConfigLoader() {
		return new ErrorConfigLoader();
	}

}
