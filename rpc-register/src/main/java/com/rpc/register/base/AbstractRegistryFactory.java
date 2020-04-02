package com.rpc.register.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: yuegb
 * @Date: 2020-2-26 17:59
 * @Description:抽象工厂类
 */
public abstract class AbstractRegistryFactory implements RegistryFactory {

    // 存放注册中心集合
    public static final Map<URL, Registry> REGISTRIES = new HashMap<>();

    // 注册中心锁
    private static final ReentrantLock LOCK = new ReentrantLock();

    @Override
    public Registry getRegister(URL url) {
        try {
            LOCK.lock();
            Registry registry = REGISTRIES.get(url);
            if (null != registry) {
                return registry;
            }
            registry = createRegistry(url);
            if (registry == null) {
                throw new IllegalStateException("Can not create registry " + url);
            }
            REGISTRIES.put(url, registry);
            return registry;
        } finally {
            LOCK.unlock();
        }
    }

    protected abstract Registry createRegistry(URL url);
}
