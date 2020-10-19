package com.rpc.core.rpc;

import com.core.common.enums.FutureState;
import com.core.common.exception.RpcException;
import com.rpc.core.serialize.DeserializableObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Date: 2020-10-19 11:06
 * @Description:
 */
@Slf4j
public class DefaultResponseFuture implements ResponseFuture, Traceable {

    protected final Object lock = new Object();
    protected volatile FutureState state = FutureState.DOING;
    protected Object result = null;
    protected Exception exception = null;
    protected long createTime = System.currentTimeMillis();
    protected int timeout = 0;
    protected long processTime = 0;
    protected Request request;
    protected List<FutureListener> listeners;
    protected URL serverUrl;
    protected Class returnType;
    // 协议附件
    private Map<String, String> attachments;
    private TraceableContext traceableContext = new TraceableContext();

    public DefaultResponseFuture(Request requestObj, int timeout, URL serverUrl) {
        this.request = requestObj;
        this.timeout = timeout;
        this.serverUrl = serverUrl;
    }

    @Override
    public void onSuccess(Response response) {
        this.result = response.getValue();
        this.processTime = response.getProcessTime();
        this.attachments = attachments;
        if (response instanceof Traceable) {
            traceableContext.setReceiveTime(((Traceable) response).getTraceableContext().getReceiveTime());
            traceableContext.traceInfoMap = ((Traceable) response).getTraceableContext().getTraceInfoMap();
        }

        done();
    }

    private boolean isDoing() {
        return state.isDoingState();
    }

    protected boolean done() {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }
            state = FutureState.DONE;
            lock.notifyAll();
        }
        notifyListeners();
        return true;
    }

    private void notifyListeners() {
        if (listeners != null) {
            for (FutureListener listener : listeners) {
                notifyListener(listener);
            }
        }
    }

    private void notifyListener(FutureListener listener) {
        try {
            listener.operationComplete(this);
        } catch (Throwable t) {
            log.error(this.getClass().getName() + " notifyListener Error: " + listener.getClass().getSimpleName(), t);
        }
    }

    @Override
    public void onFailure(Response response) {
        this.exception = response.getException();
        this.processTime = response.getProcessTime();

        done();
    }

    @Override
    public long getCreateTime() {
        return 0;
    }

    @Override
    public void setReturnType(Class<?> clazz) {

    }

    @Override
    public Object getValue() {
        synchronized (lock) {
            if (!isDoing()) {
                return getValueOrThrowable();
            }
            if (timeout <= 0) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    cancel(new RpcException(this.getClass().getName() + " getValue InterruptedException : "
                            + "requestId" + request.getRequestId() +"interfaceName:" + request.getInterfaceName() + " cost=" + (System.currentTimeMillis() - createTime), e));
                }
                return getValueOrThrowable();
            } else {
                long waitTime = timeout - (System.currentTimeMillis() - createTime);
                if (waitTime > 0) {
                    for (; ; ) {
                        try {
                            lock.wait(waitTime);
                        } catch (InterruptedException e) {
                        }

                        if (!isDoing()) {
                            break;
                        } else {
                            waitTime = timeout - (System.currentTimeMillis() - createTime);
                            if (waitTime <= 0) {
                                break;
                            }
                        }
                    }
                }

                if (isDoing()) {
                    timeoutSoCancel();
                }
            }
        }
        return getValueOrThrowable();
    }

    private void timeoutSoCancel() {
        this.processTime = System.currentTimeMillis() - createTime;

        synchronized (lock) {
            if (!isDoing()) {
                return;
            }

            state = FutureState.CANCELLED;
            exception =
                    new RpcException(this.getClass().getName() + " request timeout: serverPort=" + serverUrl.getPort()
                            + "requestId" + request.getRequestId() +"interfaceName:" + request.getInterfaceName() + " cost=" + (System.currentTimeMillis() - createTime));

            lock.notifyAll();
        }

        notifyListeners();
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public void addListener(FutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("FutureListener is null");
        }

        boolean notifyNow = false;
        synchronized (lock) {
            if (!isDoing()) {
                notifyNow = true;
            } else {
                if (listeners == null) {
                    listeners = new ArrayList<>(1);
                }

                listeners.add(listener);
            }
        }

        if (notifyNow) {
            notifyListener(listener);
        }
    }

    @Override
    public long getRequestId() {
        return this.request.getRequestId();
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
        return attachments != null ? attachments : Collections.<String, String>emptyMap();
    }

    @Override
    public void setAttachment(String key, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(key, value);
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return RpcProtocolVersion.VERSION_1.getVersion();
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
        return traceableContext;
    }

    @Override
    public boolean cancel() {
        Exception e =
                new RpcException(this.getClass().getName() + " task cancel: serverPort=" + serverUrl.getPort() + " "
                        + "requestId" + request.getRequestId() +"interfaceName:" + request.getInterfaceName() + " cost=" + (System.currentTimeMillis() - createTime));
        return cancel(e);
    }

    @Override
    public boolean isCancelled() {
        return state.isCancelledState();
    }

    @Override
    public boolean isDone() {
        return state.isDoneState();
    }

    @Override
    public boolean isSuccess() {
        return isDone() && (exception == null);
    }

    private Object getValueOrThrowable() {
        if (exception != null) {
            throw (exception instanceof RuntimeException) ? (RuntimeException) exception : new RpcException(
                    exception.getMessage(), exception);
        }
        if (result != null && returnType != null && result instanceof DeserializableObject) {
            try {
                result = ((DeserializableObject) result).deserialize(returnType);
            } catch (IOException e) {
                log.error("deserialize response value fail! return type:" + returnType, e);
                throw new RpcException("deserialize return value fail! deserialize type:" + returnType, e);
            }
        }
        return result;
    }

    protected boolean cancel(Exception e) {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            state = FutureState.CANCELLED;
            exception = e;
            lock.notifyAll();
        }

        notifyListeners();
        return true;
    }
}
