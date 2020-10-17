package com.rpc.core.proxy;

import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.Spi;

import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:42
 * @Description: 代理工厂
 */
@Spi
public interface ProxyFactory {

    /**
     * 获取代理对象
     * @param clz
     * @param clusterList
     * @param <T>
     * @return
     */
    <T> T getProxy(Class<T> clz, List<Cluster<T>> clusterList);
}