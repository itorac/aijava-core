package com.aijava.core.trace.configs;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aijava.core.common.utils.JsonUtil;
import com.aijava.core.trace.constant.LogTraceConstant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestLogConfig {

    @Value("${restlog.request.flag:false}")
    private boolean restRequestFlag=false;

    @Value("${restlog.response.flag:false}")
    private boolean restResponseFlag=false;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object restLogs(ProceedingJoinPoint joinPoint) throws Throwable {

        List<Object> args = Arrays.stream(joinPoint.getArgs()).filter(s ->
                !(s instanceof ServletResponse) && !(s instanceof ServletRequest) && !(s instanceof InputStreamSource) && !(s instanceof FileInputStream)
        ).collect(Collectors.toList());
        long beginTime = System.currentTimeMillis();
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String requestURI = ra.getRequest().getRequestURI();
        String requestMethod = ra.getRequest().getMethod();
        if(restRequestFlag) {
            log.info("logback-restrequest:rest请求：method:{}, args:{}, path:{}, requestMethod:{}", joinPoint.getSignature(), JsonUtil.objectToJSON(args), requestURI, requestMethod);
        }
        Object proceed = joinPoint.proceed();
        if(restResponseFlag) {
            log.info("logback-restresponse:rest响应,时间 time:{} milliseconds，method:{}, response:{}, path:{}, requestMethod:{}", System.currentTimeMillis() - beginTime, joinPoint.getSignature(), JsonUtil.objectToJSON(proceed), requestURI, requestMethod);
        }
        return proceed;
    }

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void logAsyncPointCut() {
    }

    @Around("logAsyncPointCut()")
    public Object aroundAsync(ProceedingJoinPoint point) throws Throwable {
        return logMdc(point);
    }

    @Pointcut("@annotation(org.springframework.context.event.EventListener)")
    public void logEventPointCut() {
    }

    @Around("logEventPointCut()")
    public Object aroundEvent(ProceedingJoinPoint point) throws Throwable {
        return logMdc(point);
    }

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledPointCut() {
    }

    @Around("scheduledPointCut()")
    public Object aroundScheduled(ProceedingJoinPoint point) throws Throwable {
        return logMdc(point);
    }

    private Object logMdc(ProceedingJoinPoint point) throws Throwable {
        String traceId = MDC.get(LogTraceConstant.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
            MDC.put(LogTraceConstant.TRACE_ID, traceId);
        }
        Object result = point.proceed();
        MDC.remove(LogTraceConstant.TRACE_ID);
        return result;
    }
}
