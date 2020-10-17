package com.rpc.core.cluser;

import com.rpc.core.rpc.Caller;
import com.rpc.core.rpc.Referer;
import com.rpc.core.rpc.URL;

import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-15 11:19
 * @Description:服务提供者broker
 */
public interface Cluster<T> extends Caller<T> {

    @Override
    void init();

    void setUrl(URL url);

    void setLoadBalance(LoadBalance<T> loadBalance);

    void setHaStrategy(HaStrategy<T> haStrategy);

    void onRefresh(List<Referer<T>> referers);

    List<Referer<T>> getReferers();

    LoadBalance<T> getLoadBalance();
}