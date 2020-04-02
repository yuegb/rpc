package com.rpc.register.base;

import java.util.List;

/**
 * @Auther: yuegb
 * @Date: 2020-2-26 17:16
 * @Description: 当注册中心注册服务发生变化时，需要通知消费者。
 */
public interface NotifyListener {

    /**
     * 当收到服务变更通知时触发。
     *
     * 1. 通知者(即注册中心实现)需保证通知的顺序，比如：单线程推送，队列串行化，带版本对比。<br>
     *
     * @param urlList 已注册信息列表，总不为空。
     */
    void notify(List<URL> urlList);
}
