package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.repository.UserRepository;
import com.sunny.service.LoginService;
import com.sunny.service.TokenService;
import com.sunny.util.RegexUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenService tokenService;

    @Override
    public UserEntity login(UserDTO userDTO) {

        UserEntity userEntity;
        // 手机号登录判断
        if (RegexUtil.isMobileNum(userDTO.getUsername())) {
            userEntity = userRepository.findByTelephoneNum(userDTO.getUsername());
            if (userEntity == null) {
                throw new ParamErrorException("该手机号未进行注册，请先注册！");
            }
            if (!userEntity.getPassword().equals(userDTO.getPassword())) {
                throw new ParamErrorException("手机号或密码错误，请确认后再试！");
            }
        }
        // 邮箱登录判断
        else if (RegexUtil.isMailAddress((userDTO.getUsername()))) {
            userEntity = userRepository.findByEmail(userDTO.getUsername());
            if (userEntity == null) {
                throw new ParamErrorException("该邮箱未进行注册，请先注册");
            }
            if (!userEntity.getPassword().equals(userDTO.getPassword())) {
                throw new ParamErrorException("邮箱或密码错误，请确认后再试！");
            }
        }
        // 用户名登录判断
        else {
            userEntity = userRepository.findByUsername(userDTO.getUsername());
            // 判断用户是否存在
            if (userEntity == null) {
                throw new ParamErrorException("用户不存在，请先注册！");
            }
            // 根据登录用户名去查表里记录，查到后判断表里密码与登录时的密码是否一致
            if (!userEntity.getPassword().equals(userDTO.getPassword())) {
                throw new ParamErrorException("用户名或密码错误，请确认后再试！");
            }
        }

        // 登录成功，即时创建token并入库
        String token = tokenService.createToken(userEntity);
        userEntity.setToken(token);
        userRepository.save(userEntity);

        return userEntity;
    }
}
