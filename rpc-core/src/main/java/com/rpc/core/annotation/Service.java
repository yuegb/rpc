package com.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * @author yuegb
 * @Date: 2020-10-14 09:47
 * @Description:服务提供者注解
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    Class<?> interfaceClass() default void.class;

    // 分组
    String group() default "";

    // 代理类型
    String proxy() default "";

    // 最大并发调用
    int actives() default 0;

    // 是否异步
    boolean async() default false;

    // 请求超时时间
    int requestTimeout() default 0;

    // 重试次数
    int retries() default 0;

    // 是否开启gzip压缩
    boolean usegz() default false;

    String codec() default "";
}