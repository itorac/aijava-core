package com.aijava.core.trace.configs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.aijava.core.trace.constant.LogTraceConstant;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xgr
 * @data 2023/2/27
 * @apiNote
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		// RestTemplate支持traceId传递
		ClientHttpRequestInterceptor tracerInterceptor = new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				HttpHeaders headers = request.getHeaders();
				String traceId = MDC.get(LogTraceConstant.TRACE_ID);
				if (StringUtils.isEmpty(traceId)) {
					traceId = UUID.randomUUID().toString().replace("-", "");
					MDC.put(LogTraceConstant.TRACE_ID, traceId);
				}
				headers.add(LogTraceConstant.TRACE_ID, traceId);
				return execution.execute(request, body);
			}
		};
		restTemplate.getInterceptors().add(tracerInterceptor);
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory okHttpClientRequestFactory() {
		// 使用自定义的okHttpClient
		return new OkHttp3ClientHttpRequestFactory(okHttpClient());
	}

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(5, TimeUnit.SECONDS).connectionPool(new ConnectionPool(100, 3000, TimeUnit.SECONDS))
				// 设置代理
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
				// 拦截器
				.addInterceptor(new OkHttpLoggerInterceptor()).build();
	}
}