package com.rpc.core.config;

import com.rpc.core.rpc.URL;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuegb
 * @Date: 2020-10-14 15:56
 * @Description:rpc接口配置
 */
@Data
@Slf4j
public class AbstractConfig implements Serializable {

    private static final long serialVersionUID = -7669602355581561259L;

    /** 暴露、使用的协议可以是多个，但client只能使用一种协议进行访问*/
    protected List<ProtocolConfig> protocols;

    /** 注册中心的配置列表 */
    protected List<RegistryConfig> registryConfig;

    /** 解析后的所有注册中心url */
    protected List<URL> registryUrls = new ArrayList<>();

    /** 分组 */
    protected String group;

    /** 代理类型 */
    protected String proxy;

    /** 最大并发调用 */
    protected Integer actives;

    /** 是否异步 */
    protected boolean async;

    /** 超时时间 */
    protected int requestTimeout;

    /** 重试次数 */
    protected int retries;

    /** 编解码 */
    protected String codec;

    /** 是否开启压缩 */
    protected boolean usegz;

}
