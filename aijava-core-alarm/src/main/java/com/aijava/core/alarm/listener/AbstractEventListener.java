/**  
 * @Title: AbstractEventListener.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-17 09:21:38 
 */
package com.aijava.core.alarm.listener;

import java.util.HashMap;
import java.util.Map;

import com.aijava.core.alarm.event.CommonEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import com.aijava.core.common.utils.JsonUtil;
import com.aijava.core.common.utils.LocalIPFetcher;

/**
 * @ClassName: AbstractEventListener
 * @Description: 抽象监听类
 * @author xiegr
 * @date 2021-12-17 09:21:38
 */
public abstract class AbstractEventListener<T extends CommonEvent> implements ApplicationListener<T> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 本地ip
	 */
	private final String localIP = LocalIPFetcher.fetchLocalIP();

	/**
	 * 监控
	 */
	final String DB_DEFAULT = "cig_monitor";

	/**
	 * 事件库
	 */
	final String DB_EVENT = "cig_event";

	/**
	 * 系统名称
	 */
	@Value(value = "${spring.application.name:default}")
	private String tag_context;

	/**
	 * 
	 * @Title: onApplicationEvent
	 * @Description: TODO(描述)
	 * @param event
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 * @author xiegr
	 * @date 2021-12-17 02:07:53
	 */
	@Override
	public void onApplicationEvent(T event) {

		logger.debug("onApplicationEvent receive event:{}", event);

		if (event == null || StringUtils.isEmpty(event.getName()) || this.isIgnore(event)) {
			logger.debug("event is empty of ignore");
			return;
		}

		// 参数
		Map<String, Object> params = new HashMap<>();
		params.put("ip", localIP);
		this.addArgs(event, params);

		// tags
		Map<String, String> tags = new HashMap<>();
		tags.put("context", tag_context);
		tags.put("event", event.getName());
		tags.put("hostAddress", localIP);
		this.addTags(event, tags);
		// 保证所有的tag value都不能为空
		for (Map.Entry<String, String> tagEntry : tags.entrySet()) {
			String key = tagEntry.getKey();
			String value = tagEntry.getValue();
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
				logger.warn("can not find tag value at: {}", event);
				return;
			}
		}
		String catalog = this.getCatalog(event);

		/**
		 * 这里可以替换成写入到时序数据库,hbase或者elk
		 */
		logger.info("listener event:{},name:{},tags:{},params:{},catalog:{},", event, event.getName(),
				JsonUtil.objectToJSON(tags), JsonUtil.objectToJSON(params), catalog);
	}

	/**
	 * @return the tag_context
	 */
	public String getTag_context() {
		return tag_context;
	}

	/**
	 * @param tag_context the tag_context to set
	 */
	public void setTag_context(String tag_context) {
		this.tag_context = tag_context;
	}

	/**
	 * 忽略某个事件
	 *
	 * @param event 事件
	 * @return 是否忽略这个事件
	 */
	protected boolean isIgnore(final T event) {
		return false;
	}

	/**
	 * 设置参数
	 *
	 * @param args args
	 */
	protected void addArgs(final T event, final Map<String, Object> args) {

	}

	/**
	 * 设置tags标识
	 *
	 * @param tags tags
	 */
	protected void addTags(final T event, final Map<String, String> tags) {

	}

	/**
	 * 获取catalog
	 *
	 * @param event
	 * @return catalog
	 */
	abstract String getCatalog(final T event);

	/**
	 * 
	 * @Title: getDatabase
	 * @Description: 保存到那个数据库中
	 * @return
	 * @author xiegr
	 * @date 2021-12-17 02:08:22
	 */
	protected String getDatabase() {
		return DB_DEFAULT;
	}

}
