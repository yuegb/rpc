package com.rpc.core.extension;

import com.core.common.enums.RpcConstants;
import com.core.common.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @auther yuegb
 * @Date: 2020-10-15 13:46
 * @Description:封装扩展点加载器
 */
@Slf4j
public class ExtensionLoader<T> {

    // 创建本地缓存集合
    private static ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<>();
    // 单例类集合
    private ConcurrentHashMap<String, T> singletonInstances = null;
    // spi类集合
    private ConcurrentHashMap<String, Class<T>> extensionClasses = null;
    private Class<T> type;
    private volatile boolean init = false;
    private ServiceLoader<T> serviceLoader = null;
    private ExtensionLoader(Class<T> type) {
        this.type = type;
        serviceLoader = ServiceLoader.load(type);
    }

    private void checkInit() {
        if(!init) {
            loadExtensionClass();
        }
    }

    private synchronized void loadExtensionClass() {
        if (init) {
            return;
        }
        extensionClasses = loadExtensionClasses();
        singletonInstances = new ConcurrentHashMap<String, T>();
        init = true;
    }

    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        checkInterfaceType(type);

        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);

        if (loader == null) {
            loader = initExtensionLoader(type);
        }
        return loader;
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> ExtensionLoader<T> initExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);

        if (loader == null) {
            loader = new ExtensionLoader<T>(type);

            extensionLoaders.put(type, loader);
        }

        return loader;
    }

    public T getExtension(String name) {
        checkInit();

        if (name == null) {
            return null;
        }

        try {
            Spi spi = type.getAnnotation(Spi.class);

            if (spi.scope() == Scope.SINGLETON) {
                return getSingletonInstance(name);
            } else {
                Class<T> clz = extensionClasses.get(name);

                if (clz == null) {
                    return null;
                }
                return clz.newInstance();
            }
        } catch (Exception e) {
            failThrows(type, "Error when getExtension " + name, e);
        }

        return null;
    }

    private T getSingletonInstance(String name) throws InstantiationException, IllegalAccessException {
        T obj = singletonInstances.get(name);

        if (obj != null) {
            return obj;
        }

        Class<T> clz = extensionClasses.get(name);

        if (clz == null) {
            return null;
        }
        // 双重检查标志
        synchronized (singletonInstances) {
            obj = singletonInstances.get(name);
            if (obj != null) {
                return obj;
            }

            obj = clz.newInstance();
            singletonInstances.put(name, obj);
        }

        return obj;
    }

    /**
     * check clz
     *
     * <pre>
     * 		1.  is interface
     * 		2.  is contains @Spi annotation
     * </pre>
     *
     *
     * @param <T>
     * @param clz
     */
    private static <T> void checkInterfaceType(Class<T> clz) {
        if (clz == null) {
            failThrows(clz, "Error extension type is null");
        }

        if (!clz.isInterface()) {
            failThrows(clz, "Error extension type is not interface");
        }

        if (!isSpiType(clz)) {
            failThrows(clz, "Error extension type without @Spi annotation");
        }
    }

    private static <T> boolean isSpiType(Class<T> clz) {
        return clz.isAnnotationPresent(Spi.class);
    }

    /**
     * 加载spi默认路径下得可扩展类
     * @return
     */
    private ConcurrentHashMap<String, Class<T>> loadExtensionClasses() {
        ConcurrentMap<String, Class<T>> map = new ConcurrentHashMap<String, Class<T>>();
        if (serviceLoader == null) {
            throw new RpcException("ExtensionLoader loadExtensionClasses error," + type.getClass());
        }
        for (T t : serviceLoader) {
            checkExtensionType((Class<T>) t.getClass());
            String spiName = getSpiName(t.getClass());
            if (map.containsKey(spiName)) {
                failThrows(t.getClass(), ":Error spiName already exist " + spiName);
            } else {
                map.put(spiName, (Class<T>) t.getClass());
            }
        }
        return (ConcurrentHashMap<String, Class<T>>) map;
    }

    /**
     * check extension clz
     *
     * <pre>
     * 		1) is public class
     * 		2) contain public constructor and has not-args constructor
     * 		3) check extension clz instanceof Type.class
     * </pre>
     *
     * @param clz
     */
    private void checkExtensionType(Class<T> clz) {
        checkClassPublic(clz);

        checkConstructorPublic(clz);

        checkClassInherit(clz);
    }

    private void checkClassPublic(Class<T> clz) {
        if (!Modifier.isPublic(clz.getModifiers())) {
            failThrows(clz, "Error is not a public class");
        }
    }

    private void checkConstructorPublic(Class<T> clz) {
        Constructor<?>[] constructors = clz.getConstructors();

        if (constructors == null || constructors.length == 0) {
            failThrows(clz, "Error has no public no-args constructor");
        }

        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                return;
            }
        }

        failThrows(clz, "Error has no public no-args constructor");
    }

    private void checkClassInherit(Class<T> clz) {
        if (!type.isAssignableFrom(clz)) {
            failThrows(clz, "Error is not instanceof " + type.getName());
        }
    }

    /**
     * 获取扩展点的名字
     *
     * <pre>
     * 		如果扩展类有SpiMeta的注解，那么获取对应的name，如果没有的话获取classname
     * </pre>
     *
     * @param clz
     * @return
     */
    public String getSpiName(Class<?> clz) {
        SpiMeta spiMeta = clz.getAnnotation(SpiMeta.class);

        String name = (spiMeta != null && !"".equals(spiMeta.name())) ? spiMeta.name() : clz.getSimpleName();

        return name;
    }

    private static <T> void failThrows(Class<T> type, String msg) {
        throw new RpcException(type.getName() + ": " + msg);
    }

    private static <T> void failThrows(Class<T> type, String msg, Throwable cause) {
        throw new RpcException(type.getName() + ": " + msg, cause);
    }
}
