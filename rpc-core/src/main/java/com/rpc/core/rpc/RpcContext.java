package com.rpc.core.rpc;

import com.core.common.enums.URLParamType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2020-10-19 09:49
 * @Description:
 */
public class RpcContext {

    private static final ThreadLocal<RpcContext> LOCAL_CONTEXT = new ThreadLocal<>();

    private Map<Object, Object> attributes = new HashMap();

    private Map<String, String> attachments = new HashMap<>();

    private Request request;

    private Response response;

    private String clientRequestId = null;

    public static RpcContext getRpcContext() {
        return LOCAL_CONTEXT.get();
    }

    public static RpcContext init(Request request) {
        RpcContext context = new RpcContext();
        if (request != null) {
            context.setRequest(request);
            context.setClientRequestId(request.getAttachments().get(URLParamType.requestIdFromClient.getName()));
        }
        LOCAL_CONTEXT.set(context);
        return context;
    }

    public static RpcContext init() {
        RpcContext context = new RpcContext();
        LOCAL_CONTEXT.set(context);
        return context;
    }

    public static void destroy() {
        LOCAL_CONTEXT.remove();
    }

    public void putAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(Object key) {
        return attributes.get(key);
    }

    public void removeAttribute(Object key) {
        attributes.remove(key);
    }

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public void setRpcAttachment(String key, String value) {
        attachments.put(key, value);
    }

    /**
     * get attachments from rpccontext only. not from request or response
     *
     * @param key
     * @return
     */
    public String getRpcAttachment(String key) {
        return attachments.get(key);
    }

    public void removeRpcAttachment(String key) {
        attachments.remove(key);
    }

    public Map<String, String> getRpcAttachments() {
        return attachments;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

}
