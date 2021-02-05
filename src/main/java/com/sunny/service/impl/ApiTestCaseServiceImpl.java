package com.sunny.service.impl;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ApiTestCaseRepository;
import com.sunny.repository.ObjectModuleRepositiry;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ApiTestCaseService;
import com.sunny.service.ObjectSystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ApiTestCaseServiceImpl implements ApiTestCaseService {
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ApiTestCaseRepository apiTestCaseRepository;

    @Resource
    private ObjectSystemRepository objectSystemRepository;

    @Resource
    private ObjectModuleRepositiry objectModuleRepositiry;

    @Resource
    private ObjectSystemService objectSystemService;

    @Override
    public void addApiCase(ApiTestCaseDTO apiTestCaseDTO) {

        ApiTestCaseEntity apiCaseEntity = apiTestCaseDTO.getEntity();

        Date nowTime = new Date();
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        List<ObjectSystemEntity> byObjectSystem = objectSystemRepository.findByObjectSystem(apiTestCaseDTO.getObjectSystemName());
        if (byObjectSystem.size() != 0) {

            String moduleName = apiTestCaseDTO.getObjectModuleName();
            Integer systemId = byObjectSystem.get(0).getId();
            ObjectModuleEntity byModuleNameAndsystemId = objectModuleRepositiry.findByModuleNameAndAndObjsystemId(moduleName, systemId);

            if (byModuleNameAndsystemId != null) {
                apiCaseEntity.setObjectModuleId(byModuleNameAndsystemId.getId());
            } else {
                ObjectModuleEntity objectModuleEntity = new ObjectModuleEntity();
                objectModuleEntity.setModuleName(apiTestCaseDTO.getObjectModuleName());
                objectModuleEntity.setObjsystemId(systemId);
                ObjectModuleEntity save1 = objectModuleRepositiry.save(objectModuleEntity);
                apiCaseEntity.setObjectModuleId(save1.getId());
            }
        } else {
            ObjectSystemEntity objectSystemEntity = new ObjectSystemEntity();
            objectSystemEntity.setObjectSystem(apiTestCaseDTO.getObjectSystemName());
            objectSystemEntity.setUpdatedTime(nowTime);
            objectSystemEntity.setCreatedTime(nowTime);
            objectSystemEntity.setUpdatedBy(user.getUsername());
            objectSystemEntity.setCreator(user.getUsername());
            ObjectSystemEntity save2 = objectSystemRepository.save(objectSystemEntity);

            ObjectModuleEntity objectModuleEntity = new ObjectModuleEntity();
            objectModuleEntity.setModuleName(apiTestCaseDTO.getObjectModuleName());
            objectModuleEntity.setObjsystemId(save2.getId());
            ObjectModuleEntity save3 = objectModuleRepositiry.save(objectModuleEntity);
            apiCaseEntity.setObjectModuleId(save3.getId());

        }
        List<ApiTestCaseEntity> apiTestCaseEntity = apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        if (apiTestCaseEntity.size() != 0) {
            throw new BusinessException("该用例编号已存在！");
        }
        apiCaseEntity.setCreatedTime(nowTime);
        apiCaseEntity.setUpdatedTime(nowTime);

        apiCaseEntity.setCreator(user.getUsername());
        apiCaseEntity.setUpdatedBy(user.getUsername());
        apiTestCaseRepository.save(apiCaseEntity);
    }

    @Override
    public List<ApiTestCaseEntity> queryApiCase(ApiTestCaseDTO apiTestCaseDTO) {
        List<ApiTestCaseEntity> apiTestCaseEntityList;

        if (apiTestCaseDTO.getApiCaseNum() != null) {
            apiTestCaseEntityList = apiTestCaseRepository.findByApiCaseNum(apiTestCaseDTO.getApiCaseNum());
        } else {
            apiTestCaseEntityList = apiTestCaseRepository.findAll();
        }
        return apiTestCaseEntityList;
    }
}

