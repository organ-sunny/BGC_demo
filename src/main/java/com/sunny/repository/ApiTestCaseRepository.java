package com.sunny.repository;


import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiTestCaseRepository extends JpaRepository<ApiTestCaseEntity, Integer> {
}
