package com.rpc.core.center;

import com.core.common.exception.RpcException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuegb
 * @Date: 2020-11-13 15:03
 * @Description:rpc url
 * <p>
 *     rpc协议url
 *     注册结构:rpc://service//provider//127.0.0.1:8081
 *     url示例:rpc://127.0.0.1:8081/com.test.TestService?retries=1&async=false
 * </>
 *
 */
public class URL {

    public static final String PROTOCOL_SEPARATOR = "://";

    public static final String PORT_SEPARATOR = "/";

    private String protocol;

    private String hostName;

    private Integer port;

    // interfaceName
    private String path;

    private Map<String, String> parameters;

    public URL(String protocol, String hostName, int port, String path) {
        this(protocol, hostName, port, path, new HashMap<String,String>());
    }

    public URL(String protocol, String hostName, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.hostName = hostName;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    /**
     * 格式化url
     * @param url
     * @return
     */
    public static URL reserves(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RpcException("url 为空！");
        }
        // 协议类型
        String protocol = null;
        // 地址
        String host = null;
        // 端口号
        int port = 0;
        // interfaceName
        String path = null;
        // 参数集合
        Map<String, String> parameters = new HashMap<String, String>();
        int i = url.indexOf("?");
        if (i > 0) {
            String[] parts = url.substring(i + 1).split("\\&");
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j >= 0) {
                        parameters.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        parameters.put(part, part);
                    }
                }
            }
            // 获取到去除参数后的url
            url = url.substring(0, i);
        }
        i = url.indexOf(PROTOCOL_SEPARATOR);
        if (i >= 0) {
            if (i == 0) {
                throw new IllegalStateException("url missing protocol: " + url);
            }
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        }
        i = url.indexOf(PORT_SEPARATOR);
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if (url.length() > 0){
            host = url;
        }
        return new URL(protocol, host, port, path, parameters);
    }
}
