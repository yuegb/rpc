package com.rpc.core.switcher;

import com.core.common.exception.RpcException;
import com.rpc.core.extension.SpiMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell
 * @Date: 2020-10-16 11:15
 * @Description:
 */
@SpiMeta(name = "defaultSwitcherService")
public class DefaultSwitcherService implements SwitcherService {

    // 接口开关缓存
    private static ConcurrentHashMap<String, Switcher> switchers = new ConcurrentHashMap<>();

    // 接口监听器集合缓存
    private ConcurrentHashMap<String, List<SwitcherListener>>listenerMap = new ConcurrentHashMap<>();

    public static Switcher getSwitcherStatic(String name) {
        return switchers.get(name);
    }

    public static void putSwitcher(Switcher switcher) {
        if (switcher == null) {
            throw new RpcException("LocalSwitcherService addSwitcher Error: switcher is null");
        }

        switchers.put(switcher.getName(), switcher);
    }

    @Override
    public Switcher getSwitcher(String name) {
        return switchers.get(name);
    }

    @Override
    public List<Switcher> getAllSwitchers() {
        return new ArrayList<>(switchers.values());
    }

    @Override
    public void initSwitcher(String switcherName, boolean initialValue) {
        setValue(switcherName, initialValue);
    }

    @Override
    public boolean isOpen(String switcherName) {
        return getSwitcher(switcherName).isOn();
    }

    @Override
    public void setValue(String switcherName, boolean value) {
        putSwitcher(new Switcher(switcherName, value));
        List<SwitcherListener> listeners = listenerMap.get(switcherName);
        if(listeners != null) {
            for (SwitcherListener listener : listeners) {
                listener.onValueChanged(switcherName, value);
            }
        }
    }

    @Override
    public void registerListener(String switcherName, SwitcherListener listener) {
        List listeners = Collections.synchronizedList(new ArrayList<>());
        List preListeners = listenerMap.putIfAbsent(switcherName, listeners);
        if (preListeners == null) {
            listeners.add(listener);
            listenerMap.put(switcherName, listeners);
        } else {
            preListeners.add(listener);
        }
    }

    @Override
    public void unRegisterListener(String switcherName, SwitcherListener listener) {
        List<SwitcherListener> listeners = listenerMap.get(switcherName);
        if (listener == null) {
            listeners.clear();
        } else {
            listeners.remove(listener);
        }
    }
}
