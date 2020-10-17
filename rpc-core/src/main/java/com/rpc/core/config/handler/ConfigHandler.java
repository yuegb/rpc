package com.rpc.core.config.handler;

import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.Scope;
import com.rpc.core.extension.Spi;
import com.rpc.core.rpc.Exporter;
import com.rpc.core.rpc.URL;

import java.util.Collection;
import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-15 16:17
 * @Description:处理配置中心的url
 */
@Spi(scope = Scope.SINGLETON)
public interface ConfigHandler {

    <T> T refer(Class<T> interfaceClass, List<Cluster<T>> cluster, String proxyType);

    /**
     * 服务暴露
     * @param interfaceClass
     * @param ref
     * @param registryUrls
     * @param serviceUrl
     * @param <T>
     * @return
     */
    <T> Exporter<T> export(Class<T> interfaceClass, T ref, List<URL> registryUrls, URL serviceUrl);


    <T> void unexport(List<Exporter<T>> exporters, Collection<URL> registryUrls);
}