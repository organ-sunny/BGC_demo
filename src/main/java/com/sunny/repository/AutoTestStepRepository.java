package com.sunny.repository;

import com.sunny.entity.AutoTestStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoTestStepRepository extends JpaRepository<AutoTestStepEntity, Integer> {}