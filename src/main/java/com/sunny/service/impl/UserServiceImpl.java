package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.repository.UserRepository;
import com.sunny.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 当前请求
     */
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public UserEntity editUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser() {
        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        // 用户执行注销操作
        userRepository.deleteByUsername(user.getUsername());
    }

    @Override
    public UserEntity showUser() {
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");
        return user;
    }
}
