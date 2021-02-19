package com.sunny.service;

import com.sunny.constant.ConfigConstant;
import com.sunny.entity.ConfigEntity;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

//    void addUploadExcelPath(ConfigEntity configEntity);

    String getUploadExcelPath(ConfigConstant configConstant);
}
