package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.UserService;
import com.sunny.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("userManage")
public class UserManageController {

    @Resource
    private UserService userService;

    @PostMapping("deleteUser")
    public ResponseEntity deleteUser() {
        // 用户注销
        userService.deleteUser();
        return ResponseEntity.normalReturn("注销成功",200,null);
    }

    @PostMapping("showUser")
    public ResponseEntity showUser() {
        // 展示用户信息
        UserVO userVO = userService.showUser().getVO();
        return ResponseEntity.normalReturn("success",200,userVO);
    }

}
