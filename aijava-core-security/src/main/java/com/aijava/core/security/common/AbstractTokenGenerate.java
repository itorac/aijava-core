package com.aijava.core.security.common;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName AbstractTokenGenerate.java
 * @Description 抽象token，定义生成token类
 * @createTime 2021 年12 月20 日 19:48
 */
public abstract class AbstractTokenGenerate {
    /**
     * 生成token
     *
     * @param userName 用户名
     * @param longTerm 是否长时间有效
     * @return TOKEN对象
     */
    public abstract CigToken createToken(String userName, Boolean longTerm);

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return token
     */
    public abstract CigToken refreshToken(String refreshToken);

    /**
     * 默认role
     */
    public UserEnums role = UserEnums.MANAGER;
}
