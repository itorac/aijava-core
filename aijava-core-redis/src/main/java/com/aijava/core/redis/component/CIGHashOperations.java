/**  
 * @Title: CIGHASHOperations.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:16:16 
 */
package com.aijava.core.redis.component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CIGHASHOperations
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:16:16
 */
@Component("cigHashOperations")
public class CIGHashOperations<H, HK, HV> implements BeanNameAware {

	@Resource(name = "redisTemplate")
	private HashOperations<H, HK, HV> hashOperations;

	/**
	 * 读
	 */
	@Resource(name = "redisTemplateReadOnly")
	private HashOperations<H, HK, HV> hashOperationsReadOnly;

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
	 * @date 2021-12-15 03:18:47
	 */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public void delete(H key, Object... hashKeys) {
		hashOperations.delete(key, hashKeys);
	}

	public Boolean hasKey(H key, Object hashKey) {
		return hashOperationsReadOnly.hasKey(key, hashKey);
	}

	public HV get(H key, Object hashKey) {
		return hashOperationsReadOnly.get(key, hashKey);
	}

	public List<HV> multiGet(H key, Collection<HK> hashKeys) {
		return hashOperationsReadOnly.multiGet(key, hashKeys);
	}

	public Long increment(H key, HK hashKey, long delta) {
		return hashOperations.increment(key, hashKey, delta);
	}

	public Double increment(H key, HK hashKey, double delta) {
		return hashOperations.increment(key, hashKey, delta);
	}

	public Set<HK> keys(H key) {
		return hashOperationsReadOnly.keys(key);
	}

	public Long size(H key) {
		return hashOperationsReadOnly.size(key);
	}

	public void putAll(H key, Map<? extends HK, ? extends HV> m) {

		hashOperations.putAll(key, m);
	}

	public void put(H key, HK hashKey, HV value) {
		hashOperations.put(key, hashKey, value);
	}

	public Boolean putIfAbsent(H key, HK hashKey, HV value) {
		return hashOperations.putIfAbsent(key, hashKey, value);
	}

	public List<HV> values(H key) {
		return hashOperationsReadOnly.values(key);
	}

	public Map<HK, HV> entries(H key) {
		return hashOperationsReadOnly.entries(key);
	}

	/**
	 * @param key
	 * @param options
	 * @return
	 * @since 1.4
	 */

	public Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options) {
		return hashOperations.scan(key, options);
	}

	/**
	 * 
	 * @Title: getOperations
	 * @Description: TODO(描述)
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 08:40:29
	 */
	public RedisOperations<H, ?> getOperations() {
		return hashOperations.getOperations();
	}

}
