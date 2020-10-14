package com.rpc.core.config;

import com.core.common.enums.ErrorCode;
import com.core.common.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yuegb
 * @Date: 2020-10-14 16:17
 * @Description:
 */
@Slf4j
public class RefererConfig<T> extends AbstractConfig {

    private static final long serialVersionUID = 2805311803465437217L;

    private Class<T> interfaceClass;

    private String serviceInterface;

    /** 点对点直连服务提供地址 */
    private String directUrl;

    /** 初始化标识 */
    private AtomicBoolean initialized = new AtomicBoolean(false);

    private T ref;

    public T getRef() {
        if (ref == null) {
            initRef();
        }
        return ref;
    }

    /**
     * 初始化接口实现
     */
    public synchronized void initRef() {
        if (initialized.get()) {
            return;
        }
        try {
            interfaceClass = (Class<T>) Class.forName(interfaceClass.getName(), true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RpcException("ReferereConfig initRef is Error: Class not fund " + interfaceClass.getName(), e, ErrorCode.FRAMEWORK_INIT_ERROR);
        }
        checkInterface(interfaceClass);
        String proxy = null;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }
    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * 校验接口的合法性
     * @param interfaceClass
     */
    protected void checkInterface(Class<?> interfaceClass) {
        if (interfaceClass == null) {
            throw new IllegalStateException("FRAMEWORK_INIT_ERROR");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalStateException("The interface class " + interfaceClass + " is not a interface!");
        }
    }
}
