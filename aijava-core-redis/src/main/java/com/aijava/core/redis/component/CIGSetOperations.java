/**  
 * @Title: CIGSetOperations.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:15:16 
 */
package com.aijava.core.redis.component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CIGSetOperations
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:15:16
 */
@Component("cigSetOperations")
@SuppressWarnings("unchecked")
public class CIGSetOperations<K, V> implements BeanNameAware {

	@Resource(name = "redisTemplate")
	private SetOperations<K, V> setOperations;

	@Resource(name = "redisTemplateReadOnly")
	private SetOperations<K, V> setOperationsReadOnly;

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
	 * @date 2021-12-15 03:19:52
	 */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public Set<V> difference(K key, K otherKey) {
		return setOperationsReadOnly.difference(key, otherKey);
	}

	public Set<V> difference(K key, Collection<K> otherKeys) {
		return setOperationsReadOnly.difference(key, otherKeys);
	}

	public Long differenceAndStore(K key, K otherKey, K destKey) {
		return setOperations.differenceAndStore(key, otherKey, destKey);
	}

	public Long differenceAndStore(K key, Collection<K> otherKeys, K destKey) {
		return setOperations.differenceAndStore(key, otherKeys, destKey);
	}

	public Set<V> intersect(K key, K otherKey) {
		return setOperationsReadOnly.intersect(key, otherKey);
	}

	public Set<V> intersect(K key, Collection<K> otherKeys) {
		return setOperationsReadOnly.intersect(key, otherKeys);
	}

	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return setOperations.intersectAndStore(key, otherKey, destKey);
	}

	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		return setOperations.intersectAndStore(key, otherKeys, destKey);
	}

	public Set<V> union(K key, K otherKey) {
		return setOperationsReadOnly.union(key, otherKey);
	}

	public Set<V> union(K key, Collection<K> otherKeys) {
		return setOperationsReadOnly.union(key, otherKeys);
	}

	public Long unionAndStore(K key, K otherKey, K destKey) {
		return setOperations.unionAndStore(key, otherKey, destKey);
	}

	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		return setOperations.unionAndStore(key, otherKeys, destKey);
	}

	public Long add(K key, V... values) {
		return setOperations.add(key, values);
	}

	public Boolean isMember(K key, Object o) {
		return setOperationsReadOnly.isMember(key, o);
	}

	public Set<V> members(K key) {
		return setOperationsReadOnly.members(key);
	}

	public Boolean move(K key, V value, K destKey) {
		return setOperations.move(key, value, destKey);
	}

	public V randomMember(K key) {
		return setOperationsReadOnly.randomMember(key);
	}

	public Set<V> distinctRandomMembers(K key, long count) {
		return setOperations.distinctRandomMembers(key, count);
	}

	public List<V> randomMembers(K key, long count) {
		return setOperations.randomMembers(key, count);
	}

	public Long remove(K key, Object... values) {
		return setOperations.remove(key, values);
	}

	public V pop(K key) {
		return setOperations.pop(key);
	}

	public Long size(K key) {
		return setOperationsReadOnly.size(key);
	}

	/**
	 * 
	 * @Title: scan
	 * @Description: TODO(描述)
	 * @param key
	 * @param options
	 * @return
	 * @author xiegr
	 * @date 2021-12-16 07:42:40
	 */
	public Cursor<V> scan(K key, ScanOptions options) {
		return setOperations.scan(key, options);
	}

	/***
	 * 
	 * @Title: getOperations
	 * @Description: TODO(描述)
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 08:38:46
	 */
	public RedisOperations<K, V> getOperations() {
		return setOperations.getOperations();
	}
}
