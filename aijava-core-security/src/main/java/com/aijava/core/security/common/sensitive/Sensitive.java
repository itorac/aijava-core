package com.aijava.core.security.common.sensitive;

import com.aijava.core.security.common.sensitive.enums.SensitiveStrategy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName Sensitive.java
 * @Description 脱敏注解
 * *! 使用方法： 类中属性可直接使用注解：@Sensitive(strategy = SensitiveStrategy.PHONE)
 * @createTime 2021 年12 月20 日 19:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveFilterSerializer.class)
public @interface Sensitive {
    SensitiveStrategy strategy();
}
