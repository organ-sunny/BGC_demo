package com.sunny.controller;

import com.sunny.service.SendMailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("userManage")
public class UserManageController {

    @Resource
    private SendMailService bindMailService;
}
