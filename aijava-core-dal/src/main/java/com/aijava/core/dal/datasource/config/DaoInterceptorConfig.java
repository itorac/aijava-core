/**  
 * @Title: AspectJInterceptorConfig.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-01 03:18:58 
 */
package com.aijava.core.dal.datasource.config;

import javax.annotation.Resource;

import com.aijava.core.dal.datasource.interceptor.DaoInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: AspectJInterceptorConfig
 * @Description: 自定义拦截器配置
 * @author xiegr
 * @date 2021-12-01 03:18:58
 */
@Configuration
public class DaoInterceptorConfig {

	@Resource
	private DaoInterceptor daoInterceptor;

	/**
	 * 具体项目下dal包下的路径
	 */
	public static final String dalTraceExecution = "execution(* com.cig..*.dal..*.*(..))";

	@Bean
	public DefaultPointcutAdvisor defaultPointcutAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(dalTraceExecution);
		// 配置增强类advisor
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setPointcut(pointcut);
		advisor.setAdvice(daoInterceptor);
		return advisor;
	}
}
