package com.rpc.register.base;

import java.util.List;

/**
 * @Auther: yuegb
 * @Date: 2020-2-26 17:07
 * @Description:注册中心基本服务
 * <p>
 *     注册中心url：例：rpc/com.rpc.service/providers/10.20.153.10:8080
 *                 例：rpc/com.rpc.service/consumers/10.20.153.10:8080
 * </p>
 */
public interface RegistryService {

    /**
     * 注册服务.
     *
     * 1. 当URL设置了check=false时，注册失败后不报错，在后台定时重试，否则抛出异常。<br>
     * 2. 当注册中心重启，网络抖动，不能丢失数据，包括断线自动删除数据。<br>
     *
     * @param url 注册信息，不允许为空
     */
    void register(URL url);

    /**
     * 取消注册服务.
     *
     * 1.按照全url取消匹配注册。<br>
     *
     * @param url 取消注册的url
     */
    void unregister(URL url);

    /**
     * 订阅服务.
     *
     * 1. 如果没有订阅，直接忽略。<br>
     * 2. 按全URL匹配取消订阅。<br>
     *
     * @param url       订阅条件，不允许为空。
     * @param listener  变更事件监听器，不允许为空
     */
    void subscribe(URL url, NotifyListener listener);

    /**
     * 取消订阅服务.
     *
     * 1. 如果没有订阅，直接忽略。<br>
     * 2.按全URL匹配取消订阅。<br>
     *
     * @param url       订阅条件，不允许为空。
     * @param listener  变更事件监听器，不允许为空
     */
    void unsubscribe(URL url, NotifyListener listener);

    /**
     * 查询注册列表.
     *
     * @param url 查询条件，不允许为空，
     * @return    已注册信息列表，可能为空
     */
    List<String> lookup(URL url);
}
