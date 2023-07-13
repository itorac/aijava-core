/**  
 * @Title: DaoInterceptor.java
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-01 03:22:14 
 */
package com.aijava.core.dal.datasource.interceptor;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aijava.core.common.utils.StackTraceUtil;
import com.aijava.core.dal.datasource.router.MultiDataSourceRouter;
import com.google.common.collect.Maps;

/**
 * @ClassName: DaoInterceptor
 * @Description: TODO(描述)
 * @author xiegr
 * @date 2021-12-01 03:22:14
 */
@Component
public class DaoInterceptor implements MethodInterceptor {

	private static Logger logger = LoggerFactory.getLogger(DaoInterceptor.class);

	// 数据库操作阈值，超过150ms打印运行日志
	private static final int DATABASE_THRESHOLD = 100;

	/**
	 * 注入sqlSessionFactory
	 */
	@Resource
	private DefaultSqlSessionFactory sqlSessionFactory;

	/**
	 * mapper中数据库 statement命名，约定为 select* insert* update* delete.*
	 */
	private static final Pattern select = Pattern.compile("^select.*");
	private static final Pattern update = Pattern.compile("^update.*");
	private static final Pattern insert = Pattern.compile("^insert.*");
	private static final Pattern delete = Pattern.compile("^delete.*");

	/**
	 * @Title: invoke
	 * @Description: TODO(描述)
	 * @param invocation
	 * @return
	 * @throws Throwable
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 * @author xiegr
	 * @date 2021-12-01 03:28:27
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// mapper中的方法名称
		String statementId = invocation.getMethod().getName();
		// mapper地址
		String mapperNamespace = invocation.getMethod().getDeclaringClass().getName();
		if (mapperNamespace.contains("BaseMapper")) {
			Class<?> clazz = invocation.getThis().getClass();
			mapperNamespace = clazz.getInterfaces()[0].getName();
		}
		// 完整的mapper名称 com.cig.*.dal.*.insert
		String mapper = mapperNamespace + "." + statementId;

		// 数据源路由
		String dataSource = MultiDataSourceRouter.router(mapperNamespace, statementId);
		// 设置当前数据源
		MultiDataSourceRouter.setDataSourceKey(dataSource);
		// 数据库调用开始时间
		long beginTime = System.currentTimeMillis();

		// 校验sql是否合法
		String sql = checkBoundSql(invocation, statementId, mapperNamespace, mapper);

		// 全局性能统计，并记录堆栈，函数调用开始

		// 数据库操作返回结果
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Exception e) {
			logger.error("database exception,mapper[{}],statement[{}],begintime[{}],endtime[{}]. ", mapperNamespace,
					statementId, beginTime, System.currentTimeMillis(), e);

			// push一个数据库访事件

			throw e;

		} finally {
			// 全局性能统计，并记录堆栈，函数调用结束

			// 数据库操作超过 DATABASE_THRESHOLD 时，记录warning日志
			long delay = System.currentTimeMillis() - beginTime;
			if (delay > DATABASE_THRESHOLD) {
				// 打印延时信息,json格式,便于后续分析
				DBDelayMsg dbDelayMsg = new DBDelayMsg();
				dbDelayMsg.mapper = mapper;
				dbDelayMsg.delay = delay;
				dbDelayMsg.arguments = StackTraceUtil.getArguments(invocation.getArguments());
				dbDelayMsg.beginTime = beginTime;
				dbDelayMsg.endTime = System.currentTimeMillis();
				dbDelayMsg.sql = sql;
				logger.warn("database delay warn {}", dbDelayMsg);
			}
		}
		return result;
	}

	/**
	 * 检查sql是否合理,比如一定要有 where
	 * 
	 * @param invocation
	 * @param daoStatement
	 * @return
	 */
	private String checkBoundSql(MethodInvocation invocation, String statementId, String mapperNamespace,
			String daoStatement) {

		String sql = "";
		try {
			// 获取对应的sql
			sql = getBoundSql(invocation, daoStatement);
			// 不是insert操作，如果sql语句中 既不包含 where 也不包含 limit, 并且这个statement需要检查 , 则抛出异常
			if (!insert.matcher(statementId).matches() && sql.toLowerCase().indexOf("where") == -1
					&& sql.toLowerCase().indexOf("limit") == -1 && MultiDataSourceRouter.isNeedCheckSql(daoStatement)) {
				logger.warn(
						"sql is illegal, should contains key word 'where' or 'limit'. \nmapper : [{}] \nSQL : [{}].",
						daoStatement, sql);
				throw new IllegalStateException();
			}
			// 检测Mapper
			checkMapperStatementName(mapperNamespace, statementId);

		} catch (Exception e) {

			// 其他异常不处理, 只打印日志
			if (e instanceof IllegalStateException) {
				logger.info("sql is illegal, no query conditions of 'where' or 'limit' in sql, sql is: {}", sql);
				// 第一个版本,先记录日志,不抛出异常
				// throw e ;
			} else {
				logger.info("exception happend in checkBoundSql {}", e);
			}
		}

		return sql;
	}

	/**
	 * 根据dao信息 和 参数 ,获取最终的sql
	 * 
	 * @param invocation
	 * @param dao
	 * @return
	 */
	private String getBoundSql(MethodInvocation invocation, String dao) {
		String sql = "";
		try {
			Map<String, Object> paramMap = buildParamMap(invocation);
			MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(dao);
			BoundSql boundSql =ms.getBoundSql(paramMap);
			sql = boundSql.getSql();
		} catch (Exception e) {

		}
		return StringUtils.isNotEmpty(sql) ? sql : "";
	}

	/**
	 * 
	 * @Title: checkMapperStatementName
	 * @Description: 检查dao方法的命名，是否符合规范，必须以select、insert、update、delete开始
	 * @param mapperNamespace
	 * @param statementId
	 * @author xiegr
	 * @date 2021-12-01 01:50:12
	 */
	private void checkMapperStatementName(String mapperNamespace, String statementId) {
		/**
		 * 命名符合规范，直接返回
		 */
		if (select.matcher(statementId).matches() || update.matcher(statementId).matches()
				|| insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			return;
		}

		/**
		 * 如果命名不符合规范，打印warn级别的日志
		 */
		logger.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ",
				mapperNamespace, statementId);
	}

	/**
	 * 
	 * @Title: buildParamMap
	 * @Description: 构建参数
	 * @param invocation
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 08:37:49
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, Object> buildParamMap(MethodInvocation invocation) {
		Map<String, Object> paramMap = Maps.newHashMap();
		Object[] args = invocation.getArguments();
		if (args != null && args.length == 1) {
			Object _params = wrapCollection(args[0]);
			if (_params instanceof Map) {
				paramMap.putAll((Map) _params);
			}else {
				paramMap = JSONObject.parseObject(JSONObject.toJSONString(_params), Map.class);
			}
		}
		int index = 0;
		for (Parameter parameter : invocation.getMethod().getParameters()) {
			Param param = parameter.getAnnotation(Param.class);
			paramMap.put(null == param ? "" : param.value(), args[index]);
			index++;
		}
		return paramMap;
	}

	/**
	 * 
	 * @Title: wrapCollection
	 * @Description: 构建参数
	 * @param object
	 * @return
	 * @author xiegr
	 * @date 2021-12-01 08:38:53
	 */
	private Object wrapCollection(Object object) {
		if(object!=null) {
			if (object instanceof Collection) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("collection",object);
				if(object instanceof List) {
					map.put("list", object);
				}
				return map;
			}else if (object.getClass().isArray()) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("array", object);
				return map;
			}else if (isReference(object) || object.getClass().isPrimitive()) {
				Map<String, Object> map = Maps.newHashMap();
				map.put(String.valueOf(object.hashCode()), object);
				return map;
			}
		}
		return object;
	}

	/**
	 * 是否是封装类型
	 * @param object
	 * @return
	 */
	private boolean isReference(Object object) {
		if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long
				|| object instanceof Float || object instanceof Double || object instanceof Character
				|| object instanceof Boolean || object instanceof String) {
			return true;
		}
		return false;
	}
	
}



class DBDelayMsg {
	long delay;
	String mapper;
	String arguments;
	long beginTime;
	long endTime;
	String sql;

	@Override
	public String toString() {
		return (new StringBuffer()).append("delay time:").append(delay).append(",mapper:").append(mapper)
				.append(",begin:").append(beginTime).append(",end:").append(endTime).append("\n SQL:[").append(sql)
				.append("]").append("\n Arguments:[").append(arguments).append("]").toString();
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getMapper() {
		return mapper;
	}

	public void setMapper(String mapper) {
		this.mapper = mapper;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
}
