package com.sunny.service;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApiTestCaseService {

    void addApiCase(ApiTestCaseDTO apiTestCaseDTO);

    List<ApiTestCaseEntity> queryApiCase(ApiTestCaseDTO apiTestCaseDTO);

//    void editApiCase(ApiTestCaseDTO apiTestCaseDTO);

    void runApiCase(Integer apiCaseId);
}
