package com.sunny.repository;

import com.sunny.entity.ObjectModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectModuleRepositiry extends JpaRepository<ObjectModuleEntity, Integer> {
}
