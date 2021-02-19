package com.sunny.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ExcelService {

    void uploadExcel(MultipartFile file);
}
