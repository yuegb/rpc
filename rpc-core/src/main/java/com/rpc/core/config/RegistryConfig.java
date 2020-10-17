package com.rpc.core.config;

import java.io.Serializable;

/**
 * @auther yuegb
 * @Date: 2020-10-15 13:31
 * @Description:
 */
public class RegistryConfig implements Serializable {
    private static final long serialVersionUID = -4093209827128645310L;

    // 注册配置名称
    private String name;

    // 注册协议
    private String regProtocol;

    // 注册地址 注册中心地址，支持多个ip+port，格式：ip1:port1,ip2:port2,ip3，如果没有port，则使用默认的port
    private String adress;

    // 注册中心缺省端口
    private Integer port;

    // 注册中心请求超时时间(毫秒)
    private Integer requestTimeout;

    // 注册中心连接超时时间
    private Integer connectTimeout;

    // 注册中心会话超时时间(毫秒)
    private Integer registrySessionTimeout;

    // 失败后重试的时间间隔
    private Integer registryRetryPeriod;


}
