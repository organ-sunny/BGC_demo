package com.sunny.service;

import com.sunny.dto.ObjectApiDTO;
import com.sunny.entity.ObjectApiEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjectApiService {

    ObjectApiEntity addObjectApi(ObjectApiDTO objectApiDTO);

    void deleteObjectApi(ObjectApiDTO objectApiDTO);

    void deleteObjectApi2(Integer id);

    void editObjectApi(ObjectApiDTO objectApiDTO);

    List<ObjectApiEntity> queryObjectApi(ObjectApiDTO objectApiDTO);
}
