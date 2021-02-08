package com.sunny.repository;

import com.sunny.entity.ObjectSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectSystemRepository extends JpaRepository<ObjectSystemEntity, Integer> {

    List<ObjectSystemEntity> findByObjectSystem(String objectSystem);
}
