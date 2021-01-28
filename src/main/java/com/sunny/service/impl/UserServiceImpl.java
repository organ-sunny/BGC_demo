package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.repository.UserRepository;
import com.sunny.service.SendMailService;
import com.sunny.service.UserService;
import com.sunny.util.RegexUtil;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SendMailService sendMailService;

    /**
     * 当前请求
     */
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public void editUser(UserDTO userDTO) {

        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        /**
         * 用户名校验
         */
        // 用户名不能为空
        if (StringUtil.isEmpty(userDTO.getUsername())) {
            throw new ParamErrorException("用户名不能为空");
        }
        if (!RegexUtil.isUsername(userDTO.getUsername())){
            throw new ParamErrorException("用户名不符合规则！规则如下：长度为6-16位且不能含有空格。");
        }
        // 用户名已存在
        if (!userDTO.getUsername().equals(user.getUsername()) && userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new ParamErrorException("用户名已存在");
        }

        /**
         * 电话号码校验
         */
        // 电话号码不能为空
        if (StringUtil.isEmpty(userDTO.getTelephoneNum())) {
            throw new ParamErrorException("手机号不能为空！");
        }
        // 电话号码符合正则
        if (!RegexUtil.isMobileNum(userDTO.getTelephoneNum())) {
            throw new ParamErrorException("请正确填写手机号！");
        }

        // 电话号码已存在
        if (!userDTO.getTelephoneNum().equals(user.getTelephoneNum()) && userRepository.findByTelephoneNum(userDTO.getTelephoneNum()) != null) {
            throw new ParamErrorException("手机号已注册！");
        }

        // 年龄校验
        if ((userDTO.getAge() > 130 || userDTO.getAge() < 0) && userDTO.getAge() != null) {
            throw new ParamErrorException("年龄输入错误！");
        }

        /**
         * 性别校验
         */
        // 性别不能为空
        if (userDTO.getSex() == null) {
            throw new ParamErrorException("性别不能为空！");
        }
        // 性别不可更改
        if (!userDTO.getSex().equals(user.getSex()) && user.getSex() != null) {
            throw new ParamErrorException("性别不可更改！");
        }

        // 编辑用户信息
        user.setUsername(userDTO.getUsername());
        user.setSex(userDTO.getSex());
        user.setAge(userDTO.getAge());
        user.setTelephoneNum(userDTO.getTelephoneNum());
        user.setAddress(userDTO.getAddress());

        // 保存用户信息
        userRepository.save(user);
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