package com.aijava.core.trace.configs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.aijava.core.trace.constant.LogTraceConstant;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.UUID;

/**
 * @author xgr
 * @data 2023/2/26
 * @apiNote
 */
@Component
@Configurable
public class OpenFeignRequestInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(LogTraceConstant.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
            MDC.put(LogTraceConstant.TRACE_ID, traceId);
        }
        requestTemplate.header(LogTraceConstant.TRACE_ID, traceId);
    }
}