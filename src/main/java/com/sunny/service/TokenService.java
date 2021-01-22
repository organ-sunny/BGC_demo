package com.sunny.service;

import com.sunny.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    String createToken(UserEntity userEntity);

    /**
     * 通过token获取用户
     */
    UserEntity getUserByToken(String token);
}
