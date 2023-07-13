package com.aijava.core.security.alipay.bean;

import java.io.Serializable;

/**
 * @ClassName: AliappConfig
 * @Description: 配置文件
 * @author xiegr
 * @date 2022-04-18 11:51:11
 */
public class AliappConfig implements Serializable {

	private static final long serialVersionUID = -30614144717318422L;

	/**
	 * 网关
	 */
	public final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

	/**
	 * 支付宝appid
	 */
	private String appId;
	/**
	 * 应用公钥
	 */
	private String appPublicKey;
	/**
	 * 应用私钥
	 */
	private String appPrivateKey;

	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	
	/**
	 * 参数请求格式
	 */
	public final String FORMAT = "json";
	
	/**
	 * 字符集
	 */
	public final String CHARSET = "UTF-8";

	/**
	 * 加密方式
	 */
	public final String SIGN_TYPE = "RSA2";

	/**
	 * 内容加密方式
	 */
	public final String ENCRYPY_TYPE = "AES";

	/**
	 * 内容加密密钥
	 */
	private String encryptKey;
	
	
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppPublicKey() {
		return appPublicKey;
	}

	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey = appPublicKey;
	}

	public String getAppPrivateKey() {
		return appPrivateKey;
	}

	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	/**
	 * @param encryptKey the encryptKey to set
	 */
	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	/**
	 * @return the encryptKey
	 */
	public String getEncryptKey() {
		return encryptKey;
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
	 * @Title: AliappConfig
	 * @Description: AliappConfig构造函数
	 * @author xiegr
	 * @date 2022-04-15 06:07:16
	 */
	public AliappConfig() {
		super();
	}

}
