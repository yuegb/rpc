package com.rpc.core.rpc;

import com.rpc.core.extension.Scope;
import com.rpc.core.extension.Spi;

/**
 * @auther yuegb
 * @Date: 2020-10-16 10:11
 * @Description:
 */
@Spi(scope = Scope.PROTOTYPE)
public interface Provider<T> extends Caller<T> {

    Class<T> getInterface();

    T getImpl();
}