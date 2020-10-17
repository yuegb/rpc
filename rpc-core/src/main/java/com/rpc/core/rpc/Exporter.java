package com.rpc.core.rpc;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:07
 * @Description: 暴露服务
 */
public interface Exporter<T> extends Node {

    /**
     * 获取服务提供者
     * @return
     */
    Provider<T> getProvider();

    void unexport();
}