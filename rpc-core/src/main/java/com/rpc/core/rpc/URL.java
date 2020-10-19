package com.rpc.core.rpc;

import com.core.common.enums.RpcConstants;
import com.core.common.enums.URLParamType;
import com.core.common.exception.RpcException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther yuegb
 * @Date: 2020-10-14 17:26
 * @Description:rpc://127.0.0.1:8080/com.demo.Service?group=motan-demo-rpc
 */
public class URL {

    private String protocol;

    private String host;

    private int port;

    private String path;

    private Map<String, String> parameters;

    public URL(String protocol, String host, int port, String path) {
        this(protocol, host, port, path, new HashMap<String, String>());
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = removeAsyncPath(path);
        this.parameters = parameters;
    }

    public static URL valueOf(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RpcException("url is null");
        }
        String protocol = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = new HashMap<String, String>();;
        int i = url.indexOf("?"); // seperator between body and parameters
        if (i >= 0) {
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
            url = url.substring(0, i);
        }
        i = url.indexOf("://");
        if (i >= 0) {
            if (i == 0){
                throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            }
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            i = url.indexOf(":/");
            if (i >= 0) {
                if (i == 0){
                    throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                }
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }

        i = url.indexOf("/");
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

    /**
     * 因为客户端路径中的异步调用带有异步后缀，所以我们需要在订阅的路径中删除异步后缀。
     * @param path
     * @return
     */
    private String removeAsyncPath(String path){
        if (path != null && path.endsWith(RpcConstants.ASYNC_SUFFIX)) {
            return path.substring(0, path.length() - RpcConstants.ASYNC_SUFFIX.length());
        }
        return path;
    }

    public String getParameter(String name, String defaultValue) {
        String value = getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    private String getParameter(String name) {
        return parameters.get(name);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getGroup() {
        return getParameter(URLParamType.group.getName(), URLParamType.group.getValue());
    }

    public int getPort() {
        return port;
    }

    public String toSimpleString() {
        return getUri() + "?group=" + getGroup();
    }
    public String getUri() {
        return protocol + RpcConstants.PROTOCOL_SEPARATOR + host + ":" + port +
                RpcConstants.PATH_SEPARATOR + path;
    }

    public String getPath() {
        return path;
    }
}
