package com.rpc.core.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date: 2020-10-19 14:14
 * @Description:
 * 通过requestId能够知道大致请求的时间,目前是 currentTimeMillis * (2^20) + offset.incrementAndGet()
 * 通过 requestId / (2^20 * 1000) 能够得到秒
 */
public class RequestIdGenerator {

    protected static final AtomicLong offset = new AtomicLong(0);

    protected static final int BITS = 20;

    protected static final long MAX_COUNT_PER_MILLIS = 1 << BITS;

    /**
     * 获取requestId
     * @return
     */
    public static long getRequestId() {
        long currentTime = System.currentTimeMillis();
        long count = offset.incrementAndGet();
        while(count >= MAX_COUNT_PER_MILLIS){
            synchronized (RequestIdGenerator.class){
                if(offset.get() >= MAX_COUNT_PER_MILLIS){
                    offset.set(0);
                }
            }
            count = offset.incrementAndGet();
        }
        return (currentTime << BITS) + count;
    }
}
