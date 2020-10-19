package com.rpc.core.proxy;

import com.rpc.core.rpc.Request;

/**
 * @Date: 2020-10-19 14:56
 * @Description:
 */
public interface CommonHandler {

    Object call(String methodName, Object[] arguments, Class returnType) throws Throwable;

    Object asyncCall(String methodName, Object[] arguments, Class returnType) throws Throwable;

    Object call(Request request, Class returnType) throws Throwable;

    Object asyncCall(Request request, Class returnType) throws Throwable;

    Request buildRequest(String methodName, Object[] arguments);

    Request buildRequest(String interfaceName, String methodName, Object[] arguments);
}