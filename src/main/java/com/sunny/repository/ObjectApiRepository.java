package com.sunny.repository;

import com.sunny.entity.ObjectApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectApiRepository extends JpaRepository<ObjectApiEntity, Integer>, JpaSpecificationExecutor<ObjectApiEntity> {

    List<ObjectApiEntity> findByModuleIdAndApiName(Integer moduleId, String apiName);

    List<ObjectApiEntity> findByModuleId(Integer moduleId);

    List<ObjectApiEntity> findByApiName(String apiName);

    @Query("from ObjectApiEntity where id = ?1")
    ObjectApiEntity myFindById(Integer id);

}
