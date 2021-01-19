package com.sunny.controller;


import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.LoginService;
import com.sunny.vo.UserVO;
import org.aspectj.weaver.ast.Var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserDTO userDTO){

        // 参数校验
        userDTO.loginCheckParam();

        // 进行登录
        UserEntity userEntity = loginService.login(userDTO);

        // 返回响应
        UserVO userVO = userEntity.getVO();
        return ResponseEntity.normalReturn("登录成功", 200, userVO);

    }
}
