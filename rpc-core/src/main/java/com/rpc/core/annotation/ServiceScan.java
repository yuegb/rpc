package com.rpc.core.annotation;

import com.rpc.core.spring.ServiceScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yuegb
 * @Date: 2020-10-14 10:22
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({ServiceScannerRegister.class})
public @interface ServiceScan {

    //扫描包路径
    String[] basePackage() default {};
}
