package com.rpc.register.base;

/**
 * @Auther: yuegb
 * @Date: 2020-2-26 16:58
 * @Description:rpc注册中心工厂
 */
public interface RegistryFactory {

    /**
     * 连接注册中心
     *
     * 连接注册中心需要处理契约：<br>
     * 1.当设置check=false时，标识不检查连接，否则连接不上时抛出异常。<br>
     * 2.支持timeout=10000请求超时设置。<br>
     * 3.支持session=60000会话超时设置。<br>
     * 4.支持file=register.cache本地磁盘文件缓存。<br>
     * 5.支持url上面username:password权限验证。<br>
     * @param url /rpc/com.rpc.service/provides/127.0.0.1:8080
     * @return
     */
    Registry getRegister(URL url);

}
