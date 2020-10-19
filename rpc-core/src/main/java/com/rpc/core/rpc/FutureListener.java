package com.rpc.core.rpc;

/**
 * @Date: 2020-10-19 11:13
 * @Description:
 */
public interface FutureListener {

    /**
     * <pre>
     * 		建议做一些比较简单的低功耗的操作
     *
     * 		注意一些反模式：
     *
     * 		1) 死循环：
     * 			operationComplete(Future future) {
     * 					......
     * 				future.addListener(this);  // 类似于这种操作，后果你懂的
     * 					......
     * 			}
     *
     * 		2）耗资源操作或者慢操作：
     * 			operationComplete(Future future) {
     * 					......
     * 				Thread.sleep(500);
     * 					......
     * 			}
     *
     * </pre>
     *
     * @param future
     * @throws Exception
     */
    void operationComplete(Future future) throws Exception;
}