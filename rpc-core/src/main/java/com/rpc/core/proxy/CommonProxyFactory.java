package com.rpc.core.proxy;

import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.SpiMeta;

import java.util.List;

/**
 * @Date: 2020-10-19 15:14
 * @Description:
 */
@SpiMeta(name="common")
public class CommonProxyFactory implements ProxyFactory  {
    @Override
    public <T> T getProxy(Class<T> clz, List<Cluster<T>> clusters) {
        return (T) new RefererCommonHandler(clusters.get(0).getUrl().getPath(), clusters);
    }
}
