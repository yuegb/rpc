package com.rpc.remoting.zookeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuegb
 * @Date: 2020-3-16 14:47
 * @Description:
 */
@Slf4j
public abstract class AbstractZookeeperClient implements ZookeeperClient {

    // 链接超时时间, 默认为60000ms
    protected int DEFAULT_CONNECTION_TIMEOUT_MS = 5 * 1000;
    // 会话过期时间，默认为15000ms
    protected int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;
    // zookeeper 地址，如果是集群用','分开
    private final String url;

    public AbstractZookeeperClient(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
