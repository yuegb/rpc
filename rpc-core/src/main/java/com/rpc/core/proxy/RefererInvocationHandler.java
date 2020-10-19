package com.rpc.core.proxy;

import com.core.common.enums.RpcConstants;
import com.core.common.exception.RpcException;
import com.rpc.core.cluser.Cluster;
import com.rpc.core.rpc.DefaultRequest;
import com.rpc.core.rpc.Referer;
import com.rpc.core.rpc.ResponseFuture;
import com.rpc.core.util.ReflectUtil;
import com.rpc.core.util.RequestIdGenerator;
import com.rpc.core.util.RpcUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:54
 * @Description:生成refer代理对象
 */
@Slf4j
public class RefererInvocationHandler<T> extends AbstractRefererHandler<T> implements InvocationHandler {

    public RefererInvocationHandler(Class<T> clz, List<Cluster<T>> clusters) {
        this.clusters = clusters;
        this.clz = clz;
        init();
        interfaceName = RpcUtil.removeAsyncSuffix(clz.getName());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isLocalMethod(method)) {
            if ("toString".equals(method.getName())) {
                return clustersToString();
            }
            if ("equals".equals(method.getName())) {
                return proxyEquals(args[0]);
            }
            if ("hashCode".equals(method.getName())) {
                return this.clusters == null ? 0 : this.clusters.hashCode();
            }
            throw new RpcException("can not invoke local method:" + method.getName());
        }
        DefaultRequest request = new DefaultRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setArguments(args);
        String methodName = method.getName();
        boolean async = false;
        if (methodName.endsWith(RpcConstants.ASYNC_SUFFIX) && method.getReturnType().equals(ResponseFuture.class)) {
            methodName = RpcUtil.removeAsyncSuffix(methodName);
            async = true;
        }
        request.setMethodName(methodName);
        request.setParamtersDesc(ReflectUtil.getMethodParamDesc(method));
        request.setInterfaceName(interfaceName);
        return invokeRequest(request, getRealReturnType(async, this.clz, method, methodName), async);
    }

    private Class getRealReturnType(boolean async, Class<T> clz, Method method, String methodName) {
        if (async) {
            try {
                Method m = clz.getMethod(methodName, method.getParameterTypes());
                return m.getReturnType();
            } catch (NoSuchMethodException e) {
                log.warn("RefererInvocationHandler get real return type fail. err:" + e.getMessage());
                return method.getReturnType();
            }
        } else {
            return method.getReturnType();
        }
    }

    /**
     * tostring,equals,hashCode,finalize等接口未声明的方法不进行远程调用
     *
     * @param method
     * @return
     */
    public boolean isLocalMethod(Method method) {
        if (method.getDeclaringClass().equals(Object.class)) {
            try {
                Method interfaceMethod = clz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                return false;
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    private String clustersToString() {
        StringBuilder sb = new StringBuilder();
        for (Cluster<T> cluster : clusters) {
            sb.append("{protocol:").append(cluster.getUrl().getProtocol());
            List<Referer<T>> referers = cluster.getReferers();
            if (referers != null) {
                for (Referer<T> refer : referers) {
                    sb.append("[").append(refer.getUrl().toSimpleString()).append(", available:").append(refer.isAvailable()).append("]");
                }
            }
            sb.append("}");
        }
        return sb.toString();
    }

    private boolean proxyEquals(Object o) {
        if (o == null || this.clusters == null) {
            return false;
        }
        if (o instanceof List) {
            return this.clusters == o;
        } else {
            return o.equals(this.clusters);
        }
    }

}
