package com.rpc.core.serialize;

import java.io.IOException;

/**
 * @author yuegb
 * @Date: 2020-10-19 13:46
 * @Description:
 */
public class DeserializableObject {

    private Serialization serialization;

    private byte[] objBytes;

    public DeserializableObject(Serialization serialization, byte[] objBytes) {
        this.serialization = serialization;
        this.objBytes = objBytes;
    }

    public <T> T deserialize(Class<T> clz) throws IOException {
        return serialization.deserialize(objBytes, clz);
    }

    public Object[] deserializeMulti(Class<?>[] paramTypes) throws IOException {
        Object[] ret = null;
        if (paramTypes != null && paramTypes.length > 0) {
            ret = serialization.deserializeMulti(objBytes, paramTypes);
        }
        return ret;
    }
}
