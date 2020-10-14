package com.rpc.core.rpc;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:56
 * @Description:
 */
public class DefaultResponse implements Response, Traceable, CallBackable, Serializable {

    private static final long serialVersionUID = 8872964158839661093L;
    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;
    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息
    private byte rpcProtocolVersion = RpcProtocolVersion.VERSION_1.getVersion();
    private int serializeNumber = 0;// default serialization is hession2
    private List<Pair<Runnable, Executor>> taskList = new ArrayList<>();
    private AtomicBoolean isFinished = new AtomicBoolean();
    private TraceableContext traceableContext = new TraceableContext();

    public DefaultResponse() {
    }

    public DefaultResponse(long requestId) {
        this.requestId = requestId;
    }

    public DefaultResponse(Response response) {
        this.value = response.getValue();
        this.exception = response.getException();
        this.requestId = response.getRequestId();
        this.processTime = response.getProcessTime();
        this.timeout = response.getTimeout();
        this.rpcProtocolVersion = response.getRpcProtocolVersion();
        this.serializeNumber = response.getSerializeNumber();
        this.attachments = response.getAttachments();
        if (response instanceof Traceable) {
            traceableContext.setReceiveTime(((Traceable) response).getTraceableContext().getReceiveTime());
            traceableContext.traceInfoMap = ((Traceable) response).getTraceableContext().getTraceInfoMap();
        }
    }

    public DefaultResponse(Object value) {
        this.value = value;
    }

    public DefaultResponse(Object value, long requestId) {
        this.value = value;
    }

    @Override
    public void addFinishCallback(Runnable runnable, Executor executor) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public long getRequestId() {
        return 0;
    }

    @Override
    public long getProcessTime() {
        return 0;
    }

    @Override
    public void setProcessTime(long time) {

    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public void setAttachment(String key, String value) {

    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return 0;
    }

    @Override
    public void setSerializeNumber(int number) {

    }

    @Override
    public int getSerializeNumber() {
        return 0;
    }

    @Override
    public TraceableContext getTraceableContext() {
        return null;
    }
}
