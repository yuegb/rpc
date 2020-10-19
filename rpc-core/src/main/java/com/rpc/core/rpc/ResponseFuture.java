package com.rpc.core.rpc;

/**
 * @Date: 2020-10-19 11:01
 * @Description: 异步调用响应报文
 */
public interface ResponseFuture extends Response, Future {

    void onSuccess(Response response);

    void onFailure(Response response);

    long getCreateTime();

    void setReturnType(Class<?> clazz);
}