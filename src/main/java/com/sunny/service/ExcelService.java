package com.sunny.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public interface ExcelService {

    void uploadExcel(MultipartFile file);

    void downloadExcel(HttpServletResponse httpServletResponse);
}
