package com.sunny.repository;

import com.sunny.entity.AutoTestModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutoTestModuleRepository extends JpaRepository<AutoTestModuleEntity, Integer> {

    List<AutoTestModuleEntity> findAllByName(String name);

}