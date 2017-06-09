package com.ysj.myblog.entity;

/**
 * @author yusijia
 * @Description:
 */
public enum ResultStatusCode {

    PARAMETER_ERROR(30008, "Parameter error"),
    SUCCESS(20000, "success"),
    REQUEST_SUCCESS(20001, "request success"),
    FAILURE(40000, "failure"),

    ;

    private final Integer code;
    private final String message;

    ResultStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
