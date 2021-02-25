package com.sunny.repository;

import com.sunny.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity,Integer> {

    // 配置excel案例上传路径相关的
    ConfigEntity findByProperty(String property);
}
