package com.sunny.repository;

import com.sunny.entity.ObjectModuleEntity;
import com.sunny.entity.ObjectSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectSystemRepository extends JpaRepository<ObjectSystemEntity, Integer> {

    List<ObjectSystemEntity> findByObjectSystem(String objectSystem);

    @Query("from ObjectSystemEntity where id = ?1")
    ObjectSystemEntity myFindById(Integer id);

    @Query("from ObjectSystemEntity where isDeleted = 0")
    List<ObjectSystemEntity> myFindAll();

    List<ObjectSystemEntity> findAllByIsDeleted(Integer isDeleted);

    List<ObjectSystemEntity> findAllByIsDeletedAndObjectSystem(Integer isDeleted, String systemName);

}
