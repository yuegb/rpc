package com.rpc.core.proxy;

import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.SpiMeta;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:52
 * @Description:
 */
@SpiMeta(name="jdk")
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Class<T> clz, List<Cluster<T>> clusters) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new RefererInvocationHandler<>(clz, clusters));
    }
}
