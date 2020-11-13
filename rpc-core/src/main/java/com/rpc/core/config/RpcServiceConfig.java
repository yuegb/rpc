package com.rpc.core.config;

/**
 * @Date: 2020-11-13 14:55
 * @Description:
 * @author yuegb
 */
public class RpcServiceConfig extends AbstractInterfaceConfig {

    private static final long serialVersionUID = 5592540505131362429L;

    private int actives;

    public int getActives() {
        return actives;
    }

    public void setActives(int actives) {
        this.actives = actives;
    }

}
