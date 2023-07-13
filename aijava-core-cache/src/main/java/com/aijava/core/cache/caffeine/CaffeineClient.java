/**  
 * @Title: CaffeineClient.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 07:09:02 
 */
package com.aijava.core.cache.caffeine;

import java.util.Map;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.google.errorprone.annotations.CompatibleWith;

/**
 * @ClassName: CaffeineClient
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 07:09:02
 */
public interface CaffeineClient {

	/**
	 * 
	 * @Title: set
	 * @Description: 设置值
	 * @param key
	 * @param object
	 * @author xiegr
	 * @date 2021-12-17 07:17:13
	 */
	public void put(Object key, Object object);

	/**
	 * 
	 * @Title: putAll
	 * @Description: TODO(描述)
	 * @param map
	 * @author xiegr
	 * @date 2021-12-18 10:10:48
	 */
	public void putAll(Map<Object, Object> map);

	/**
	 * 
	 * @Title: getIfPresent
	 * @Description:获取缓存
	 * @param key
	 * @return
	 * @author xiegr
	 * @date 2021-12-18 04:27:56
	 */
	public Object getIfPresent(Object key);

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
	public Object get(Object key, @NonNull Function<Object, Object> mappingFunction);

	/**
	 * 
	 * @Title: invalidate
	 * @Description: 失效单个key
	 * @param key
	 * @author xiegr
	 * @date 2021-12-18 06:20:48
	 */
	public void invalidate(@NonNull @CompatibleWith("K") Object key);

	/**
	 * 
	 * @Title: invalidateAll
	 * @Description:根据key失效
	 * @param keys
	 * @author xiegr
	 * @date 2021-12-18 06:20:37
	 */
	public void invalidateAll(@NonNull Iterable<@NonNull ?> keys);

	/**
	 * 
	 * @Title: invalidateAll
	 * @Description:失效所有的key，谨慎使用
	 * @author xiegr
	 * @date 2021-12-18 06:20:27
	 */
	public void invalidateAll();

}
