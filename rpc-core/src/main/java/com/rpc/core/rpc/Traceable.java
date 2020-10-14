package com.rpc.core.rpc;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:46
 * @Description:链路追踪接口
 */
public interface Traceable {

    TraceableContext getTraceableContext();
}
