package com.sunny.repository;

import com.sunny.entity.ObjectModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectModuleRepositiry extends JpaRepository<ObjectModuleEntity, Integer> {

    ObjectModuleEntity findByModuleNameAndAndObjsystemId(String moduleName, Integer objsystemId);

    List<ObjectModuleEntity> findByModuleName(String moduleName);
}
