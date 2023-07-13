/**  
 * @Title: CigAlipayClient.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2022-04-18 05:59:52 
 */
package com.aijava.core.security.alipay.bean;

import com.alipay.api.AlipayClient;

/**
 * @ClassName: CigAlipayClient
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2022-04-18 05:59:52
 */
public class CigAlipayClient {

	/**
	 * 阿里客户端
	 */
	private AlipayClient alipayClient;

	/**
	 * 第三方授权token
	 */
	private String appAuthToken;

	private String pId;

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return the alipayClient
	 */
	public AlipayClient getAlipayClient() {
		return alipayClient;
	}

	/**
	 * @param alipayClient the alipayClient to set
	 */
	public void setAlipayClient(AlipayClient alipayClient) {
		this.alipayClient = alipayClient;
	}

	/**
	 * @return the appAuthToken
	 */
	public String getAppAuthToken() {
		return appAuthToken;
	}

	/**
	 * @param appAuthToken the appAuthToken to set
	 */
	public void setAppAuthToken(String appAuthToken) {
		this.appAuthToken = appAuthToken;
	}

	/**
	 * @Title: CigAlipayClient
	 * @Description: CigAlipayClient构造函数
	 * @param alipayClient
	 * @param appAuthToken
	 * @author xiegr
	 * @date 2022-04-18 06:03:42
	 */
	public CigAlipayClient(AlipayClient alipayClient, String appAuthToken) {
		super();
		this.alipayClient = alipayClient;
		this.appAuthToken = appAuthToken;
	}

	/**
	 * @Title: CigAlipayClient
	 * @Description: CigAlipayClient构造函数
	 * @author xiegr
	 * @date 2022-04-18 06:03:46
	 */
	public CigAlipayClient() {
		super();
		// TODO Auto-generated constructor stub
	}

}
