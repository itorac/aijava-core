/**  
 * @Title: CIGListOperations.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:15:54 
 */
package com.aijava.core.redis.component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CIGListOperations
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:15:54
 */
@Component("cigListOperations")
@SuppressWarnings("unchecked")
public class CIGListOperations<K, V> implements BeanNameAware {

	@Resource(name = "redisTemplate")
	private ListOperations<K, V> listOperations;

	@Resource(name = "redisTemplateReadOnly")
	private ListOperations<K, V> listOperationsReadOnly;

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
	 * @date 2021-12-15 03:17:55
	 */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public List<V> range(K key, long start, long end) {
		return listOperationsReadOnly.range(key, start, end);
	}

	public void trim(K key, long start, long end) {
		listOperations.trim(key, start, end);
	}

	public Long size(K key) {
		return listOperationsReadOnly.size(key);
	}

	public Long leftPush(K key, V value) {
		return listOperations.leftPush(key, value);
	}

	public Long leftPushAll(K key, V... values) {
		return listOperations.leftPushAll(key, values);
	}

	/**
	 * 
	 * @Title: leftPushAll
	 * @Description: TODO(描述)
	 * @param key
	 * @param values
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 03:42:33
	 */
	public Long leftPushAll(K key, Collection<V> values) {
		return listOperations.leftPushAll(key, values);
	}

	public Long leftPushIfPresent(K key, V value) {
		return listOperations.leftPushIfPresent(key, value);
	}

	public Long leftPush(K key, V pivot, V value) {
		return listOperations.leftPush(key, pivot, value);
	}

	public Long rightPush(K key, V value) {
		return listOperations.rightPush(key, value);
	}

	public Long rightPushAll(K key, V... values) {
		return listOperations.rightPushAll(key, values);
	}

	/**
	 * Insert all {@literal values} at the tail of the list stored at
	 * {@literal key}.
	 *
	 * @param key    must not be {@literal null}.
	 * @param values must not be {@literal empty} nor contain {@literal null}
	 *               values.
	 * @return
	 * @since 1.5
	 */

	public Long rightPushAll(K key, Collection<V> values) {
		return listOperations.rightPushAll(key, values);
	}

	public Long rightPushIfPresent(K key, V value) {
		return listOperations.rightPushIfPresent(key, value);
	}

	public Long rightPush(K key, V pivot, V value) {
		return listOperations.rightPush(key, pivot, value);
	}

	public void set(K key, long index, V value) {
		listOperations.set(key, index, value);
	}

	public Long remove(K key, long i, Object value) {
		return listOperations.remove(key, i, value);
	}

	public V index(K key, long index) {
		return listOperationsReadOnly.index(key, index);
	}

	public V leftPop(K key) {
		return listOperations.leftPop(key);
	}

	public V leftPop(K key, long timeout, TimeUnit unit) {
		return listOperations.leftPop(key, timeout, unit);
	}

	public V rightPop(K key) {
		return listOperations.rightPop(key);
	}

	public V rightPop(K key, long timeout, TimeUnit unit) {
		return listOperations.rightPop(key, timeout, unit);
	}

	public V rightPopAndLeftPush(K sourceKey, K destinationKey) {
		return listOperations.rightPopAndLeftPush(sourceKey, destinationKey);
	}

	public V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit) {
		return listOperations.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
	}

	/**
	 * 
	 * @Title: getOperations
	 * @Description: TODO(描述)
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 08:39:16
	 */
	public RedisOperations<K, V> getOperations() {
		return listOperations.getOperations();
	}
}
