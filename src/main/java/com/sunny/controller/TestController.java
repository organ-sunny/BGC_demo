package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.entity.UserEntity;
import com.sunny.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于试验RestFul API接口规范
 */

@RestController
@RequestMapping("/api/v1/users")
public class TestController {

    @Resource
    private UserService userService;

    // 新增
    @PostMapping
    public ResponseEntity add(@RequestBody UserEntity userEntity) {
        System.out.println(userEntity);
        return ResponseEntity.normalReturn("新增成功", 200, null);
    }

    // 删除用户
    @DeleteMapping
    public ResponseEntity delete() {
        userService.deleteUser();
        return ResponseEntity.normalReturn("删除成功", 200, null);
    }

    // 修改
    @PutMapping("{{id}}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserEntity userEntity) {
        System.out.println(id);
        System.out.println(userEntity);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 获取所有
    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.normalReturn("success", 200, userService.showUser());
    }

    // 通过id获取
    @GetMapping("{{id}}")
    public ResponseEntity getById(@PathVariable Long id) {
        System.out.println(id);
        return ResponseEntity.normalReturn("success", 200, null);
    }

}
