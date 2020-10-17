package com.rpc.core.proxy;

import com.rpc.core.cluser.Cluster;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:54
 * @Description:生成refer代理对象
 */
public class RefererInvocationHandler<T> implements InvocationHandler {

    public <T> RefererInvocationHandler(Class<T> clz, List<Cluster<T>> clusters) {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
