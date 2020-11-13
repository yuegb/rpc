package com.rpc.core.config;

/**
 * @author yuegb
 * @Date: 2020-11-13 14:33
 * @Description:
 */
public class AbstractInterfaceConfig extends AbstractConfig {

    private static final long serialVersionUID = 4391764000467491522L;

    protected String interfaceName;

    protected String group;

    protected String version;

    protected String proxy;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }
}
