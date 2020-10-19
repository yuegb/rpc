package com.rpc.core.util;

import com.core.common.enums.RpcConstants;
import com.rpc.core.rpc.Request;
import com.rpc.core.rpc.Traceable;
import com.rpc.core.rpc.TraceableContext;

/**
 * @Date: 2020-10-19 10:20
 * @Description:
 */
public class RpcUtil {

    public static void logEvent(Request request, String event, long time) {
        if (!(request instanceof Traceable)) {
            return;
        }
        TraceableContext context = ((Traceable) request).getTraceableContext();
        if (RpcConstants.TRACE_CSEND.equals(event)) {
            context.setSendTime(time);
            return;
        }
        if (RpcConstants.TRACE_SRECEIVE.equals(event)) {
            context.setReceiveTime(time);
            return;
        }
        context.addTraceInfo(event, String.valueOf(time));
    }

    public static String removeAsyncSuffix(String path) {
        if (path != null && path.endsWith(RpcConstants.ASYNC_SUFFIX)) {
            return path.substring(0, path.length() - RpcConstants.ASYNC_SUFFIX.length());
        }
        return path;
    }
}
