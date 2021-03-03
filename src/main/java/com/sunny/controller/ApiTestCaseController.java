package com.sunny.controller;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ApiTestCaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @PostMapping("query")
    public ResponseEntity queryApiCase(@RequestBody(required = false) Map<String, Object> map) {
        // 执行查询
        List<ApiTestCaseEntity> apiTestCaseEntityList = apiTestCaseService.queryApiCase(map);
        return ResponseEntity.normalReturn("success", 200, apiTestCaseEntityList);
    }

    @PostMapping("runApiCase")
    public ResponseEntity runApiCase(@RequestBody(required = false) List<Integer> integerList) {
        apiTestCaseService.runApiCase(integerList);
        return ResponseEntity.normalReturn("success", 200, null);
    }
}
