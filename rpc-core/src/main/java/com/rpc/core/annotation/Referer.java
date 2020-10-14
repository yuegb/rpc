package com.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * @author yuegb
 * @Date: 2020-10-14 09:47
 * @Description:
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Referer {

    Class<?> interfaceClass() default void.class;

    // 用来区分多实现
    String group() default "";

    // 代理类型
    String proxy() default "";

    // 最大并发调用
    int actives() default 0;

    // 是否异步
    boolean async() default false;

    // 超时时间
    int requestTimeout() default 0;

    // 重试次数
    int retries() default 0;

    // 编码
    String codec() default "";

    // 是否开启gzip压缩
    boolean usegz() default false;

    // 直连地址
    String directUrl() default "";
}
