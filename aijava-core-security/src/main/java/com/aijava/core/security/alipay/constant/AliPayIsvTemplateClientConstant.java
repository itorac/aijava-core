package com.aijava.core.security.alipay.constant;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AliPayIsvClientConstant.java
 * @Description 支付宝三方ISV客户端初始化使用常量
 * @createTime 2022 年04 月12 日 13:58
 */
public class AliPayIsvTemplateClientConstant {
    /**
     * 支付宝网关
     */
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    /**
     * ISV三方的小程序应用的ID(微秒纪元餐饮模版)
     */
    public static final String APPID = "";

    /**
     * 应用公钥
     */
    public static final String APP_PUBLIC_KEY = "";
    
    /**
     * 应用私钥
     */
    public static final String APP_PRIVATE_KEY = "";
   
    /**
     * 参数请求格式
     */
    public static final String FORMAT = "json";
    /**
     * 字符集
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_PUBLIC_KEY = "";
    /**
     * 加密方式
     */
    public static final String SIGN_TYPE = "RSA2";
    /**
     * 加解密接口密钥
     */
    public static final String DECRYPT_KEY = "";
    
    /**
     * 加密方式
     */
    public static final String DECRYPT_TYPE = "AES";
}
