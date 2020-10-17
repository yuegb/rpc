package com.rpc.core.switcher;

/**
 * @author yuegb
 * @Date: 2020-10-16 11:11
 * @Description:
 */
public interface SwitcherListener {

    void onValueChanged(String key,Boolean value);
}