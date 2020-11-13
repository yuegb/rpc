package com.core.common.exception;

import com.core.common.enums.ErrorCode;

/**
 * @Date: 2020-11-13 15:28
 * @Description:
 */
public class RpcException extends RuntimeException {

    private Integer code;

    private String msg;

    private ErrorCode errorCode;


    public RpcException(ErrorCode errorCode) {
        this.code = errorCode.getValue();
        this.msg = errorCode.getName();
        this.errorCode = errorCode;
    }

    public RpcException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
        errorCode.setName(msg);
    }

    public RpcException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RpcException() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
