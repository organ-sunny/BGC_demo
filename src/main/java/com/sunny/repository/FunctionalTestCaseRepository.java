package com.sunny.repository;

import com.sunny.entity.FunctionalTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionalTestCaseRepository extends JpaRepository<FunctionalTestCaseEntity, Integer>, JpaSpecificationExecutor<FunctionalTestCaseEntity> {

    List<FunctionalTestCaseEntity> findByModuleId(Integer moduleId);

    List<FunctionalTestCaseEntity> findByCaseNum(String caseNum);

    List<FunctionalTestCaseEntity> findByCaseName(String caseName);

    List<FunctionalTestCaseEntity> findByModuleIdAndCaseNum(Integer moduleId, String caseNum);
}
