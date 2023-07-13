package com.aijava.core.redis.config;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public abstract class RedisTemplateConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateConfig.class);

	/**
	 * @Title: lettuceClientResources
	 * @Description:增加一个销毁方法
	 * @return
	 * @author xiegr
	 * @date 2022-04-22 04:40:57
	 */
	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean(ClientResources.class)
	public DefaultClientResources lettuceClientResources() {
		return DefaultClientResources.create();
	}

	/**
	 * 
	 * @Title: getJedisConnectionFactory
	 * @Description: LettuceConnectionFactory
	 * @param redisProperties
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 05:33:13
	 */
	public LettuceConnectionFactory getLettuceConnectionFactory(RedisProperties redisProperties,
			DefaultClientResources lettuceClientResources) {
		LOGGER.info("redis properties is :" + redisProperties);
		GenericObjectPoolConfig<Object> genericObjectPoolConfig = getGenericObjectPoolConfig(redisProperties);
		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(redisProperties.getTimeout())).poolConfig(genericObjectPoolConfig)
				.clientResources(lettuceClientResources).build();
		// 单机
		RedisStandaloneConfiguration redisStandaloneConfiguration = getRedisStandaloneConfiguration(redisProperties);
		LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
		// 哨兵
		// RedisSentinelConfiguration redisSentinelConfiguration =
		// getRedisSentinelConfiguration(redisProperties);
		// LettuceConnectionFactory factory = new
		// LettuceConnectionFactory(redisSentinelConfiguration, clientConfig);
		// 集群
		// RedisClusterConfiguration redisClusterConfiguration =
		// getRedisClusterConfiguration(redisProperties);
		// LettuceConnectionFactory factory = new
		// LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
		factory.afterPropertiesSet();
		return factory;
	}

	/**
	 * 
	 * @Title: genericObjectPoolConfig
	 * @Description:连接池
	 * @param redisProperties
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 07:24:29
	 */
	private GenericObjectPoolConfig<Object> getGenericObjectPoolConfig(RedisProperties redisProperties) {
		GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
		genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
		genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
		genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
		genericObjectPoolConfig.setMaxWait(Duration.ofMillis(redisProperties.getLettuce().getPool().getMaxWait()));
		return genericObjectPoolConfig;
	}

	/**
	 * 
	 * @Title: getRedisStandaloneConfiguration
	 * @Description: 单机模式
	 * @param redisProperties
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 07:24:40
	 */
	public RedisStandaloneConfiguration getRedisStandaloneConfiguration(RedisProperties redisProperties) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
		redisStandaloneConfiguration.setHostName(redisProperties.getHost());
		redisStandaloneConfiguration.setPort(redisProperties.getPort());
		redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
		return redisStandaloneConfiguration;
	}

	/**
	 * 哨兵模式
	 * 
	 * @param redisProperties
	 * @return
	 */
	public RedisSentinelConfiguration getRedisSentinelConfiguration(RedisProperties redisProperties) {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		redisSentinelConfiguration.setDatabase(redisProperties.getDatabase());
		redisSentinelConfiguration.setSentinelPassword(RedisPassword.of(redisProperties.getPassword()));
		List<String> servers = redisProperties.getSentinel().getNodes();
		Set<RedisNode> redisNodes = new HashSet<RedisNode>();
		for (String ipPort : servers) {
			String[] ipAndPort = ipPort.split(":");
			redisNodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
		}
		// setSentinels的参数类型是Iterable<RedisNode>，所以放有RedisNode的集合可以是实现Iterable接口的任意集合，使用list、set都可以
		redisSentinelConfiguration.setSentinels(redisNodes);
		// 监控主节点的名称
		redisSentinelConfiguration.setMaster(redisProperties.getSentinel().getMaster());
		return redisSentinelConfiguration;
	}

	/**
	 * 
	 * @Title: getRedisClusterConfiguration
	 * @Description:以后需要集群配置
	 * @param redisProperties
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 08:05:32
	 */
	public RedisClusterConfiguration getRedisClusterConfiguration(RedisProperties redisProperties) {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		// 如果需要切换为集群配置
		List<String> servers = redisProperties.getCluster().getNodes();
		Set<RedisNode> nodes = new HashSet<RedisNode>();
		for (String ipPort : servers) {
			String[] ipAndPort = ipPort.split(":");
			nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
		}
		redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
		redisClusterConfiguration.setClusterNodes(nodes);
		redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
		return redisClusterConfiguration;
	}

	/**
	 * 
	 * @Title: getRedisTemplate
	 * @Description: redis template
	 * @param redisConnectionFactory
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 05:33:05
	 */
	public RedisTemplate<Object, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.setValueSerializer(redisSerializer);
		redisTemplate.setHashKeySerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(redisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
