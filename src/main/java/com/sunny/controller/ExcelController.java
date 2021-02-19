package com.sunny.controller;

import com.sunny.entity.ResponseEntity;
import com.sunny.service.ExcelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;

    @PostMapping("upload")
    public ResponseEntity uploadExcel(@RequestParam(required = false, name = "file") MultipartFile file) {
        excelService.uploadExcel(file);
        return ResponseEntity.normalReturn("success", 200, null);
    }

}
