package com.sunny.service.impl;

import com.sunny.dto.ObjectModuleDTO;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ObjectModuleRepository;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ObjectModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ObjectModuleServiceImpl implements ObjectModuleService {
    @Resource
    private ObjectModuleRepository objectModuleRepository;

    @Resource
    private ObjectSystemRepository objectSystemRepository;

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 入参：moduleName、systemId
     * 先判断系统是否存在：
     * 系统存在：
     * 再判断模块是否存在：
     * 模块存在：返回提示模块已存在
     * 模块不存在：直接新增保存模块
     * 系统不存在：新增系统，拿到系统id，塞给模块，然后新增保存模块
     */
    @Override
    public ObjectModuleEntity addModule(ObjectModuleDTO objectModuleDTO) {

        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        Integer objsystemId = objectModuleDTO.getObjsystemId();
        // 如果系统存在
        if (objectSystemRepository.findById(objsystemId) != null) {
            String moduleName = objectModuleDTO.getModuleName();
            List<ObjectModuleEntity> objectModuleEntityList = objectModuleRepository.findByModuleNameAndAndObjsystemId(moduleName, objsystemId);
            // 如果模块存在
            if (objectModuleEntityList.size() != 0) {
                throw new BusinessException("该模块已存在！");
            }

            ObjectModuleEntity objectModuleEntity = objectModuleDTO.getEntity();

            objectModuleEntity.setCreator(user.getUsername());
            objectModuleEntity.setCreatedTime(new Date());
            objectModuleEntity.setUpdatedBy(user.getUsername());
            objectModuleEntity.setUpdatedTime(new Date());

            return objectModuleRepository.save(objectModuleEntity);

        } else {
            throw new BusinessException("系统不存在！");
        }
    }

    @Override
    public void deleteObjectModule(List<Integer> idList) {
        if (idList.size() == 0) {
            return;
        }
        for (Integer moduleId : idList) {
            try {
                objectModuleRepository.deleteById(moduleId);
            } catch (Exception e) {
                throw new BusinessException("id为" + moduleId + "的模块不存在！");
            }
        }
    }

    @Override
    public void editObjectModule(Integer moduleId, ObjectModuleDTO objectModuleDTO) {

        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        if (objectModuleDTO.getModuleName() == null) {
            throw new BusinessException("模块名不能为空！");
        }

        ObjectModuleEntity objectModuleEntity = objectModuleRepository.myFindById(moduleId);
        if (!objectModuleEntity.getModuleName().equals(objectModuleDTO.getModuleName()) &&
                objectModuleRepository.findByModuleName(objectModuleDTO.getModuleName()).size() != 0){
            throw new BusinessException("该模块名已存在！");
        }

        objectModuleEntity.setModuleName(objectModuleDTO.getModuleName());
        objectModuleEntity.setUpdatedTime(new Date());
        objectModuleEntity.setUpdatedBy(user.getUsername());

        objectModuleRepository.save(objectModuleEntity);
    }

    /**
     * 入参：objsystemId、moduleName
     * 如果系统id、模块名同时为空，则查询返回模块表中所有模块表记录
     * 如果系统id、模块名都不空，返回模块表中模块名、系统id同时满足条件的记录
     * 如果系统id空，模块名不空，返回模块表中模块名为条件的所有记录
     * 如果系统id不空，模块名空，返回模块表中系统id为条件的所有记录
     */
    @Override
    public List<ObjectModuleEntity> queryObjectModule(ObjectModuleDTO objectModuleDTO) {

        List<ObjectModuleEntity> objectModuleEntityList = new ArrayList<>();
        Integer objsystemId = objectModuleDTO.getObjsystemId();
        String moduleName = objectModuleDTO.getModuleName();

        if (objsystemId != null && moduleName == null) {
            objectModuleEntityList = objectModuleRepository.findByObjsystemId(objsystemId);
        }

        if (objsystemId == null && moduleName != null) {
            objectModuleEntityList = objectModuleRepository.findByModuleName(moduleName);
        }

        if (objsystemId != null && moduleName != null) {
            objectModuleEntityList = objectModuleRepository.findByModuleNameAndAndObjsystemId(objectModuleDTO.getModuleName(), objectModuleDTO.getObjsystemId());

        }

        if (objsystemId == null && moduleName == null) {
            objectModuleEntityList = objectModuleRepository.findAll();
        }

        return objectModuleEntityList;
    }

    @Override
    public List<ObjectModuleEntity> getBySystemId(Integer systemId) {
        return objectModuleRepository.findByObjsystemId(systemId);
    }
}
