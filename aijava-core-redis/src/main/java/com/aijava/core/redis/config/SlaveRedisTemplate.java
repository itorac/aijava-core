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
public class SlaveRedisTemplate extends RedisTemplateConfig {

	@Resource
	private RedisProperties redisProperties;

	/**
	 * 
	 * @Title: getRedisTemplate
	 * @Description: 读库
	 * @param redisConnectionFactory
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 06:00:28
	 */
	@Bean(name = "redisTemplateReadOnly")
	@Override
	public RedisTemplate<Object, Object> getRedisTemplate(
			@Qualifier("slaveJedisConnFactory") RedisConnectionFactory redisConnectionFactory) {
		return super.getRedisTemplate(redisConnectionFactory);
	}

	/**
	 * 
	 * @Title: getJedisConnectionFactory
	 * @Description: 获取读的工厂
	 * @param redisProperties
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 06:00:42
	 */
	@Bean(name = "slaveJedisConnFactory")
	@Override
	public LettuceConnectionFactory getLettuceConnectionFactory(
			@Qualifier("slaveRedisProperties") RedisProperties redisProperties, DefaultClientResources lettuceClientResources) {
		return super.getLettuceConnectionFactory(redisProperties,lettuceClientResources);
	}

	/**
	 * @Title: getRedisProperties
	 * @Description: 获取读的配置
	 * @return
	 * @author xiegr
	 * @date 2022-04-22 05:17:51
	 */
	@Bean(name = "slaveRedisProperties")
	@ConfigurationProperties(prefix = "spring.redis.slave")
	public RedisProperties getRedisProperties() {
		return redisProperties;
	}

}
