package com.sunny.service.impl;

import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ObjectSystemService;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ObjectSystemSerivceImpl implements ObjectSystemService {

    @Resource
    private ObjectSystemRepository objectSystemRepository;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public ObjectSystemEntity addObjectSystem(ObjectSystemDTO objectSystemDTO) {

        List<ObjectSystemEntity> objSystem = objectSystemRepository.findByObjectSystem(objectSystemDTO.getObjectSystem());
        if (objSystem.size() != 0) {
            throw new BusinessException("该系统名称已存在！");
        }

        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        ObjectSystemEntity objectSystemEntity = objectSystemDTO.getEntity();

        objectSystemEntity.setCreatedTime(new Date());
        objectSystemEntity.setCreator(user.getUsername());
        objectSystemEntity.setUpdatedTime(new Date());
        objectSystemEntity.setUpdatedBy(user.getUsername());
        objectSystemEntity.setIsDeleted(0);

        return objectSystemRepository.save(objectSystemEntity);
    }

    @Override
    public void deleteObjectSystem(List<Integer> idList) {
        if (idList.size() == 0) {
            return;
        }

        for (Integer systemId : idList) {
            try {
                ObjectSystemEntity objectSystemEntity = objectSystemRepository.myFindById(systemId);
                objectSystemEntity.setIsDeleted(1);
                objectSystemRepository.save(objectSystemEntity);
            } catch (Exception e) {
                throw new BusinessException("id为" + systemId + "的系统不存在！");
            }
        }
    }

    @Override
    public void editObjectSystem(Integer systemId, ObjectSystemDTO objectSystemDTO) {

        UserEntity userEntity = (UserEntity) httpServletRequest.getAttribute("user");

        if (StringUtil.isEmpty(objectSystemDTO.getObjectSystem())) {
            throw new BusinessException("项目名不能为空！");
        }

        ObjectSystemEntity objectSystemEntity = objectSystemRepository.getOne(systemId);
        if (!objectSystemEntity.getObjectSystem().equals(objectSystemDTO.getObjectSystem()) &&
                objectSystemRepository.findByObjectSystem(objectSystemDTO.getObjectSystem()).size() != 0) {
            throw new BusinessException("该项目名已存在！");
        }

        objectSystemEntity.setObjectSystem(objectSystemDTO.getObjectSystem());
        objectSystemEntity.setUpdatedBy(userEntity.getUsername());
        objectSystemEntity.setUpdatedTime(new Date());

        objectSystemRepository.save(objectSystemEntity);
    }

    @Override
    public List<ObjectSystemEntity> queryObjectSystem(ObjectSystemDTO objectSystemDTO) {
        List<ObjectSystemEntity> objectSystemEntity;

        if (objectSystemDTO.getObjectSystem() != null) {
            objectSystemEntity = objectSystemRepository.findAllByIsDeletedAndObjectSystem(0, objectSystemDTO.getObjectSystem());
        } else {
//            objectSystemEntity = objectSystemRepository.findAllByIsDeleted(0);
            objectSystemEntity = objectSystemRepository.myFindAll();
        }
        return objectSystemEntity;
    }

    @Override
    public ObjectSystemEntity getById(Integer id) {
        return objectSystemRepository.getOne(id);
    }
}
