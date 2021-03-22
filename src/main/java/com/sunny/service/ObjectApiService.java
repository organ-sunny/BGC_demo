package com.sunny.service;

import com.sunny.dto.ObjectApiDTO;
import com.sunny.entity.ObjectApiEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjectApiService {

    ObjectApiEntity addObjectApi(ObjectApiDTO objectApiDTO);

    void deleteObjectApi(List<Integer> idList);

    void editObjectApi(Integer apiId, ObjectApiDTO objectApiDTO);

    List<ObjectApiEntity> queryObjectApi(ObjectApiDTO objectApiDTO);

    List<ObjectApiEntity> getByModuleId(Integer moduleId);

}
