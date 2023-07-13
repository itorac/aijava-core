/**  
 * @Title: CIGZsetOperations.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:14:05 
 */
package com.aijava.core.redis.component;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CIGZsetOperations
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-15 03:14:05
 */
@Component("cigZSetOperations")
public class CIGZSetOperations<K, V> implements BeanNameAware {

	@Resource(name = "redisTemplate")
	private ZSetOperations<K, V> zsetOperations;
	
	@Resource(name = "redisTemplateReadOnly")
	private ZSetOperations<K, V> zsetOperationsReadOnly;

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
	 * @date 2021-12-15 03:19:35
	 */
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return zsetOperations.intersectAndStore(key, otherKey, destKey);
	}

	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		return zsetOperations.intersectAndStore(key, otherKeys, destKey);
	}

	public Long unionAndStore(K key, K otherKey, K destKey) {
		return zsetOperations.unionAndStore(key, otherKey, destKey);
	}

	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		return zsetOperations.unionAndStore(key, otherKeys, destKey);
	}

	public Set<V> range(K key, long start, long end) {
		return zsetOperationsReadOnly.range(key, start, end);
	}

	public Set<V> reverseRange(K key, long start, long end) {
		return zsetOperationsReadOnly.reverseRange(key, start, end);
	}

	public Set<ZSetOperations.TypedTuple<V>> rangeWithScores(K key, long start, long end) {
		return zsetOperationsReadOnly.rangeWithScores(key, start, end);
	}

	public Set<ZSetOperations.TypedTuple<V>> reverseRangeWithScores(K key, long start, long end) {
		return zsetOperationsReadOnly.reverseRangeWithScores(key, start, end);
	}

	public Set<V> rangeByScore(K key, double min, double max) {
		return zsetOperationsReadOnly.rangeByScore(key, min, max);
	}

	public Set<V> rangeByScore(K key, double min, double max, long offset, long count) {
		return zsetOperationsReadOnly.rangeByScore(key, min, max, offset, count);
	}

	public Set<V> reverseRangeByScore(K key, double min, double max) {
		return zsetOperationsReadOnly.reverseRangeByScore(key, min, max);
	}

	public Set<V> reverseRangeByScore(K key, double min, double max, long offset, long count) {
		return zsetOperationsReadOnly.reverseRangeByScore(key, min, max, offset, count);
	}

	public Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max) {
		return zsetOperationsReadOnly.rangeByScoreWithScores(key, min, max);
	}

	public Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset,
			long count) {
		return zsetOperationsReadOnly.rangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max) {
		return zsetOperationsReadOnly.reverseRangeByScoreWithScores(key, min, max);
	}

	public Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max, long offset,
			long count) {
		return zsetOperationsReadOnly.reverseRangeByScoreWithScores(key, min, max, offset, count);
	}

	public Boolean add(K key, V value, double score) {
		return zsetOperations.add(key, value, score);
	}

	public Long add(K key, Set<ZSetOperations.TypedTuple<V>> typedTuples) {
		return zsetOperations.add(key, typedTuples);
	}

	public Double incrementScore(K key, V value, double delta) {
		return zsetOperations.incrementScore(key, value, delta);
	}

	public Long rank(K key, Object o) {
		return zsetOperationsReadOnly.rank(key, o);
	}

	public Long reverseRank(K key, Object o) {
		return zsetOperationsReadOnly.reverseRank(key, o);
	}

	public Double score(K key, Object o) {
		return zsetOperationsReadOnly.score(key, o);
	}

	public Long remove(K key, Object... values) {
		return zsetOperations.remove(key, values);
	}

	public Long removeRange(K key, long start, long end) {
		return zsetOperations.removeRange(key, start, end);
	}

	public Long removeRangeByScore(K key, double min, double max) {
		return zsetOperations.removeRangeByScore(key, min, max);
	}

	public Long count(K key, double min, double max) {
		return zsetOperations.count(key, min, max);
	}

	/**
	 * Returns the number of elements of the sorted set stored with given
	 * {@code key}.
	 *
	 * @param key
	 * @return
	 * @see #zCard(Object)
	 */

	public Long size(K key) {
		return zsetOperationsReadOnly.size(key);
	}

	/**
	 * Returns the number of elements of the sorted set stored with given
	 * {@code key}.
	 *
	 * @param key
	 * @return
	 * @since 1.3
	 */

	public Long zCard(K key) {
		return zsetOperationsReadOnly.zCard(key);
	}

	/**
	 * @param key
	 * @param options
	 * @return
	 * @since 1.4
	 */

	public Cursor<ZSetOperations.TypedTuple<V>> scan(K key, ScanOptions options) {
		return zsetOperations.scan(key, options);
	}

	/**
	 * 
	 * @Title: getOperations
	 * @Description: 可以执行lua
	 * @return
	 * @author xiegr
	 * @date 2021-12-15 08:38:11
	 */
	public RedisOperations<K, V> getOperations() {
		return zsetOperations.getOperations();
	}

}
