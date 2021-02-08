package com.sunny.repository;


import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiTestCaseRepository extends JpaRepository<ApiTestCaseEntity, Integer> {
    List<ApiTestCaseEntity> findByApiCaseNum(String apiCaseNum);

    List<ApiTestCaseEntity> findByObjectSystemName(String objectSystemName);

    List<ApiTestCaseEntity> findByObjectModuleName(String objectModuleName);

    List<ApiTestCaseEntity> findByObjectModuleNameAndObjectSystemName(String objectModuleName, String objectSystemName);
}
