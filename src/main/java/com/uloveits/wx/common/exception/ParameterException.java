package com.uloveits.wx.common.exception;

/**
 * 参数异常
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class ParameterException extends IException {
    private static final long serialVersionUID = 7993671808524980055L;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String code, String message) {
        super(code, message);
    }

    @Override
    public String getCode() {
        String code = super.getCode();
        if (code == null) {
            code = "400";
        }
        return code;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = "参数错误";
        }
        return message;
    }
}
