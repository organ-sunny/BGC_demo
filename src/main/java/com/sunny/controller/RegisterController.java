package com.sunny.controller;

import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserDTO userDTO) {
        // 参数校验
        userDTO.registerCheckParam();

        try {
            // 执行注册方法
            registerService.register(userDTO);
            return ResponseEntity.normalReturn("success", 200, null);
        } catch (Exception e) {
            return ResponseEntity.normalReturn(e.getMessage(), 101, null);
        }
    }
}
