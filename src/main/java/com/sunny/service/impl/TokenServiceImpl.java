package com.sunny.service.impl;

import com.sunny.entity.UserEntity;
import com.sunny.exception.TokenException;
import com.sunny.repository.UserRepository;
import com.sunny.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private UserRepository userRepository;

    @Override
    public String createToken(UserEntity userEntity) {
        String token = userEntity.getUsername() + userEntity.getPassword() + new Date().getTime();
        userEntity.setUpdatedTime(new Date());
        userRepository.save(userEntity);
        return DigestUtils.md5DigestAsHex(token.getBytes());
    }

    @Override
    public UserEntity getUserByToken(String token) {

        if (token == null) {
            throw new TokenException("未检测到token或token已失效！");
        }

        UserEntity userEntity = userRepository.findByToken(token);
        if (userEntity == null) {
            throw new TokenException("用户不存在，请确认后再试！");
        }

        return userEntity;
    }

}
