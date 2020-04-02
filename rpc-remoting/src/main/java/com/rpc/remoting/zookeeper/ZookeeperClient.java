package com.rpc.remoting.zookeeper;

import java.util.List;

/**
 * @Auther: yuegb
 * @Date: 2020-3-16 15:21
 * @Description:
 */
public interface ZookeeperClient {

    /**
     * 创建持久化节点
     * @param path
     */
    void createPersistent(String path);

    /**
     * 创建临时节点
     * @param path
     */
    void createEphemeral(String path);

    /**
     * 删除节点
     * @param path
     */
    void deletePath(String path);

    /**
     * 获取子节点结合
     * @param path
     * @return
     */
    List<String> getChildren(String path);

    /**
     * 判断节点是否存在
     * @param path
     * @return
     */
    boolean checkExists(String path);
}
