/*
 * package com.cig.core.trace.configs;
 * 
 * import java.util.UUID;
 * 
 * import org.apache.commons.lang3.StringUtils; import
 * org.apache.dubbo.rpc.Invocation; import org.apache.dubbo.rpc.Invoker; import
 * org.apache.dubbo.rpc.ListenableFilter; import org.apache.dubbo.rpc.Result;
 * import org.apache.dubbo.rpc.RpcContext; import
 * org.apache.dubbo.common.constants.CommonConstants; import
 * org.apache.dubbo.common.extension.Activate; import
 * org.apache.dubbo.rpc.RpcException; import org.slf4j.MDC;
 * 
 * import com.cig.core.trace.constant.LogTraceConstant;
 * 
 * 
 * @Activate(group = {CommonConstants.PROVIDER,CommonConstants.CONSUMER}) public
 * class GlobalTraceFilter extends ListenableFilter{
 * 
 * @Override public Result invoke(Invoker<?> invoker, Invocation invocation)
 * throws RpcException { String traceId =
 * RpcContext.getContext().getAttachment("traceId"); if (
 * !StringUtils.isEmpty(traceId) ) { // *) 从RpcContext里获取traceId并保存
 * MDC.put(LogTraceConstant.TRACE_ID, traceId); } else { // *) 交互前重新设置traceId,
 * 避免信息丢失 RpcContext.getContext().setAttachment("traceId",
 * UUID.randomUUID().toString().replace("-", ""));
 * MDC.put(LogTraceConstant.TRACE_ID, UUID.randomUUID().toString().replace("-",
 * "")); } // *) 实际的rpc调用 return invoker.invoke(invocation); }
 * 
 * 
 * 
 * }
 */