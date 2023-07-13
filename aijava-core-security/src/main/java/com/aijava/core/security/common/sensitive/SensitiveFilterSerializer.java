package com.aijava.core.security.common.sensitive;

import com.aijava.core.security.common.sensitive.enums.SensitiveStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName SensitiveFilterSerializer.java
 * @Description 调用者需要继承该类，实现desensitization方法
 * @createTime 2021 年12 月20 日 19:04
 */
public abstract class SensitiveFilterSerializer extends JsonSerializer<String>
        implements ContextualSerializer, ApplicationContextAware {

    private SensitiveStrategy strategy;

    /**
     * 默认脱敏级别：
     * 0：不做脱敏处理
     * 1：管理平台用户手机号等信息脱敏
     * 2：商家端信息脱敏（为2时，表示管理端，商家端同时脱敏）
     * <p>
     * PS:
     */
    private final byte sensitiveLevel = 0;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 字段序列化处理
        gen.writeString(strategy.desensitizer().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {

        // 判定是否 需要脱敏处理
        if (desensitization()) {
            //获取敏感枚举
            Sensitive annotation = property.getAnnotation(Sensitive.class);
            //如果有敏感注解，则加入脱敏规则
            if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
                this.strategy = annotation.strategy();
                return this;
            }
        }
        return prov.findValueSerializer(property.getType(), property);
    }

    /**
     * 是否需要进行脱敏
     * 可通过重写该方法来定义服务内相关数据脱敏设置
     *
     * @return
     */
    private boolean desensitization() {
        return sensitiveLevel > 0;
    }
}
