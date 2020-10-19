package com.rpc.core.serialize;

import com.rpc.core.extension.Scope;
import com.rpc.core.extension.Spi;

import java.io.IOException;

/**
 * @Date: 2020-10-19 13:47
 * @Description:
 */
@Spi(scope= Scope.PROTOTYPE)
public interface Serialization {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<?> clz);

    byte[] serializeMulti(Object[] data) throws IOException;

    Object[] deserializeMulti(byte[] bytes, Class<?>[] classes);

    /**
     * serialization 的唯一编号，用于传输协议中指定序列化方式。每种序列化的方式必须唯一
     * @return
     */
    int getSerializationNumber();
}