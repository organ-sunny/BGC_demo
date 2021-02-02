package com.sunny.controller;

import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ObjectSystemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/objectSystem")
public class ObjectSystemController {

    @Resource
    private ObjectSystemService objectSystemService;

    @PostMapping("add")
    public ResponseEntity addObjectSystem(@RequestBody(required = false) ObjectSystemDTO objectSystemDTO) {

        // 参数校验
        objectSystemDTO.check();

        objectSystemService.addObjectSystem(objectSystemDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @GetMapping("query")
    public ResponseEntity queryObjectSystem(ObjectSystemDTO objectSystemDTO) {
        try {
            List<ObjectSystemEntity> result = objectSystemService.queryObjectSystem(objectSystemDTO);
            return ResponseEntity.normalReturn("success", 200, result);
        } catch (Exception e) {
            return ResponseEntity.normalReturn(e.getMessage(), 101, null);
        }
    }
}