/**  
 * @Title: AsyncCaffeineClientImpl.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-18 10:23:36 
 */
package com.aijava.core.cache.caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @ClassName: AsyncCaffeineClientImpl
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-18 10:23:36
 */
@Component
public class AsyncCaffeineClientImpl implements AsyncCaffeineClient {

	/**
	 * 定义缓存
	 */
	AsyncCache<Object, Object> cache = Caffeine.newBuilder().initialCapacity(16)
			// 数量上限
			.maximumSize(10_000)
			// 过期机制
			.expireAfterWrite(1, TimeUnit.MINUTES).buildAsync();

	/**
	 * @Title: put
	 * @Description: TODO(描述)
	 * @param key
	 * @param object
	 * @see AsyncCaffeineClient#put(java.lang.Object,
	 *      java.util.concurrent.CompletableFuture)
	 * @author xiegr
	 * @date 2021-12-18 10:29:57
	 */
	@Override
	public void put(Object key, CompletableFuture<Object> object) {
		cache.put(key, object);
	}

	/**
	 * @Title: getIfPresent
	 * @Description: TODO(描述)
	 * @param key
	 * @return
	 * @see AsyncCaffeineClient#getIfPresent(java.lang.Object)
	 * @author xiegr
	 * @date 2021-12-18 10:29:57
	 */
	@Override
	public CompletableFuture<Object> getIfPresent(Object key) {
		return cache.getIfPresent(key);
	}

	/**
	 * @Title: get
	 * @Description: TODO(描述)
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @see AsyncCaffeineClient#get(java.lang.Object,
	 *      java.util.function.Function)
	 * @author xiegr
	 * @date 2021-12-18 10:37:31
	 */
	@Override
	public CompletableFuture<Object> get(Object key, @NonNull Function<Object, Object> mappingFunction) {
		return cache.get(key, mappingFunction);
	}

	/**
	 * @Title: get
	 * @Description: TODO(描述)
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @see AsyncCaffeineClient#get(java.lang.Object,
	 *      java.util.function.BiFunction)
	 * @author xiegr
	 * @date 2021-12-18 11:13:11
	 */
	@Override
	public CompletableFuture<Object> get(Object key,
			BiFunction<Object, Executor, CompletableFuture<Object>> mappingFunction) {
		return cache.get(key, mappingFunction);
	}

}
