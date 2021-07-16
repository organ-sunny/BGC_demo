package com.sunny.controller;

import com.sunny.dto.AutoTestModuleDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.AutoTestModuleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/autoTestModule")
public class AutoTestModuleController {

    @Resource
    private AutoTestModuleService autoTestModuleService;

    @PostMapping("query")
    public ResponseEntity query() {
        return ResponseEntity.normalReturn("success", 200, autoTestModuleService.query());
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody(required = false) AutoTestModuleDTO autoTestModuleDTO) {
        autoTestModuleService.save(autoTestModuleDTO);
        return ResponseEntity.returnTrue();
    }

}