package com.rpc.core.config.handler;

import com.core.common.enums.RpcConstants;
import com.rpc.core.cluser.Cluster;
import com.rpc.core.extension.ExtensionLoader;
import com.rpc.core.proxy.ProxyFactory;
import com.rpc.core.extension.SpiMeta;
import com.rpc.core.rpc.Exporter;
import com.rpc.core.rpc.URL;

import java.util.Collection;
import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:38
 * @Description: 通过refurl获取refers，创建代理对象
 */
@SpiMeta(name = RpcConstants.DEFAULT_VALUE)
public class SimpleConfigHandler implements ConfigHandler {

    @Override
    public <T> T refer(Class<T> interfaceClass, List<Cluster<T>> cluster, String proxyType) {
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(proxyType);
        return proxyFactory.getProxy(interfaceClass, cluster);
    }

    @Override
    public <T> Exporter<T> export(Class<T> interfaceClass, T ref, List<URL> registryUrls, URL serviceUrl) {
        // export service

        return null;
    }

    @Override
    public <T> void unexport(List<Exporter<T>> exporters, Collection<URL> registryUrls) {

    }
}
