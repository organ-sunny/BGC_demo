package com.sunny.service.impl;

import com.sunny.constant.ConfigConstant;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ConfigRepository;
import com.sunny.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigRepository configRepository;

//    @Override
//    public void addUploadExcelPath(ConfigEntity configEntity) {
//        String key = "UPLOAD_EXCEL_PATH";
//        String value = "D:/WorkSpace/ideaProjects/BGC_demo/upload/temp";
//        configEntity.setProperty(key);
//        configEntity.setValue(value);
//        configRepository.save(configEntity);
//    }

    // 查config表获取excel上传路径
    @Override
    public String getUploadExcelPath(ConfigConstant configConstant) {
        try {
            return configRepository.findByProperty(configConstant.getValue()).getValue();
        }catch (Exception e){
            throw new BusinessException("无该路径的属性！");
        }
    }
}
