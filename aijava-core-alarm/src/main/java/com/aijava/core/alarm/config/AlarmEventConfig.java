/**  
 * @Title: TaskPoolConfig.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 03:48:22 
 */
package com.aijava.core.alarm.config;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @ClassName: TaskPoolConfig
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 03:48:22
 */
@Configuration
@EnableAsync
public class AlarmEventConfig {

	/**
	 * 
	 * @Title: getRejectedExecutionHandler
	 * @Description: 超出队列回收策略
	 * @return
	 * @author xiegr
	 * @date 2021-12-17 03:55:03
	 */
	@Bean
	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return new ThreadPoolExecutor.DiscardPolicy();
	}

	/**
	 * 
	 * @Title: taskExecutro
	 * @Description: 线程
	 * @param rejectedExecutionHandler
	 * @return
	 * @author xiegr
	 * @date 2021-12-17 03:55:45
	 */
	@Bean("cigEventTaskExecutor")
	public Executor taskExecutro(RejectedExecutionHandler rejectedExecutionHandler) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()*2);
		// 根据系统压力修改
		taskExecutor.setQueueCapacity(50000);
		taskExecutor.setThreadNamePrefix("CigEventExecutor--");
		taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
		return taskExecutor;
	}

	/**
	 * 
	 * @Title: getSimpleApplicationEventMulticaster
	 * @Description: TODO(描述)
	 * @param taskExecutor
	 * @return
	 * @author xiegr
	 * @date 2021-12-17 04:11:39
	 */
	@Bean("applicationEventMulticaster")
	public SimpleApplicationEventMulticaster getSimpleApplicationEventMulticaster(
			@Qualifier("cigEventTaskExecutor") Executor taskExecutor) {
		SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
		simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor);
		return simpleApplicationEventMulticaster;
	}
}
