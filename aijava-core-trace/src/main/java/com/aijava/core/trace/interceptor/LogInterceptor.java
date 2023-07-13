package com.aijava.core.trace.interceptor;

import com.aijava.core.trace.constant.LogTraceConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author xgr
 * @data 2023/2/26
 * @apiNote
 */
@Component
public class LogInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String traceId = request.getHeader(LogTraceConstant.TRACE_ID);
    	
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        MDC.put(LogTraceConstant.TRACE_ID, traceId);
        //多线程
        //Map<String, String> context = MDC.getCopyOfContextMap();
        response.setHeader(LogTraceConstant.TRACE_ID, traceId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        MDC.remove(LogTraceConstant.TRACE_ID);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        MDC.remove(LogTraceConstant.TRACE_ID);
    }
}
