package com.sunny.service.impl;

import com.sunny.service.SendMailService;
import com.sunny.util.RandomNumUtil;
import com.sunny.util.RegexUtil;
import com.sunny.util.SendMailUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendMailServiceImpl implements SendMailService {

    /**
     * 验证码缓存
     * */
    private static final Map<String, Object[]> MAIL_CODE_MAP = new HashMap<>();

    @Override
    public Object[] getCode(String mailAddress) {
        return MAIL_CODE_MAP.get(mailAddress);
    }


    @Override
    public Integer sendMail(String mailAddress) {
        try {
            Integer mailCode = RandomNumUtil.randomNum();
            if(RegexUtil.isMailAddress(mailAddress)){
                SendMailUtil.sendCode(mailAddress, "邮箱验证邮件","欢迎使用邮箱登录/注册", mailCode);
            } else {
                throw new RuntimeException("邮箱错误");
            }

            // 保存
            MAIL_CODE_MAP.put(mailAddress, new Object[]{mailCode,new Date()});


            return mailCode;
        }catch (Exception e){
            throw new RuntimeException("邮箱验证码发送失败！");
        }
    }
}