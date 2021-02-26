package com.sunny.controller;


import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.LoginService;
import com.sunny.util.ClassUtil;
import com.sunny.dto.vo.LoginVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserDTO userDTO) {

        // 参数校验
        userDTO.loginCheckParam();

        // 进行登录
        try {
            UserEntity userEntity = loginService.login(userDTO);
            // 返回响应
            LoginVO loginVO = ClassUtil.transformation(userEntity, LoginVO.class);
            return ResponseEntity.normalReturn("success", 200, loginVO);
        } catch (Exception e) {
            return ResponseEntity.normalReturn(e.getMessage(), 500, null);
        }
    }

    @GetMapping("test/{city}")
    public String test(@PathVariable(required = false, name = "city") String city) {
        return city;
    }

}
