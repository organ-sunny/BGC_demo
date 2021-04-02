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

    @PostMapping
    public ResponseEntity add(@RequestBody(required = false) FunctionalTestCaseDTO functionalTestCaseDTO) {
        functionalTestCaseDTO.check();
        functionalTestCaseService.addFunctionalCase(functionalTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @DeleteMapping
    public ResponseEntity delete(Integer id) {
        functionalTestCaseService.deleteFunctionalCase(id);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @PutMapping("{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody FunctionalTestCaseDTO functionalTestCaseDTO) {
        functionalTestCaseDTO.check();
        functionalTestCaseService.editFunctionalCase(id, functionalTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @GetMapping
    public ResponseEntity query(Integer id) {
        return ResponseEntity.normalReturn("success", 200, null);
    }


}
