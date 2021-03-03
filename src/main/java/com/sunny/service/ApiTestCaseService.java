package com.sunny.service;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ApiTestCaseService {

    void addApiCase(ApiTestCaseDTO apiTestCaseDTO);

    List<ApiTestCaseEntity> queryApiCase(Map<String, Object> map);

    void runApiCase(Integer apiCaseId);

    void runApiCase(List<Integer> apiCaseIds);

}
