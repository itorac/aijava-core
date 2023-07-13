/*
 * package com.cig.core.trace.configs;
 * 
 * import org.apache.commons.lang3.StringUtils; import
 * org.apache.dubbo.rpc.Invocation; import org.apache.dubbo.rpc.Invoker; import
 * org.apache.dubbo.rpc.Result; import org.apache.dubbo.rpc.RpcContext; import
 * org.apache.dubbo.common.constants.CommonConstants; import
 * org.apache.dubbo.common.extension.Activate; import
 * org.apache.dubbo.rpc.RpcException; import org.apache.dubbo.rpc.*; import
 * org.slf4j.MDC;
 * 
 * import com.cig.core.trace.constant.LogTraceConstant;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j
 * 
 * @Activate(group = { CommonConstants.PROVIDER, CommonConstants.CONSUMER },
 * value = "tracing") public class DubboTraceFilter implements Filter {
 * 
 * @Override public Result invoke(Invoker<?> invoker, Invocation invocation)
 * throws RpcException { log.info("begin DubboTraceFilter"); //
 * 获取dubbo上下文中的traceId String traceId =
 * RpcContext.getContext().getAttachment(LogTraceConstant.TRACE_ID); if
 * (StringUtils.isBlank(traceId)) { // customer 设置traceId到dubbo的上下文
 * RpcContext.getContext().setAttachment(LogTraceConstant.TRACE_ID,
 * MDC.get(LogTraceConstant.TRACE_ID)); } else { // provider 设置traceId到日志的上下文
 * MDC.put(LogTraceConstant.TRACE_ID, traceId); } //
 * log.info(MDC.get(TRACE_ID)); return invoker.invoke(invocation); } }
 */