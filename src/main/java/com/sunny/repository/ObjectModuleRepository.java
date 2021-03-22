package com.sunny.repository;

import com.sunny.entity.ObjectModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectModuleRepository extends JpaRepository<ObjectModuleEntity, Integer> {

    List<ObjectModuleEntity> findByModuleNameAndAndObjsystemId(String moduleName, Integer objsystemId);

    List<ObjectModuleEntity> findByModuleName(String moduleName);

    List<ObjectModuleEntity> findByObjsystemId(Integer objsystemId);

    @Query("from ObjectModuleEntity where id = ?1")
    ObjectModuleEntity myFindById(Integer id);

}
