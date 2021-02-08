package com.sunny.controller;

import com.sunny.dto.ObjectModuleDTO;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ObjectModuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/objectModule")
public class ObjectModuleController {
    @Resource
    private ObjectModuleService objectModuleService;

    @GetMapping("query")
    public ResponseEntity queryModule(ObjectModuleDTO objectModuleDTO) {

        List<ObjectModuleEntity> result = objectModuleService.queryModule(objectModuleDTO);
        return ResponseEntity.normalReturn("success", 200, result);
    }

    @PostMapping("add")
    public ResponseEntity addModule(@RequestBody(required = false) ObjectModuleDTO objectModuleDTO) {

        objectModuleDTO.check();

        objectModuleService.addModule(objectModuleDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

}
