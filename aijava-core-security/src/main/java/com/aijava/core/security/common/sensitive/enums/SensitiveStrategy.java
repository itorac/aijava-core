package com.aijava.core.security.common.sensitive.enums;

import java.util.function.Function;

/**
 * @ClassName SensitiveStrategy.java
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @Description 敏感策略枚举
 * @createTime 2021 年12 月20 日 18:57
 */public enum SensitiveStrategy {
    /**
     * Username sensitive strategy.
     */
    USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),
    /**
     * Id card sensitive type.
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),
    /**
     * Phone sensitive type.
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    /**
     * Email sensitive type.
     */
    EMAIL(s -> s.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2")),
    /**
     * Name sensitive type.
     */
    NAME(s -> s.replaceAll("^(.{3}).+(.{3})$", "$1*****$2")),
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));

    private final Function<String, String> desensitizer;

    SensitiveStrategy(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
