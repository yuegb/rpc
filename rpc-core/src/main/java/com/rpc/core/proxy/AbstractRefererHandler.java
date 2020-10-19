package com.rpc.core.proxy;

import com.core.common.enums.RpcConstants;
import com.core.common.enums.URLParamType;
import com.core.common.exception.RpcException;
import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.ExtensionLoader;
import com.rpc.core.rpc.*;
import com.rpc.core.serialize.DeserializableObject;
import com.rpc.core.switcher.Switcher;
import com.rpc.core.switcher.SwitcherService;
import com.rpc.core.util.RpcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:59
 * @Description:
 */
@Slf4j
public class AbstractRefererHandler<T> {

    protected List<Cluster<T>> clusters;

    protected Class<T> clz;

    protected SwitcherService switcherService = null;

    protected String interfaceName = null;

    void init() {
        // cluster不能为空
        String switchName = this.clusters.get(0).getUrl().getParameter(URLParamType.switcherService.getName(), URLParamType.switcherService.getValue());
        switcherService = ExtensionLoader.getExtensionLoader(SwitcherService.class).getExtension(switchName);
    }

    Object invokeRequest(Request request,Class returnType, boolean async) throws Throwable {
        RpcContext curContext = RpcContext.getRpcContext();
        curContext.putAttribute(RpcConstants.ASYNC_SUFFIX, async);
        Map<String, String> attachments = curContext.getRpcAttachments();
        if (!attachments.isEmpty()) {
            for (Map.Entry<String, String> entry : attachments.entrySet()) {
                request.setAttachment(entry.getKey(), entry.getValue());
            }
        }
        if (StringUtils.isNoneBlank(curContext.getClientRequestId())) {
            request.setAttachment(URLParamType.requestIdFromClient.getName(), curContext.getClientRequestId());
        }
        // 判断服务是否降级，如果没有，那么就默认选择第一个，否则轮询
        for (Cluster<T> cluster : clusters) {
            String protocolSwitcher = RpcConstants.PROTOCOL_SWITCHER_PREFIX + cluster.getUrl().getProtocol();
            Switcher switcher = switcherService.getSwitcher(protocolSwitcher);
            if (switcher!= null && !switcher.isOn()) {
                continue;
            }
            Response response = null;
            boolean throwException = Boolean.parseBoolean(cluster.getUrl().getParameter(URLParamType.throwException.getName(), URLParamType.throwException.getValue()));
            try {
                RpcUtil.logEvent(request, RpcConstants.TRACE_INVOKE, System.currentTimeMillis());
                response = cluster.call(request);
                if (async) {
                    if (response instanceof ResponseFuture) {
                        ((ResponseFuture) response).setReturnType(returnType);
                        return response;
                    } else {
                        ResponseFuture responseFuture = new DefaultResponseFuture(request, 0, cluster.getUrl());
                        if (response.getException() != null) {
                            responseFuture.onFailure(response);
                        } else {
                            responseFuture.onSuccess(response);
                        }
                        responseFuture.setReturnType(returnType);
                        return responseFuture;
                    }
                } else {
                    Object value = response.getValue();
                    if (value != null && value instanceof DeserializableObject) {
                        try {
                            value = ((DeserializableObject) value).deserialize(returnType);
                        } catch (IOException e) {
                            log.error("deserialize response value fail! deserialize type:" + returnType, e);
                            throw new RpcException("deserialize return value fail! deserialize type:" + returnType, e);
                        }
                    }
                    return value;
                }
            } catch (Exception e) {
                log.error("RefererInvocationHandler invoke Error: uri=" + cluster.getUrl(), e);
                throw e;
            }
        }
        throw new RpcException("Referer call Error: cluster not exist, interface=" + interfaceName);
    }

    private Object getDefaultReturnValue(Class<?> returnType) {
        if (returnType != null && returnType.isPrimitive()) {
            return PrimitiveDefault.getDefaultReturnValue(returnType);
        }
        return null;
    }

    private static class PrimitiveDefault {
        private static boolean defaultBoolean;
        private static char defaultChar;
        private static byte defaultByte;
        private static short defaultShort;
        private static int defaultInt;
        private static long defaultLong;
        private static float defaultFloat;
        private static double defaultDouble;

        private static Map<Class<?>, Object> primitiveValues = new HashMap<Class<?>, Object>();

        static {
            primitiveValues.put(boolean.class, defaultBoolean);
            primitiveValues.put(char.class, defaultChar);
            primitiveValues.put(byte.class, defaultByte);
            primitiveValues.put(short.class, defaultShort);
            primitiveValues.put(int.class, defaultInt);
            primitiveValues.put(long.class, defaultLong);
            primitiveValues.put(float.class, defaultFloat);
            primitiveValues.put(double.class, defaultDouble);
        }

        public static Object getDefaultReturnValue(Class<?> returnType) {
            return primitiveValues.get(returnType);
        }

    }
}
