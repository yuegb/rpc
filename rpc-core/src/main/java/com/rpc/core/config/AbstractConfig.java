package com.rpc.core.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author yuegb
 * @Date: 2020-10-14 15:56
 * @Description:rpc接口配置
 */
@Data
@Slf4j
public class AbstractConfig implements Serializable {

    private static final long serialVersionUID = -7669602355581561259L;

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
