package com.sunny.repository;


import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiTestCaseRepository extends JpaRepository<ApiTestCaseEntity, Integer> {
    List<ApiTestCaseEntity> findByApiCaseNum(String apiCaseNum);

    List<ApiTestCaseEntity> findByObjectSystemName(String objectSystemName);

    List<ApiTestCaseEntity> findByObjectModuleName(String objectModuleName);

    List<ApiTestCaseEntity> findByObjectModuleNameAndObjectSystemName(String objectModuleName, String objectSystemName);
}
