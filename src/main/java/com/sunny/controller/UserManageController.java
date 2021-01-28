package com.sunny.controller;

import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.UserService;
import com.sunny.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/userManage")
public class UserManageController {

    @Resource
    private UserService userService;

    // 用户注销
    @PostMapping("deleteUser")
    public ResponseEntity deleteUser() {

        userService.deleteUser();
        return ResponseEntity.normalReturn("注销成功", 200, null);
    }

    // 展示用户信息
    @PostMapping("showUser")
    public ResponseEntity showUser() {
        UserVO userVO = userService.showUser().getVO();
        return ResponseEntity.normalReturn("success", 200, userVO);
    }

    // 编辑用户信息
    @PostMapping("editUser")
    public ResponseEntity editUser(@RequestBody(required = false) UserDTO userDTO) {
        userService.editUser(userDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

}
