package com.sunny.service;

import com.sunny.service.impl.SendMailServiceImpl;
import org.springframework.stereotype.Service;

@Service
public interface SendMailService {

    Integer sendMail(String mailAddress);

    /**
     * 获取保存的验证码
     * */
    SendMailServiceImpl.MailCode getCode(String mailAddress);

}
