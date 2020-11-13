package com.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * @Date: 2020-11-13 14:03
 * @Description:服务调用方注解
 * @author yuegb
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcRefer {

    /** 接口类型 */
    Class<?> interfaceClass() default void.class;

    /** 接口名称 */
    String interfaceName() default "";

    /** 分组:用于区分同一接口多个实现 */
    String group() default "";

    /** 协议版本 */
    String version() default "";

    /** 代理类型 */
    String proxy() default "";

    /** 直连服务端地址 */
    String directUrl() default "";

    /** 请求超时时间 */
    int requestTimeout() default 10000;

    /** 容器启动判断是否监测服务提供者 */
    boolean check() default false;

    /** 失败重试次数 */
    int retries() default 2;

    /** 是否异步调用 */
    boolean async() default false;
}