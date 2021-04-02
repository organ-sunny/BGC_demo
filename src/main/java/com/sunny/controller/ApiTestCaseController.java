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

    @PostMapping
    public ResponseEntity addApiCase(@RequestBody(required = false) ApiTestCaseDTO apiTestCaseDTO) {
        apiTestCaseDTO.check();
        apiTestCaseService.addApiCase(apiTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @DeleteMapping
    public ResponseEntity deleteApiCase(@RequestBody(required = false) List<Integer> idList) {
        apiTestCaseService.deleteApiCase(idList);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @PutMapping("{apiCaseId}")
    public ResponseEntity editApiCase(@PathVariable Integer apiCaseId, @RequestBody(required = false) ApiTestCaseDTO apiTestCaseDTO) {
        apiTestCaseDTO.check();
        apiTestCaseService.editApiCase(apiCaseId, apiTestCaseDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    @GetMapping("{apiId}")
    public ResponseEntity queryApiCase(@PathVariable Integer apiId) {
        // 执行查询
        List<ApiTestCaseEntity> apiTestCaseEntityList = apiTestCaseService.queryApiCase(apiId);
        return ResponseEntity.normalReturn("success", 200, apiTestCaseEntityList);
    }

    @GetMapping
    public ResponseEntity queryAllApiCases(@RequestBody(required = false) Map<String, Object> map) {
        // 执行查询
        List<ApiTestCaseEntity> apiTestCaseEntityList = apiTestCaseService.queryAllApiCases(map);
        return ResponseEntity.normalReturn("success", 200, apiTestCaseEntityList);
    }

    @PostMapping("runApiCase")
    public ResponseEntity runApiCase(@RequestBody(required = false) List<Integer> apiCaseIdList) {
        apiTestCaseService.runApiCase(apiCaseIdList);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    /**
     * 调试
     * */
    @PostMapping("debug")
    public ResponseEntity debug(@RequestBody(required = false) Map<String, Object> RequestBody) {
        return ResponseEntity.normalReturn("success", 200, apiTestCaseService.runApiCase((Integer) RequestBody.get("id"), true));
    }

}