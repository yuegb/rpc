package com.rpc.core.extension;

import java.lang.annotation.*;

/**
 * @auther yuegb
 * @Date: 2020-10-15 13:56
 * @Description:spi扩展点注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Spi {

    Scope scope() default Scope.PROTOTYPE;
}
