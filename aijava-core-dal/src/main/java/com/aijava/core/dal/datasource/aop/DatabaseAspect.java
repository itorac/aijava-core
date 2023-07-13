/**  
 * @Title: DatabaseAspect.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-01 04:07:17 
 */
package com.aijava.core.dal.datasource.aop;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.aijava.core.dal.datasource.annotation.Database;
import com.aijava.core.dal.datasource.helpers.DatabaseForce;
import com.aijava.core.dal.datasource.router.MultiDataSourceRouter;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DatabaseAspect
 * @Description: 强制切换数据源拦截器
 * @author xiegr
 * 
 *         <pre>
 * 使用方法:
 * 在服务层的方法名上添加如下注解
 *&#64;Transactional
 *&#64;Database(DataSource = "数据库名", ForceMaster = true)
 *         </pre>
 * 
 * @date 2021-12-01 04:07:17
 */
@Aspect
//该切面应当先于 @Transactional 执行
@Order(value = -1)
@Component
@Slf4j
public class DatabaseAspect {

	// 拦截DataSource下面的类
	@Pointcut("@annotation(com.aijava.core.dal.datasource.annotation.Database)")
	public void databasePointCut() {
	}

	@Around("databasePointCut()")
	public Object proceed(ProceedingJoinPoint point) throws Throwable {
		Database database=null;
		Class<? extends Object> target = point.getTarget().getClass();
		if(target.isAnnotationPresent(Database.class)){
            // 判断类上是否标注着注解
			database = target.getAnnotation(Database.class);
             log.info("类上标注了注解");
        }else{
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            if(method.isAnnotationPresent(Database.class)){
                // 判断方法上是否标注着注解，如果类和方法上都没有标注，则报错
            	database = method.getAnnotation(Database.class);
                log.info("方法上标注了注解");
            }else{
                throw new RuntimeException("@Database注解只能用于类或者方法上, 错误出现在:[" +
                        target.toString() +" " + method.toString() + "];");
            }
        }
		//MethodSignature signature = (MethodSignature) point.getSignature();
		//Method method = signature.getMethod();
		//Database database = method.getAnnotation(Database.class);
		// 判断注解中的ForceMaster取值
		if (database != null && database.ForceMaster()) {
			DatabaseForce.forceMaster();
		}
		// 指定数据源,配合 @transactional 事务使用
		if (database != null && StringUtils.isNotEmpty(database.DataSource())) {
			// 根据多数据源路由选择数据源
			MultiDataSourceRouter.specifyDataSource(database.DataSource());
		}
		return point.proceed();
	}

}
