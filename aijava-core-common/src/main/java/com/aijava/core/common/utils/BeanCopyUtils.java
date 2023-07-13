/**  
 * @Title: BeanCopyUtils.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-29 03:01:51 
 */
package com.aijava.core.common.utils;

import java.util.List;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * @ClassName: BeanCopyUtils
 * @Description: 对象转换工具
 * @author xiegr
 * @date 2021-11-29 03:01:51
 */
public class BeanCopyUtils {

	/**
	 * 对象拷贝
	 * <p>
	 * 
	 * 拷贝不支持对象里面嵌套对象拷贝
	 * 
	 * @Title: convertFrom
	 * @param source  原对象
	 * @param target  目标对象
	 *
	 * @param @return 设定文件
	 * @return T 返回类型
	 *
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T convertToObject(Object source, Object target) {
		Preconditions.checkNotNull(source, "source can't be null");
		Preconditions.checkNotNull(target, "target can't be null");
		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
		copier.copy(source, target, null);
		return (T) target;
	}

	/**
	 * List对象转换，不支持嵌套转换
	 * 
	 * @Title: convertFromList
	 * 
	 * @param sourceList 原list
	 * @param clazz      目标对象
	 * @param @return    设定文件
	 * @return List<T> 返回类型
	 *
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static final <T> List<T> convertToList(List<? extends Object> sourceList, Class<T> clazz) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return null;
		}
		BeanCopier copier = BeanCopier.create(sourceList.get(0).getClass(), clazz, false);
		List<T> list = Lists.newArrayList();
		// 这里反射性能可能不佳，要测试
		try {
			Object target = null;
			for (Object t : sourceList) {
				target = clazz.newInstance();
				copier.copy(t, target, null);
				list.add((T) target);
			}
		} catch (Exception e) {
			throw new RuntimeException("other exception", e);
		}
		return list;
	}
}
