package com.aijava.core.security.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName CigToken.java
 * @Description Token实体类
 * @createTime 2021 年12 月20 日 19:46
 */
@Getter
@Setter
public class CigToken {
	/**
	 * 刷新token
	 */
	private String accessToken;

	/**
	 * 刷新token
	 */
	private String refreshToken;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
