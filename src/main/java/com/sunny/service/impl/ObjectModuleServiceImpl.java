package com.sunny.service.impl;

import com.sunny.dto.ObjectModuleDTO;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ObjectModuleRepositiry;
import com.sunny.service.ObjectModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ObjectModuleServiceImpl implements ObjectModuleService {
    @Resource
    private ObjectModuleRepositiry objectModuleRepositiry;

    @Override
    public void addModule(ObjectModuleDTO objectModuleDTO) {

        String moduleName = objectModuleDTO.getModuleName();
        Integer objsystemId = objectModuleDTO.getObjsystemId();
        ObjectModuleEntity byModuleNameAndAndObjsystemId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, objsystemId);
        if (byModuleNameAndAndObjsystemId != null) {
            throw new BusinessException("该模块已存在！");
        }
        ObjectModuleEntity objectModuleEntity = objectModuleDTO.getEntity();
        objectModuleRepositiry.save(objectModuleEntity);
    }

    @Override
    public List<ObjectModuleEntity> queryModule(ObjectModuleDTO objectModuleDTO) {

        List<ObjectModuleEntity> objectModuleEntityList;
        if (objectModuleDTO.getModuleName() != null) {
            objectModuleEntityList = objectModuleRepositiry.findByModuleName(objectModuleDTO.getModuleName());
        } else {
            objectModuleEntityList = objectModuleRepositiry.findAll();
        }

        return objectModuleEntityList;


    }
}
