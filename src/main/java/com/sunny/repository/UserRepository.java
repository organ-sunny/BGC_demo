package com.sunny.repository;

import com.sunny.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 根据手机号查找用户
    UserEntity findByTelephoneNum(String telephoneNum);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}
