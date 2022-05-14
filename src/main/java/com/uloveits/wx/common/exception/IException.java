package com.uloveits.wx.common.exception;

/**
 * 自定义异常基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public abstract class IException extends RuntimeException {
    private static final long serialVersionUID = -1582874427218948396L;
    private String code;

    public IException() {
    }

    public IException(String message) {
        super(message);
    }

    public IException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
