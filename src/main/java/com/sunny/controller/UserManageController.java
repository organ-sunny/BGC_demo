package com.sunny.controller;

import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.SendMailService;
import com.sunny.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("userManage")
public class UserManageController {

    @Resource
    private UserService userService;

    @PostMapping("deleteUser")
    public ResponseEntity deleteUser(@RequestBody(required = false) HttpServletRequest httpServletRequest) {
        // 获取token
        String token = httpServletRequest.getHeader("token");
        if (token == null ){
            return ResponseEntity.normalReturn("token缺失",503,null);
        }

        // 用户注销
        userService.deleteUser(token);
        return ResponseEntity.normalReturn("注销成功",200,null);
    }
}
