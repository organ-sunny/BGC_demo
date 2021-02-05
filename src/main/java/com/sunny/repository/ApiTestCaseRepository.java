package com.sunny.repository;


import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiTestCaseRepository extends JpaRepository<ApiTestCaseEntity, Integer> {
    List<ApiTestCaseEntity> findByApiCaseNum(String apiCaseNum);
}
