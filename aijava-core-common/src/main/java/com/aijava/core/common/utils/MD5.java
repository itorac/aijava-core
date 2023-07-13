package com.aijava.core.common.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @ClassName: MD5
 * @Description: MD5 工具类
 * @author xiegr
 * @date 2021-11-29 09:07:35
 */
public class MD5 {

	private static final Logger logger = LoggerFactory.getLogger(MD5.class);

	/**
	 * 对消息计算MD5， 结果用Hex（十六进制）编码
	 *
	 * @param message 消息
	 * @return MD5之后的结果
	 */
	public static String md5(String message) {
		return new String(Hex.encodeHex(md5Digest(message)));
	}

	/**
	 * 
	 * @Title: md5
	 * @Description: 对消息计算MD5， 结果用Hex（十六进制）编码,增加盐值
	 * @param message 消息
	 * @param salt    盐值
	 * @return
	 * @author xiegr
	 * @date 2022-01-19 04:36:43
	 */
	public static String md5(String message, String salt) {
		return new String(Hex.encodeHex(md5Digest(salt + message)));
	}

	/**
	 * 计算MD5
	 *
	 * @param message 原始消息
	 * @return MD5之后的记过
	 */
	private static byte[] md5Digest(String message) {

		byte[] md5Bytes = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md5Bytes = md.digest(message.getBytes(Charsets.UTF_8));

		} catch (NoSuchAlgorithmException e) {
			logger.error("md5 error: NoSuchAlgorithmException");
		}

		return md5Bytes;
	}
	
}
