
package com.aijava.core.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

/**
 * 
 * @ClassName: JsonUtil
 * @Description: json转换
 * @author xiegr
 * @date 2021-12-02 10:09:09
 */
public class JsonUtil {

	public JsonUtil() {

	}

	/**
	 * @Title: toJavaObject
	 * @Description: 从json字符串中解析出java对象
	 * @param
	 * @param jsonStr json格式
	 * @param
	 * @param clazz   类
	 * @param
	 * @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz) {

		return JSON.parseObject(json, clazz);
	}

	/**
	 * TODO从json字符串中解析出List对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * 
	 * @Title: javaObjectToJson
	 * @Description: TODO 对象转化为JSON
	 * @param
	 * @param object 转化对象
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String objectToJSON(Object object) {
		if (object == null) {
			return null;
		}
		return JSON.toJSON(object).toString();
	}

	/**
	 * 
	 * @Title: objectToJSON
	 * @Description: TODO 对象转化为JSON
	 * @param
	 * @param object  转化对象
	 * @param
	 * @param mapping 过滤掉的字段
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public static String objectToJSON(Object object, ParserConfig mapping) {
		if (object == null) {
			return null;
		}
		return JSON.toJSON(object, mapping).toString();
	}
}
