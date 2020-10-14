package com.core.common.exception;

import com.core.common.enums.ErrorCode;

/**
 * @author yuegb
 * @Date: 2020-10-14 16:33
 * @Description: 自定义异常实现
 */
public class RpcException extends RuntimeException {

    private String errorMsg = null;

    private ErrorCode errorCode = null;

    public RpcException(String message) {
        super(message);
        this.errorCode = ErrorCode.SERVICE_DEFAULT_ERROR;
        this.errorMsg = message;
    }

    public RpcException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public RpcException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public RpcException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

}
