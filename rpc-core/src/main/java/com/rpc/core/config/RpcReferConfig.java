package com.rpc.core.config;

/**
 * @Date: 2020-11-13 14:53
 * @Description:
 * @auther yuegb
 */
public class RpcReferConfig extends AbstractInterfaceConfig {

    private static final long serialVersionUID = 1634621557949746103L;

    private String directUrl;

    private Long requestTimeout;

    private boolean check;

    private Integer retries;

    private boolean async;

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }

    public Long getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(Long requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
