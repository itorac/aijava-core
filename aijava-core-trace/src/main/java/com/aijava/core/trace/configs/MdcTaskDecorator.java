package com.aijava.core.trace.configs;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class MdcTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		 Map<String,String> map = MDC.getCopyOfContextMap();
	        return () -> {
	            try{
	                MDC.setContextMap(map);
	                runnable.run();
	            } finally {
	                MDC.clear();
	            }
	        };
	}

}
