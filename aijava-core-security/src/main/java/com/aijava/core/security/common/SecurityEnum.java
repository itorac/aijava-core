package com.aijava.core.security.common;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName PermissionEnum.java
 * @Description 安全相关常量
 * @createTime 2021 年12 月20 日 15:33
 */
public enum SecurityEnum {

    /**
     * 存在Header中的token参数头名
     */
    HEADER_TOKEN("accessToken"),
    USER_CONTEXT("userContext"),
    JWT_SECRET("secret");

    String value;

    SecurityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
