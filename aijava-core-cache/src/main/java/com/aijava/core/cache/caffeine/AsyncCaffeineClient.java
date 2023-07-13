/**  
 * @Title: AsyncCaffeineClient.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-18 10:23:15 
 */
package com.aijava.core.cache.caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @ClassName: AsyncCaffeineClient
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-18 10:23:15
 */
public interface AsyncCaffeineClient {

	/**
	 * 
	 * @Title: set
	 * @Description: 设置值
	 * @param key
	 * @param object
	 * @author xiegr
	 * @date 2021-12-17 07:17:13
	 */
	public void put(Object key, CompletableFuture<Object> object);

	/**
	 * 
	 * @Title: getIfPresent
	 * @Description:获取缓存
	 * @param key
	 * @return
	 * @author xiegr
	 * @date 2021-12-18 04:27:56
	 */
	public CompletableFuture<Object> getIfPresent(Object key);

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(描述)
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @author xiegr
	 * @date 2021-12-18 10:10:57
	 */
	public CompletableFuture<Object> get(Object key, @NonNull Function<Object, Object> mappingFunction);

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(描述)
	 * @param key
	 * @param mappingFunction
	 * @return
	 * @author xiegr
	 * @date 2021-12-18 11:09:32
	 */
	public CompletableFuture<Object> get(Object key,
			BiFunction<Object, Executor, CompletableFuture<Object>> mappingFunction);

}
