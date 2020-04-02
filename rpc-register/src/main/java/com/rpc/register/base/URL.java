package com.rpc.register.base;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @Auther: yuegb
 * @Date: 2020-2-26 18:15
 * @Description: 自定义注册url结构
 * <p>
 *     url : /rpc/com.rpc.service/provides/127.0.0.1:8080
 * </p>
 *
 */
@Data
@Log4j2
public class URL {

    public static final String COLON = ":";
    // root路径
    public static final String ROOT = "/rpc";
    // 提供者路径
    public static final String PROVIDERS = "/providers";
    // 消费者路径
    public static final String CONSUMERS = "/consumers";

    // 服务名称
    private String serviceName;

    //ip地址
    private String ip;

    //端口号
    private String port;

    public URL(String serviceName, String ip, String port) {
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
    }

    public String getProviderUrl() {
        return new StringBuilder().append(ROOT).append(serviceName).append(PROVIDERS).append(ip).append(COLON).append(port).toString();
    }

    public String getConsumerUrl() {
        return new StringBuilder().append(ROOT).append(serviceName).append(CONSUMERS).append(ip).append(COLON).append(port).toString();
    }
}
