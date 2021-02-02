package com.sunny.service.impl;

import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.ObjectSystemEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ObjectSystemRepository;
import com.sunny.service.ObjectSystemService;
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
    public void addObjectSystem(ObjectSystemDTO objectSystemDTO) {

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

        objectSystemRepository.save(objectSystemEntity);
    }

    @Override
    public List<ObjectSystemEntity> queryObjectSystem(ObjectSystemDTO objectSystemDTO) {

        List<ObjectSystemEntity> objectSystemEntity;

        if (objectSystemDTO.getObjectSystem() != null) {
            objectSystemEntity = objectSystemRepository.findByObjectSystem(objectSystemDTO.getObjectSystem());
        } else {
            objectSystemEntity = objectSystemRepository.findAll();
        }

        return objectSystemEntity;
    }
}
