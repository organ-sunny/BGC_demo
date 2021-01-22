package com.sunny.handler;

import com.sunny.entity.ResponseEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.exception.TokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 *
 * */
@ControllerAdvice
@ResponseBody
public class SelfExceptionHandler {

    /**
     * 处理请求参数异常
     * */
    @ExceptionHandler(ParamErrorException.class)
    public ResponseEntity ParamErrorException(ParamErrorException paramErrorException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(501);
        responseEntity.setMessage(paramErrorException.getMessage());
        return responseEntity;
    }

    /**
     * 处理token异常
     * */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity TokenException(TokenException tokenException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(505);
        responseEntity.setMessage(tokenException.getMessage());
        return responseEntity;
    }

//
//    /**
//     * 处理runtime
//     * */
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity RuntimeException(RuntimeException RuntimeException) {
//        ResponseEntity responseEntity = new ResponseEntity();
//        responseEntity.setCode(101);
//        responseEntity.setMessage(RuntimeException.getMessage());
//        return responseEntity;
//    }

}