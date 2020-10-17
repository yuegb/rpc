package com.rpc.core.switcher;

/**
 * @auther yuegb
 * @Date: 2020-10-16 11:02
 * @Description: 服务升降级实体类
 */
public class Switcher {

    private boolean on = true;
    private String name;

    public Switcher(String name, boolean on) {
        this.name = name;
        this.on = on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * isOn: true，服务可用; isOn: false, 服务不可用
     *
     * @return
     */
    public boolean isOn() {
        return on;
    }

    /**
     * turn on switcher
     */
    public void onSwitcher() {
        this.on = true;
    }

    /**
     * turn off switcher
     */
    public void offSwitcher() {
        this.on = false;
    }

}
