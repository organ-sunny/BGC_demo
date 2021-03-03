package com.sunny.repository;

import com.sunny.entity.ApiTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiTestCaseRepository extends JpaRepository<ApiTestCaseEntity, Integer>, JpaSpecificationExecutor<ApiTestCaseEntity> {

    List<ApiTestCaseEntity> findByApiCaseNum(String apiCaseNum);

    List<ApiTestCaseEntity> findByApiCaseNameAndObjectApiId(String apiCaseName, Integer objectApiId);

    List<ApiTestCaseEntity> findByApiCaseName(String apiCaseName);

    @Query("from ObjectApiEntity where id = ?1")
    List<ApiTestCaseEntity> myFindById(Integer id);
}
