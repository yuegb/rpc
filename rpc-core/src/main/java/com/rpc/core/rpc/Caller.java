package com.rpc.core.rpc;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:36
 * @Description:
 */
public interface Caller<T> extends Node {

    Class<T> getInterface();

    Response call(Request request);
}