package com.rpc.core;

import com.rpc.core.annotation.ServiceScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @auther yuegb
 * @Date: 2020-10-14 11:13
 * @Description:
 */
@SpringBootApplication
@ServiceScan(basePackage = {"com.rpc.core"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
