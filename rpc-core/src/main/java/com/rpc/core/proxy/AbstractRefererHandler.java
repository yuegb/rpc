package com.rpc.core.proxy;

import com.rpc.core.cluser.Cluster;

import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:59
 * @Description:
 */
public class AbstractRefererHandler<T> {

    protected List<Cluster<T>> clusterList;

    protected Class<T> clz;


}
