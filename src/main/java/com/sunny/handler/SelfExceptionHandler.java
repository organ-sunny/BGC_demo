package com.sunny.handler;

import com.sunny.entity.ResponseEntity;
import com.sunny.exception.BusinessException;
import com.sunny.exception.ParamErrorException;
import com.sunny.exception.TokenException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 */
@ControllerAdvice
@ResponseBody
public class SelfExceptionHandler {

    /**
     * token异常全局处理
     */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity TokenException(TokenException tokenException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(101);
        responseEntity.setMessage(tokenException.getMessage());
        return responseEntity;
    }

    /**
     * 业务异常全局处理
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity BusinessException(BusinessException businessException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(102);
        responseEntity.setMessage(businessException.getMessage());
        return responseEntity;
    }

    /**
     * 参数异常全局处理
     */
    @ExceptionHandler(ParamErrorException.class)
    public ResponseEntity ParamErrorException(ParamErrorException paramErrorException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(103);
        responseEntity.setMessage(paramErrorException.getMessage());
        return responseEntity;
    }

    /**
     * JSON错误全局处理
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(401);
        responseEntity.setMessage(httpMessageNotReadableException.getMessage());
        return responseEntity;
    }
}