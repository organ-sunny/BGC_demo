package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.service.SendMailService;
import com.sunny.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class MailCodeController {

    @Resource
    private SendMailService sendMailService;

    @PostMapping("sendMailCode")
    public ResponseEntity sendMailCode(@RequestBody(required = false) Map<String, String> RequestBody) {
        try {
            String mailAddress = RequestBody.get("mailAddress");
            if (StringUtil.isEmpty(mailAddress)) {
                throw new ParamErrorException("注册邮箱不能为空！ ");
            }
            Integer mailCode = sendMailService.sendMail(mailAddress);
            return ResponseEntity.normalReturn("success",200, mailCode);
        }catch (Exception e){
            return ResponseEntity.normalReturn(e.getMessage(),500, null);
        }
    }

}
