package com.rpc.core.rpc;

import java.util.concurrent.Executor;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:57
 * @Description:
 */
public interface CallBackable {

    void addFinishCallback(Runnable runnable, Executor executor);

    void onFinish();
}