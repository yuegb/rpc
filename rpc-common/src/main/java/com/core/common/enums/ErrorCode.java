package com.core.common.enums;

/**
 * @Auther: yuegb
 * @Date: 2020-3-2 20:36
 * @Description:自定义异常枚举
 */
public enum ErrorCode {

    SUCCESS(200, "成功"),
    PARAMS_ERROR(50002, "参数错误！"),
    NO_AUTHORITY(50005, "没有权限！"),
    SERVER_ERROR(500, "服务器异常");

    private Integer value;

    private String name;

    ErrorCode(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }
}
