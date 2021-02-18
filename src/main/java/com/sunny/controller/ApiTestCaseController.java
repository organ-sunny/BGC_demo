package com.sunny.controller;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ApiTestCaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/apiTestCase")
public class ApiTestCaseController {

    @Resource
    private ApiTestCaseService apiTestCaseService;

    @PostMapping("add")
    public ResponseEntity addApiCase(@RequestBody(required = false) ApiTestCaseDTO apiTestCaseDTO) {

        // 参数校验
        apiTestCaseDTO.check();

        // 执行新增
        apiTestCaseService.addApiCase(apiTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @GetMapping("query")
    public ResponseEntity queryApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        // 执行查询
        List<ApiTestCaseEntity> apiTestCaseEntityList = apiTestCaseService.queryApiCase(apiTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, apiTestCaseEntityList);
    }

//    @PostMapping("edit")
//    public ResponseEntity editApiCase(@RequestBody(required = false) ApiTestCaseDTO apiTestCaseDTO) {
//        // 参数校验
//        apiTestCaseDTO.check();
//
//        // 进行编辑
//        apiTestCaseService.editApiCase(apiTestCaseDTO);
//        return ResponseEntity.normalReturn("success", 200, null);
//    }

//    @PostMapping("runApiCase")
//    public ResponseEntity runApiCase(Integer apiCaseId) {
//        apiTestCaseService.runApiCase(apiCaseId);
//        return ResponseEntity.normalReturn("success", 200, null);
//    }

    @PostMapping("runApiCase")
    public ResponseEntity runApiCase(@RequestBody(required = false) List<Integer> integerList) {
        apiTestCaseService.runApiCase(integerList);
        return ResponseEntity.normalReturn("success", 200, null);
    }
}
