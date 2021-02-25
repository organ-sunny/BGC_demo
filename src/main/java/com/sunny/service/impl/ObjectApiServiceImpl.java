package com.sunny.service.impl;

import com.sunny.dto.ObjectApiDTO;
import com.sunny.entity.ObjectApiEntity;
import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.UserEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.ObjectApiRepository;
import com.sunny.repository.ObjectModuleRepositiry;
import com.sunny.service.ObjectApiService;
import com.sunny.util.RegexUtil;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ObjectApiServiceImpl implements ObjectApiService {

    @Resource
    private ObjectApiRepository objectApiRepository;

    @Resource
    private ObjectModuleRepositiry objectModuleRepositiry;

    @Resource
    private HttpServletRequest httpServletRequest;

    /**
     * 入参：moduleId、apiName、apiAddress、apiMethod
     */
    @Override
    public void addObjectApi(ObjectApiDTO objectApiDTO) {

        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");
        Integer moduleId = objectApiDTO.getModuleId();

        ObjectModuleEntity objectModuleEntity = objectModuleRepositiry.myFindById(moduleId);
        if (objectModuleEntity == null){
            throw new BusinessException("该接口所属模块不存在，请先录入模块！");
        }

        List<ObjectApiEntity> byModuleIdAndApiName = objectApiRepository.findByModuleIdAndApiName(objectApiDTO.getModuleId(), objectApiDTO.getApiName());
        if (byModuleIdAndApiName.size() != 0) {
            throw new BusinessException("该接口已录入，请确认后再试！");
        }

        ObjectApiEntity objectApiEntity = objectApiDTO.getEntity();
        objectApiEntity.setCreator(user.getUsername());
        objectApiEntity.setCreatedTime(new Date());
        objectApiEntity.setUpdatedBy(user.getUsername());
        objectApiEntity.setUpdatedTime(new Date());

        objectApiRepository.save(objectApiEntity);
    }

    @Override
    public void deleteObjectApi(ObjectApiDTO objectApiDTO) {
        Integer objectApiId = objectApiDTO.getId();
        ObjectApiEntity objectApiEntity = objectApiRepository.myFindById(objectApiId);
        if (objectApiEntity == null){
            throw new BusinessException("不存在这条记录！");
        }
        objectApiRepository.deleteById(objectApiId);
    }

    @Override
    public void deleteObjectApi2(Integer id) {
        ObjectApiEntity objectApiEntity = objectApiRepository.myFindById(id);
        if(objectApiEntity == null){
            throw new BusinessException("该条记录不存在，请确认后再试！");
        }
        objectApiRepository.deleteById(id);
    }

    @Override
    public void editObjectApi(ObjectApiDTO objectApiDTO) {
        // 定位用户，用于“更新人”字段
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        ObjectApiEntity objectApiEntity = objectApiRepository.myFindById(objectApiDTO.getId());
        if(objectApiEntity == null){
            throw new BusinessException("该条记录不存在，请确认后再试！");
        }
        if(!RegexUtil.isRequestMethodRight(objectApiDTO.getApiMethod())){
            throw new BusinessException("请求方法填写错误：请填写POST、GET、PUT、DELETE中的一个，并注意大小写！");
        }
        objectApiEntity.setApiName(objectApiDTO.getApiName());
        objectApiEntity.setApiAddress(objectApiDTO.getApiAddress());
        objectApiEntity.setApiMethod(objectApiDTO.getApiMethod());
        objectApiEntity.setUpdatedBy(user.getUsername());
        objectApiEntity.setUpdatedTime(new Date());
        objectApiRepository.save(objectApiEntity);
    }

    @Override
    public List<ObjectApiEntity> queryObjectApi(ObjectApiDTO objectApiDTO) {

        List<ObjectApiEntity> objectApiEntityList = new ArrayList<>();
        Integer moduleId = objectApiDTO.getModuleId();
        String apiName = objectApiDTO.getApiName();
        if (moduleId != null && StringUtil.isEmpty(apiName)){
            objectApiEntityList = objectApiRepository.findByModuleId(moduleId);
        }

        if (moduleId == null && !StringUtil.isEmpty(apiName)){
            objectApiEntityList = objectApiRepository.findByApiName(apiName);
        }

        if (moduleId == null && StringUtil.isEmpty(apiName)){
            objectApiEntityList = objectApiRepository.findAll();
        }

        if (moduleId != null && !StringUtil.isEmpty(apiName)){
            objectApiEntityList = objectApiRepository.findByModuleIdAndApiName(moduleId,apiName);
        }
        if (objectApiEntityList.size() != 0){
            return objectApiEntityList;
        }else {
            throw new BusinessException("未查询到相关记录！");
        }

    }
}
