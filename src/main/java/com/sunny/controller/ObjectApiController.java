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
    // 删除接口
    @DeleteMapping
    public ResponseEntity deleteObjectApi(@RequestBody(required = false) List<Integer> idList) {
        objectApiService.deleteObjectApi(idList);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 修改接口
    @PutMapping("{id}")
    public ResponseEntity editObjectApi(@PathVariable Integer id, @RequestBody(required = false) ObjectApiDTO objectApiDTO) {
        objectApiService.editObjectApi(id, objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, null);
    }

    // 查询接口
    @GetMapping
    public ResponseEntity queryObjectApi(ObjectApiDTO objectApiDTO) {
        List<ObjectApiEntity> objectApiEntityList = objectApiService.queryObjectApi(objectApiDTO);
        return ResponseEntity.normalReturn("success", 200, objectApiEntityList);
    }
}
