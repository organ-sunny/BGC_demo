package com.sunny.controller;

import com.sunny.dto.UserDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.SecurityCenterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/securityCenter")
public class SecurityCenterController {

    @Resource
    private SecurityCenterService securityCenterService;

    @RequestMapping("updateEmail")
    public ResponseEntity updateEmail(@RequestBody(required = false) UserDTO userDTO) {
        try {
            securityCenterService.updateEmail(userDTO);
            return ResponseEntity.normalReturn("success", 200, null);
        } catch (Exception e) {
            return ResponseEntity.normalReturn(e.getMessage(), 101, null);
        }
    }

    @RequestMapping("updatePassword")
    public ResponseEntity updatePassword(@RequestBody(required = false) UserDTO userDTO) {
        try {
            securityCenterService.updatePassword(userDTO);
            return ResponseEntity.normalReturn("success", 200, null);
        } catch (Exception e) {
            return ResponseEntity.normalReturn(e.getMessage(), 101, null);
        }
    }
}
