package com.rpc.core.rpc;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:49
 * @Description:
 */
public class DefaultRequest implements Request, Traceable, Serializable {

    private static final long serialVersionUID = 4907490575909198034L;

    private String interfaceName;
    private String methodName;
    private String paramtersDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;
    private long requestId;
    private byte rpcProtocolVersion = RpcProtocolVersion.VERSION_1.getVersion();
    // default serialization is hession2
    private int serializeNumber = 0;
    private TraceableContext traceableContext = new TraceableContext();

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getParamtersDesc() {
        return paramtersDesc;
    }

    public void setParamtersDesc(String paramtersDesc) {
        this.paramtersDesc = paramtersDesc;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }


    @Override
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.emptyMap();
    }

    @Override
    public void setAttachment(String name, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(name, value);
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public int getRetries() {
        return retries;
    }

    @Override
    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public byte getRpcProtocolVersion() {
        return rpcProtocolVersion;
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {
        this.rpcProtocolVersion = rpcProtocolVersion;
    }

    @Override
    public void setSerializeNumber(int number) {
        this.serializeNumber = number;
    }

    @Override
    public int getSerializeNumber() {
        return serializeNumber;
    }

    @Override
    public TraceableContext getTraceableContext() {
        return traceableContext;
    }

    @Override
    public String toString() {
        return interfaceName + "." + methodName + "(" + paramtersDesc + ") requestId=" + requestId;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }
}
