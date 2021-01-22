package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.repository.UserRepository;
import com.sunny.service.TokenService;
import com.sunny.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenService tokenService;

    @Override
    public UserEntity editUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(String token) {
        // 根据token定位当前用户
        UserEntity user = tokenService.getUserByToken(token);
        if (user == null ){
            throw new RuntimeException("用户不存在！");
        }

        // 用户执行注销操作
        userRepository.deleteByUsername(user.getUsername());

    }
}
