package com.aijava.core.dal.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: Database
 * @Description: 默认数据源
 * @author xiegr
 * <pre>
 * 使用方法:
 * 在服务层的方法名上添加如下注解
 * @Transactional
 * @Database(DataSource = "数据库名", ForceMaster = true)
 * </pre>
 * @date 2021-12-01 03:40:27
 */
@Target({ElementType.TYPE,  ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Database {

	/**
	 * @Title: DataSource
	 * @Description: 指定数据源,配合@Transactional一起使用
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 03:43:20
	 */
	String DataSource() default "";

	/**
	 * 
	 * @Title: ForceMaster
	 * @Description:强制数据库操作使用主库
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 03:43:04
	 */
	boolean ForceMaster() default true;

}
