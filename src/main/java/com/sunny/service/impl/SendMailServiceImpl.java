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
     */
    private static final Map<String, Object[]> MAIL_CODE_MAP = new HashMap<>();

    @Override
    public Object[] getCode(String mailAddress) {
        return MAIL_CODE_MAP.get(mailAddress);
    }


    @Override
    public Integer sendMail(String mailAddress) {

        /**
         * 先进行逻辑校验，逻辑校验通过后再进入业务逻辑代码
         * 1、邮箱格式校验
         * 2、发送邮箱验证码接口是否被频繁调用
         * 3、...
         */
        // 验证邮箱格式
        if (!RegexUtil.isMailAddress(mailAddress)) {
            throw new RuntimeException("邮箱输入错误");
        }

        // 验证接口是否频繁调用
        Object[] objects = MAIL_CODE_MAP.get(mailAddress);
        if (objects != null) {
            Date date = (Date) objects[1];
            if (new Date().getTime() - date.getTime() < 60 * 1000) {
                throw new RuntimeException("请勿频繁操作！");
            }
        }

        /**
         * 以下为业务代码，在逻辑校验通过后，会被执行。
         */
        // 发送验证码
        Integer mailCode = RandomNumUtil.randomNum();
        try {
            SendMailUtil.sendCode(mailAddress, "邮箱验证邮件", "欢迎使用邮箱登录/注册", mailCode);
        } catch(Exception e){
            throw new RuntimeException("邮箱验证码发送失败，失败原因：" + e.getMessage());
        }

        // 保存到 MAIL_CODE_MAP
        MAIL_CODE_MAP.put(mailAddress, new Object[]{mailCode, new Date()});

        return mailCode;
    }
}