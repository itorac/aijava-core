/**  
 * @Title: RedisProperties.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 03:02:14 
 */
package com.aijava.core.redis.config;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @ClassName: RedisProperties
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 03:02:14
 */
@Component
public class RedisProperties implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-16 03:28:33
	 */
	private static final long serialVersionUID = 7586753157343726027L;

	private int database = 0;

	private String host = "localhost";

	private String password;

	private int port = 6379;
	/**
	 * Connection timeout.
	 */
	private int timeout = 500;

	private final Lettuce lettuce = new Lettuce();

	private final Sentinel sentinel = new Sentinel();

	private final Cluster cluster = new Cluster();

	/**
	 * @return the database
	 */
	public int getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(int database) {
		this.database = database;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the lettuce
	 */
	public Lettuce getLettuce() {
		return lettuce;
	}

	public Sentinel getSentinel() {
		return sentinel;
	}

	public Cluster getCluster() {
		return cluster;
	}

	/**
	 * Pool properties.
	 */
	public static class Pool {

		/**
		 * Maximum number of "idle" connections in the pool. Use a negative value to
		 * indicate an unlimited number of idle connections.
		 */
		private int maxIdle = 8;

		/**
		 * Target for the minimum number of idle connections to maintain in the pool.
		 * This setting only has an effect if both it and time between eviction runs are
		 * positive.
		 */
		private int minIdle = 0;

		/**
		 * Maximum number of connections that can be allocated by the pool at a given
		 * time. Use a negative value for no limit.
		 */
		private int maxActive = 8;

		/**
		 * Maximum amount of time a connection allocation should block before throwing
		 * an exception when the pool is exhausted. Use a negative value to block
		 * indefinitely.
		 */
		private int maxWait = -1;

		public int getMaxIdle() {
			return this.maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return this.minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return this.maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		/**
		 * @return the maxWait
		 */
		public int getMaxWait() {
			return maxWait;
		}

		/**
		 * @param maxWait the maxWait to set
		 */
		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

	}

	/**
	 * Jedis client properties.
	 */
	public static class Lettuce {

		/**
		 * Jedis pool configuration.
		 */
		private Pool pool;

		public Pool getPool() {
			return this.pool;
		}

		public void setPool(Pool pool) {
			this.pool = pool;
		}

	}

	public static class Sentinel {

		private String master;

		private List<String> nodes;

		public String getMaster() {
			return master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public List<String> getNodes() {
			return nodes;
		}

		public void setNodes(List<String> nodes) {
			this.nodes = nodes;
		}
	}

	public static class Cluster {

		private Integer maxRedirects;

		private List<String> nodes;

		public List<String> getNodes() {
			return nodes;
		}

		public void setNodes(List<String> nodes) {
			this.nodes = nodes;
		}

		public Integer getMaxRedirects() {
			return maxRedirects;
		}

		public void setMaxRedirects(Integer maxRedirects) {
			this.maxRedirects = maxRedirects;
		}

	}

}
