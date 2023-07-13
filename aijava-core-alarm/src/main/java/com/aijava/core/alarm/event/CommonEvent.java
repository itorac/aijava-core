package com.aijava.core.alarm.event;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @ClassName: CommonEvent
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-16 09:04:32
 */
public abstract class CommonEvent extends ApplicationEvent {

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author xiegr
	 * @date 2021-12-16 08:56:16
	 */
	private static final long serialVersionUID = -9051414448163738428L;

	/**
	 * @Title: MyEvent
	 * @Description: MyEvent构造函数
	 * @param source
	 * @author xiegr
	 * @date 2021-12-16 08:56:59
	 */
	public CommonEvent(String name) {
		super(name);
		this.name = name;
	}

	// 事件名称，线程名称
	final String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
