package com.rpc.core.rpc;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:25
 * @Description:
 */
public interface Node {

    void init();

    void destroy();

    boolean isAvailable();

    String desc();

    URL getUrl();
}