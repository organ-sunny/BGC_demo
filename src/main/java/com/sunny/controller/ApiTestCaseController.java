package com.sunny.controller;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ApiTestCaseService;
import org.springframework.web.bind.annotation.*;

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
        apiTestCaseDTO.check();
        apiTestCaseService.addApiCase(apiTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @DeleteMapping
    public ResponseEntity deleteApiCase(@RequestBody(required = false) List<Integer> idList){
        apiTestCaseService.deleteApiCase(idList);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @PutMapping("{id}")
    public ResponseEntity editApiCase(@PathVariable Integer apiId,@RequestBody(required = false) ApiTestCaseDTO apiTestCaseDTO){
        apiTestCaseDTO.check();
        apiTestCaseService.editApiCase(apiId, apiTestCaseDTO);
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
