package com.aijava.core.dal.datasource.helpers;

/**
 * 
 * @ClassName: DatabaseForce
 * @Description: 强制主库
 * @author xiegr
 * @date 2021-12-06 05:29:44
 */
public class DatabaseForce {

	private static final ThreadLocal<Boolean> forceMaster = new InheritableThreadLocal<Boolean>();

	/**
	 * 
	 * @Title: forceMaster
	 * @Description:强制数据库操作使用主数据库master,不使用slave
	 * @author xiegr
	 * @date 2021-12-01 03:56:31
	 */
	public static void forceMaster() {
		forceMaster.set(Boolean.TRUE);
	}

	/**
	 * 
	 * @Title: masterOrSlave
	 * @Description:每次rest请求过来时,初始化数据库路由 读操作默认会平均分摊到主和从
	 * @author xiegr
	 * @date 2021-12-01 03:56:42
	 */
	public static void masterOrSlave() {
		forceMaster.set(Boolean.FALSE);
	}

	/**
	 * 
	 */
	public static void remove() {
		forceMaster.remove();
	}

	/**
	 * @Title: isForceMaster
	 * @Description: 获取"强制路由"配置, dal框架中路由时使用
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 03:57:03
	 */
	public static Boolean isForceMaster() {
		Boolean force = forceMaster.get();
		if (force == null) {
			return Boolean.FALSE;
		}
		return force;
	}

}
