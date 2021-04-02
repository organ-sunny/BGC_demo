package com.sunny.service;

import com.sunny.dto.ApiTestCaseDTO;
import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface ApiTestCaseService {

    void addApiCase(ApiTestCaseDTO apiTestCaseDTO);

    void deleteApiCase(List<Integer> idList);

    void editApiCase(Integer apiCaseId, ApiTestCaseDTO apiTestCaseDTO);

    List<ApiTestCaseEntity> queryApiCase(Integer apiId);

    List<ApiTestCaseEntity> queryAllApiCases(Map<String, Object> map);

    Map<String, Object> runApiCase(Integer apiCaseId, boolean isDebug);

    void runApiCase(List<Integer> apiCaseIdList);

}
