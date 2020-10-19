package com.core.common.enums;

/**
 * @author yuegb
 * @Date: 2020-10-14 17:34
 * @Description:
 */
public class RpcConstants {
    // 异步调用标识
    public static final String ASYNC_SUFFIX = "async";

    public static final String DEFAULT_CHARACTER = "utf-8";

    public static final String DEFAULT_VALUE = "default";

    public static final String PROTOCOL_RPC = "rpc";

    public static final String PROXY_JDK = "jdk";

    public static final String PROTOCOL_SWITCHER_PREFIX = "protocol:";

    // 事件类型，客户端发送数据
    public static final String TRACE_CSEND = "TRACE_CSEND";
    // 服务端接受数据
    public static final String TRACE_SRECEIVE = "TRACE_SRECEIVE";
    // 调用
    public static final String TRACE_INVOKE = "TRACE_INVOKE";

    public static final String PROTOCOL_SEPARATOR = "://";

    public static final String PATH_SEPARATOR = "/";
}
