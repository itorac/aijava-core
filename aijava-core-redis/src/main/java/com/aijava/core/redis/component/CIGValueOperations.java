/**  
 * @Title: YHValueOperations.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 01:27:37 
 */
package com.aijava.core.redis.component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @ClassName: YHValueOperations
 * @Description:String操作
 * @author xiegr
 * @date 2021-12-15 01:27:37
 */
@Component("cigValueOperations")
public class CIGValueOperations<K, V> implements BeanNameAware {

	/**
	 * 写
	 */
	@Resource(name = "redisTemplate")
	private ValueOperations<K, V> valueOperations;
	
	/**
	 * 读
	 */
	@Resource(name = "redisTemplateReadOnly")
	private ValueOperations<K, V> valueOperationsReadOnly;
	
	

	private String beanName;

	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @Title: setBeanName
	 * @Description: TODO(描述)
	 * @param name
	 * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
	 * @author xiegr
	 * @date 2021-12-15 02:35:07
	 */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	/**
	 * 
	 * @Title: set
	 * @Description: TODO(描述)
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @author xiegr
	 * @date 2021-12-15 03:36:56
	 */
	public void set(K key, V value, long timeout, TimeUnit unit) {
		valueOperations.set(key, value, timeout, unit);
	}

	/**
	 * 
	 * @Title: setIfAbsent
	 * @Description: TODO(描述)
	 * @param key
	 * @param value
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:00
	 */
	public Boolean setIfAbsent(K key, V value) {
		return valueOperations.setIfAbsent(key, value);
	}

	/**
	 *
	 * @Title: setIfAbsent
	 * @Description: TODO(描述)
	 * @param key
	 * @param value
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:00
	 */
	public Boolean setIfAbsent(K key, V value,long timeout, TimeUnit unit) {
		return valueOperations.setIfAbsent(key, value,timeout,unit);
	}

	/**
	 * 
	 * @Title: syncMultiSet
	 * @Description: TODO(描述)
	 * @param m
	 * @author xiegr
	 * @date 2021-12-15 03:37:05
	 */
	public void syncMultiSet(Map<? extends K, ? extends V> m) {
		valueOperations.multiSet(m);
	}

	/**
	 * 
	 * @Title: multiSetIfAbsent
	 * @Description: TODO(描述)
	 * @param m
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:11
	 */
	public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m) {
		return valueOperations.multiSetIfAbsent(m);
	}

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(描述)
	 * @param key
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:15
	 */
	public V get(Object key) {
		return valueOperationsReadOnly.get(key);
	}

	/**
	 * 
	 * @Title: getAndSet
	 * @Description: TODO(描述)
	 * @param key
	 * @param value
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:20
	 */
	public V getAndSet(K key, V value) {
		return valueOperations.getAndSet(key, value);
	}

	/**
	 * 
	 * @Title: multiGet
	 * @Description: TODO(描述)
	 * @param keys
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:24
	 */
	public List<V> multiGet(Collection<K> keys) {
		return valueOperationsReadOnly.multiGet(keys);
	}

	/**
	 * 
	 * @Title: increment
	 * @Description: TODO(描述)
	 * @param key
	 * @param delta
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:31
	 */
	public Long increment(K key, long delta) {
		return valueOperations.increment(key, delta);
	}

	/**
	 * 
	 * @Title: increment
	 * @Description: 增长
	 * @param key
	 * @param delta
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:35
	 */
	public Double increment(K key, double delta) {
		return valueOperations.increment(key, delta);
	}

	/**
	 * 
	 * @Title: set
	 * @Description: set
	 * @param key
	 * @param value
	 * @param offset
	 * @author xiegr
	 * @date 2021-12-15 03:37:39
	 */
	public void set(K key, V value, long offset) {
		valueOperations.set(key, value, offset);
	}

	/**
	 * 
	 * @Title: set
	 * @Description: TODO(描述)
	 * @param key
	 * @param value
	 * @author xiegr
	 * @date 2021-12-15 04:26:40
	 */
	public void set(K key, V value) {
		valueOperations.set(key, value);
	}

	/**
	 * 
	 * @Title: size
	 * @Description:大小
	 * @param key
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:37:42
	 */
	public Long size(K key) {
		return valueOperationsReadOnly.size(key);
	}

	/**
	 * 
	 * @Title: getOperations
	 * @Description:可以执行lua
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 08:37:31
	 */
	public RedisOperations<K, V> getOperations() {
		return valueOperations.getOperations();
	}

}
