/**  
 * @Title: BaseErrorCode.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-11-30 10:06:19 
 */
package com.aijava.core.error;

import java.util.Map;

/**
 * @ClassName: BaseErrorCode
 * @Description: 定义错误码接口
 * @author xiegr
 * @date 2021-11-30 10:06:19
 */
public interface BaseErrorCode {

	/**
	 * 
	 * @Title: getCode
	 * @Description:获取编码
	 * @return
	 * @author xiegr
	 * @date 2021-12-08 06:06:37
	 */
	public int getCode();

	/**
	 * 
	 * @Title: setErrorContent
	 * @Description: 设置错误内容
	 * @param content
	 * @author xiegr
	 * @date 2021-12-08 06:06:12
	 */
	public void setErrorContent(Map<String, Object> content);

}
