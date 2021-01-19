package com.sunny.service.impl;

import com.sunny.entity.UserEntity;
import com.sunny.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String createToken(UserEntity userEntity) {
        String token = userEntity.getUsername() + userEntity.getPassword();
        return DigestUtils.md5DigestAsHex(token.getBytes());
    }

}
