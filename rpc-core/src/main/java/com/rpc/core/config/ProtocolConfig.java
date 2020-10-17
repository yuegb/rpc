package com.rpc.core.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @auther yuegb
 * @Date: 2020-10-15 13:18
 * @Description:协议配置
 */
@Data
public class ProtocolConfig implements Serializable {

    private static final long serialVersionUID = 7349845178236927918L;
    // 服务协议
    private String name;
    // 序列化方式
    private String serialization;
    // 协议编码方式
    private String codec;
    // IO线程池大小
    private Integer iothreads;
    // 请求超时
    protected Integer requestTimeout;
    // client最小连接数
    protected Integer minClientConnection;
    // client最大连接数
    protected Integer maxClientConnection;
    // 最小工作pool线程数
    protected Integer minWorkerThread;
    // 最大工作pool线程数
    protected Integer maxWorkerThread;
    // 请求响应包的最大长度限制
    protected Integer maxContentLength;
    // server支持的最大连接数
    protected Integer maxServerConnection;
    // 负载均衡 方式
    protected String loadbalance;
    // 高可用策略 strategy
    protected String haStrategy;
    // server端队列大小
    protected Integer workerQueueSize;
    // server端的连接数大小
    protected Integer acceptConnections;
    // 动态代理类型
    protected String proxy;
    // 失败重试次数
    protected Integer retries;
    // 是否异步调用，如果是，将回调future
    protected Boolean async;
    // 扩展参数
    private Map<String, String> parameters;

}
