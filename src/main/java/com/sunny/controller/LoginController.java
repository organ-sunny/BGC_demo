package com.sunny.controller;


import com.sunny.dto.UserDTO;
import com.sunny.entity.ParentEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.LoginService;
import com.sunny.util.ClassUtil;
import com.sunny.vo.LoginVO;
import com.sunny.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserDTO userDTO){

        // 参数校验
        userDTO.loginCheckParam();

        // 进行登录
        try {
            UserEntity userEntity = loginService.login(userDTO);
            // 返回响应
            LoginVO loginVO = ClassUtil.transformation(userEntity, LoginVO.class);
            return ResponseEntity.normalReturn("success", 200, loginVO);
        }catch (Exception e){
            return ResponseEntity.normalReturn(e.getMessage(), 500, null);
        }
    }
}
