package com.sunny.exception;

public class ParamErrorException extends RuntimeException {

    public ParamErrorException(String s) {
        super(s);
    }

    public ParamErrorException() {
        super("请求参数错误或缺失");
    }

}
