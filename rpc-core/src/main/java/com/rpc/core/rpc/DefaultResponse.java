package com.rpc.core.rpc;

import com.core.common.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:56
 * @Description:
 */
@Slf4j
public class DefaultResponse implements Response, Traceable, CallBackable, Serializable {

    private static final long serialVersionUID = 8872964158839661093L;
    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;
    // rpc协议版本兼容时可以回传一些额外的信息
    private Map<String, String> attachments;
    private byte rpcProtocolVersion = RpcProtocolVersion.VERSION_1.getVersion();
    // 默认的序列化机制 hessian2
    private int serializeNumber = 0;
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
        if (!isFinished.get()) {
            taskList.add(Pair.of(runnable, executor));
        }
    }

    @Override
    public void onFinish() {
        if (!isFinished.compareAndSet(false, true)) {
            return;
        }
        for (Pair<Runnable, Executor> pair : taskList) {
            Runnable runnable = pair.getKey();
            Executor executor = pair.getValue();
            if (null == executor) {
                runnable.run();
            } else {
                try {
                    executor.execute(runnable);
                } catch (Exception e) {
                    log.error("Callbackable response exec callback task error, e: ", e);
                }
            }
        }
    }

    @Override
    public Object getValue() {
        if (exception != null) {
            throw (exception instanceof RuntimeException) ? (RuntimeException) exception : new RpcException(
                    exception.getMessage(), exception);
        }
        return value;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    @Override
    public long getProcessTime() {
        return processTime;
    }

    @Override
    public void setProcessTime(long time) {
        this.processTime = time;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.emptyMap();
    }

    @Override
    public void setAttachment(String key, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        attachments.put(key, value);
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {
        this.rpcProtocolVersion = rpcProtocolVersion;
    }

    @Override
    public byte getRpcProtocolVersion() {
        return rpcProtocolVersion;
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
}
