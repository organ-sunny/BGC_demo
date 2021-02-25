package com.sunny.controller;

import com.sunny.dto.ObjectApiDTO;
import com.sunny.entity.ObjectApiEntity;
import com.sunny.entity.ResponseEntity;
import com.sunny.service.ObjectApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/objectApi")
public class ObjectApiController {

    @Resource
    private ObjectApiService objectApiService;

    // 新增接口
    @PostMapping
    public ResponseEntity addObjectApi(@RequestBody(required = false) ObjectApiDTO objectApiDTO) {

        objectApiDTO.check();
        objectApiService.addObjectApi(objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 删除接口1
    // 请求地址：http://localhost:1001/api/objectApi
    @DeleteMapping
    public ResponseEntity deleteObjectApi(@RequestBody ObjectApiDTO objectApiDTO) {
        objectApiService.deleteObjectApi(objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 删除接口2
    // 请求地址：http://localhost:1001/api/objectApi/34
    @DeleteMapping("{id}")
    public ResponseEntity deleteObjectApi(@PathVariable("id") Integer id) {
        objectApiService.deleteObjectApi2(id);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 修改接口
    @PutMapping
    public ResponseEntity editObjectApi(@RequestBody ObjectApiDTO objectApiDTO) {
        objectApiDTO.check();
        objectApiService.editObjectApi(objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 查询接口
    @GetMapping
    public ResponseEntity queryObjectApi(@RequestBody(required = false) ObjectApiDTO objectApiDTO) {
        List<ObjectApiEntity> objectApiEntities = objectApiService.queryObjectApi(objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, objectApiEntities);
    }
}
