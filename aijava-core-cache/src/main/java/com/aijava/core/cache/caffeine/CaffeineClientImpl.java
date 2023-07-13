/**  
 * @Title: CaffeineClientWrapper.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 07:15:30 
 */
package com.aijava.core.cache.caffeine;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @ClassName: CaffeineClientWrapper
 * @Description: 缓存
 * @author xiegr
 * @date 2021-12-17 07:15:30
 */
@Component
public class CaffeineClientImpl implements CaffeineClient {

	/**
	 * 定义缓存
	 */
	Cache<Object, Object> cache = Caffeine.newBuilder().initialCapacity(16)
			// 数量上限
			.maximumSize(10_000)
			// 过期机制
			.expireAfterWrite(1, TimeUnit.MINUTES)
			// 弱引用key
			.weakKeys()
			// 弱引用value
			.weakValues().build();

	/**
	 * @Title: put
	 * @Description: TODO(描述)
	 * @param key
	 * @param object
	 * @see CaffeineClient#put(java.lang.Object,
	 *      java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-18 04:28:23
	 */
	@Override
	public void put(Object key, Object object) {
		cache.put(key, object);
	}

	/**
	 * @Title: putAll
	 * @Description: TODO(描述)
	 * @param map
	 * @see CaffeineClient#putAll(java.util.Map)
	 * @author xiegr
	 * @date 2021-12-18 04:28:23
	 */
	@Override
	public void putAll(Map<Object, Object> map) {
		cache.putAll(map);
	}

	/**
	 * @Title: getIfPresent
	 * @Description: TODO(描述)
	 * @param key
	 * @return
	 * @see CaffeineClient#getIfPresent(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-18 04:28:23
	 */
	@Override
	public Object getIfPresent(Object key) {
		return cache.getIfPresent(key);
	}

	/**
	 * @Title: get
	 * @Description: 获取
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @see CaffeineClient#get(java.lang.Object,
	 *      java.util.function.Function)
	 * @author xiegr
	 * @date 2021-12-18 04:28:23
	 */
	@Override
	public Object get(Object key, @NonNull Function<Object, Object> mappingFunction) {
		return cache.get(key, mappingFunction);
	}

	/**
	 * @Title: invalidate
	 * @Description:根据key失效
	 * @param key
	 * @see CaffeineClient#invalidate(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-18 06:21:55
	 */
	@Override
	public void invalidate(@NonNull Object key) {
		cache.invalidate(key);
	}

	/**
	 * @Title: invalidateAll
	 * @Description: 根据keys失效
	 * @param keys
	 * @see CaffeineClient#invalidateAll(java.lang.Iterable)
	 * @author xiegr
	 * @date 2021-12-18 06:21:55
	 */
	@Override
	public void invalidateAll(@NonNull Iterable<@NonNull ?> keys) {
		cache.invalidateAll(keys);
	}

	/**
	 * @Title: invalidateAll
	 * @Description: 失效所有
	 * @see CaffeineClient#invalidateAll()
	 * @author xiegr
	 * @date 2021-12-18 06:21:55
	 */
	@Override
	public void invalidateAll() {
		cache.invalidateAll();
	}

}
