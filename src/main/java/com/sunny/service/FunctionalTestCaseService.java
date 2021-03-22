package com.sunny.service;

import com.sunny.dto.FunctionalTestCaseDTO;
import com.sunny.entity.FunctionalTestCaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FunctionalTestCaseService {

    void addFunctionalCase(FunctionalTestCaseDTO functionalTestCaseDTO);

    void deleteFunctionalCase(Integer id);

    List<FunctionalTestCaseEntity> queryFunctionalTestCase(Map<String, Object> map);
}
