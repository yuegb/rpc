package com.rpc.core.annotation;

/**
 * @author yuegb
 * @Date: 2020-11-13 14:02
 * @Description:服务提供注解
 */
public @interface RpcService {

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

    /** 最大并发调用 */
    int actives() default 0;

}