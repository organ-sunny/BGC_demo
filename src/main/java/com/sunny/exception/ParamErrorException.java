package com.sunny.exception;

public class ParamErrorException extends RuntimeException{
    public ParamErrorException(){
        super("入参缺失或错误！");
    }
}
