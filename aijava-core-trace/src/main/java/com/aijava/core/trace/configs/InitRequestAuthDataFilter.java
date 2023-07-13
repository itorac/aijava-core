package com.aijava.core.trace.configs;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aijava.core.trace.constant.LogTraceConstant;

public class InitRequestAuthDataFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String reqId = request.getParameter(LogTraceConstant.TRACE_ID);
		if (reqId == null || "".equals(reqId.trim())) {
			reqId = request.getHeader(LogTraceConstant.TRACE_ID);
		}
		if (reqId == null || "".equals(reqId.trim())) {
			reqId = UUID.randomUUID().toString().replace("-", "");
		}
		MDC.put(LogTraceConstant.TRACE_ID, reqId);
		// 该traceId是让日志打印出来的key值
		MDC.put(LogTraceConstant.TRACE_ID, reqId);
		/** 防止MDC 多次生成，引入的sq-component-log 有拦截 header 头信息 **/
		logger.info("header:" + request.getHeader(LogTraceConstant.TRACE_ID));
		filterChain.doFilter(request, response);
		MDC.remove(LogTraceConstant.TRACE_ID);
	}

}
