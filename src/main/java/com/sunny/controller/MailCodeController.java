package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.service.SendMailService;
import com.sunny.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("user")
public class MailCodeController {

    @Resource
    private SendMailService sendMailService;

    @PostMapping("sendMailCode")
    public ResponseEntity sendMailCode(@RequestBody(required = false) Map<String, String> RequestBody) {
        String mailAddress = RequestBody.get("mailAddress");
        if (StringUtil.isEmpty(mailAddress)) {
            throw new ParamErrorException();
        }

        Integer mailCode = sendMailService.sendMail(mailAddress);

        return ResponseEntity.normalReturn("邮箱验证码发送成功",200, mailCode);
    }

}
