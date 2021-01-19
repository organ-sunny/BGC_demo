package com.sunny.service;

import com.sunny.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface SendMailService {

    Integer sendMail(String mailAddress);

    /**
     * 获取保存的验证码
     * */
    Integer getCode(String mailAddress);

}
