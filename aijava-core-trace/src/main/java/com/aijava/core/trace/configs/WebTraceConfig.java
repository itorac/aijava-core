package com.aijava.core.trace.configs;


import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aijava.core.trace.interceptor.LogInterceptor;

/**
 * @author xgr
 */
@Configuration
public class WebTraceConfig implements WebMvcConfigurer {


    @Resource
    private LogInterceptor logInterceptor;

    /**
     * 该方法用于注册拦截器
     * 可注册多个拦截器，多个拦截器组成一个拦截器链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// addPathPatterns 添加路径
        // excludePathPatterns 排除路径
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") //配置需要添加静态资源请求的url
                .addResourceLocations("classpath:/pic/"); //配置静态资源路径
    }

}
