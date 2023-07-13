/**  
 * @Title: MasterRedisTemplate.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 03:26:54 
 */
package com.aijava.core.redis.config;

import javax.annotation.Resource;

import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName: MasterRedisTemplate
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 03:26:54
 */

@Configuration
@EnableCaching
public class MasterRedisTemplate extends RedisTemplateConfig {

	@Resource
	private RedisProperties redisProperties;

	/**
	 * @Title: getRedisTemplate
	 * @Description:主的模板
	 * @param redisConnectionFactory
	 * @return 
	 * @see RedisTemplateConfig#getRedisTemplate(org.springframework.data.redis.connection.RedisConnectionFactory)
	 * @author xiegr
	 * @date 2022-04-22 05:18:26
	 */
	@Bean(name = "redisTemplate")
	@Override
	public RedisTemplate<Object, Object> getRedisTemplate(
			@Qualifier("masterJedisConnFactory") RedisConnectionFactory redisConnectionFactory) {
		return super.getRedisTemplate(redisConnectionFactory);
	}

	/**
	 * @Title: getLettuceConnectionFactory
	 * @Description: 主工厂
	 * @param redisProperties
	 * @param lettuceClientResources
	 * @return 
	 * @see RedisTemplateConfig#getLettuceConnectionFactory(RedisProperties, io.lettuce.core.resource.DefaultClientResources)
	 * @author xiegr
	 * @date 2022-04-22 05:18:15
	 */
	@Primary
	@Bean(name = "masterJedisConnFactory")
	@Override
	public LettuceConnectionFactory getLettuceConnectionFactory(
			@Qualifier("masterRedisProperties") RedisProperties redisProperties,DefaultClientResources lettuceClientResources) {
		return super.getLettuceConnectionFactory(redisProperties,lettuceClientResources);
	}

	/**
	 * @Title: getRedisProperties
	 * @Description: 获取主配置
	 * @return
	 * @author xiegr
	 * @date 2022-04-22 05:18:06
	 */
	@Bean(name = "masterRedisProperties")
	@ConfigurationProperties(prefix = "spring.redis.master")
	public RedisProperties getRedisProperties() {
		return redisProperties;
	}

}
