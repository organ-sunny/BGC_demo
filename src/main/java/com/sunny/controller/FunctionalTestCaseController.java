package com.sunny.controller;

import com.sunny.dto.FunctionalTestCaseDTO;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.FunctionalTestCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/functionalTestCase")
public class FunctionalTestCaseController {

    @Resource
    private FunctionalTestCaseService functionalTestCaseService;

    @GetMapping
    public ResponseEntity query(Integer id) {
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @DeleteMapping
    public ResponseEntity delete(Integer id) {
        functionalTestCaseService.deleteFunctionalCase(id);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @PostMapping
    public ResponseEntity add(FunctionalTestCaseDTO functionalTestCaseDTO) {
        functionalTestCaseDTO.check();
        functionalTestCaseService.addFunctionalCase(functionalTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }
}
