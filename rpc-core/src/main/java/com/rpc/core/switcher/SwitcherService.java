package com.rpc.core.switcher;

import com.rpc.core.extension.Scope;
import com.rpc.core.extension.Spi;

import java.util.List;

/**
 * @auther yuegb
 * @Date: 2020-10-16 11:05
 * @Description:
 */
@Spi(scope = Scope.SINGLETON)
public interface SwitcherService {

    /**
     * 获取接口开关
     * @param name
     * @return
     */
    Switcher getSwitcher(String name);

    /**
     * 获取所有接口开关
     * @return
     */
    List<Switcher> getAllSwitchers();

    /**
     * 初始化开关
     * @param switcherName
     * @param initialValue
     */
    void initSwitcher(String switcherName, boolean initialValue);

    /**
     * 检查开关是否开启
     * @param switcherName
     * @return
     */
    boolean isOpen(String switcherName);

    /**
     * 设置开关状态
     * @param switcherName
     * @param value
     */
    void setValue(String switcherName, boolean value);

    /**
     * 为开关注册监听器，监听开关是否变化
     * @param switcherName
     * @param listener
     */
    void registerListener(String switcherName, SwitcherListener listener);

    /**
     * 取消监听器注册
     * @param switcherName
     * @param listener
     */
    void unRegisterListener(String switcherName, SwitcherListener listener);
}