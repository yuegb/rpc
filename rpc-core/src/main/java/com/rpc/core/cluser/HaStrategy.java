package com.rpc.core.cluser;

import com.rpc.core.rpc.Request;
import com.rpc.core.rpc.Response;
import com.rpc.core.rpc.URL;

/**
 * @auther yuegb
 * @Date: 2020-10-15 11:47
 * @Description:高可用策略
 */
public interface HaStrategy<T> {

    void setUrl(URL url);

    Response call(Request request, LoadBalance<T> loadBalance);
}