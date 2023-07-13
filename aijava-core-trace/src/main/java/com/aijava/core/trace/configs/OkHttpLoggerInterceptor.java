package com.aijava.core.trace.configs;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.aijava.core.trace.constant.LogTraceConstant;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

@Slf4j
public class OkHttpLoggerInterceptor implements Interceptor {

	@SuppressWarnings("deprecation")
	@Override
	public Response intercept(Chain chain) throws IOException {
		// 目前默认使用的是post请求，且格式是utf8，使用的是application/json
		Request request = chain.request();
		// copy all headers to newheaders
		Headers.Builder headers = request.headers().newBuilder();
		// add traceid | spanid | parentspanid to headers
		if (StringUtils.isNotBlank(MDC.get(LogTraceConstant.TRACE_ID))) {
			headers.add(LogTraceConstant.TRACE_ID, MDC.get(LogTraceConstant.TRACE_ID));// 设置X-B3-TraceId
		}
		if (StringUtils.isNotBlank(LogTraceConstant.TRACE_ID)) {
			headers.add(LogTraceConstant.TRACE_ID, MDC.get(LogTraceConstant.TRACE_ID));
		}
		String spanIdNew = UUID.randomUUID().toString().replace("-", "");
		headers.add(LogTraceConstant.TRACE_ID, spanIdNew);// 设置X-B3-SpanId供外部使用
		// rebuild a new request
		request = request.newBuilder().headers(headers.build()).build();

		Buffer buffer = new Buffer();
		request.body().writeTo(buffer);
		String requestBody = buffer.readUtf8();
		String requestUrl = request.url().toString();
		String[] url = requestUrl.split("/");
		log.info("[Request Addr]: " + request.url());
		log.info("[Service Name]: " + url[url.length - 1] + "; [Request Body]: " + requestBody);
		Response response = chain.proceed(request);
		BufferedSource source = response.body().source();
		source.request(Long.MAX_VALUE);
		buffer = source.buffer();
		String responseBody = buffer.readUtf8();
		log.info("[Response Status Code]: " + response.code() + "; [Resonse Status Text]: "
				+ HttpStatus.valueOf(response.code()).name());
		log.info("[Service Name]: " + url[url.length - 1] + "; [Response Body]: " + responseBody);
		return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBody)).build();
	}
}
