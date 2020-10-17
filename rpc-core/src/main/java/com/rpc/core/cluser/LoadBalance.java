package com.rpc.core.cluser;

import com.rpc.core.rpc.Referer;
import com.rpc.core.rpc.Request;

import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-15 11:30
 * @Description: 负载均衡策略
 */
public interface LoadBalance<T> {

    /**
     * 刷新引用列表
     * @param refers
     */
    void onRefresh(List<Referer<T>> refers);

    /**
     * 根据不同负载均衡策略选择不同的refer进行调用
     * 具体策略由子类进行实现
     * @param request
     * @return
     */
    Referer<T> select(Request request);
}
