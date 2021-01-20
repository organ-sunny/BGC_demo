package com.sunny.service;

import org.springframework.stereotype.Service;

@Service
public interface SendMailService {

    Integer sendMail(String mailAddress);

    /**
     * 获取保存的验证码
     */
    Object[] getCode(String mailAddress);

}
