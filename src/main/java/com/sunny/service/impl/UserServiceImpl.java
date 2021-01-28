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
        // 用户名已存在
        if (!userDTO.getUsername().equals(user.getUsername()) && userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new ParamErrorException("用户名已存在");
        }

        /**
         * 密码校验：
         */

        // 校验原密码
        if(!userDTO.getPassword().equals(user.getPassword())){
            throw new ParamErrorException("原密码错误！");
        }
        // 前端只要传了新密码就直接完成修改密码操作
        if (userDTO.getNewPassword() != null) {
            user.setPassword(userDTO.getNewPassword());
        }

        /**
         * 电话号码校验
         */
        // 电话号码不能为空
        if (StringUtil.isEmpty(userDTO.getTelephoneNum())) {
            throw new ParamErrorException("手机号不能为空！");
        }
        // 电话号码符合正则
        if (!RegexUtil.isMobileNum(userDTO.getTelephoneNum())){
            throw new ParamErrorException("请正确填写手机号！");
        }

        // 电话号码已存在
        if (!userDTO.getTelephoneNum().equals(user.getTelephoneNum()) && userRepository.findByTelephoneNum(userDTO.getTelephoneNum()) != null) {
            throw new ParamErrorException("手机号已注册！");
        }

        /**
         * 邮箱校验
         */
        // 如果修改过，才进行校验
        if (!userDTO.getEmail().equals(user.getEmail())) {
            // 邮箱不能为空
            if (StringUtil.isEmpty(userDTO.getEmail())) {
                throw new ParamErrorException("邮箱不能为空！");
            }
            // 邮箱符合正则
            if (!RegexUtil.isMailAddress(userDTO.getEmail())){
                throw new ParamErrorException("请正确填写邮箱！");
            }
            // 邮箱已存在
            if (!userDTO.getEmail().equals(user.getEmail()) && userRepository.findByEmail(userDTO.getEmail()) != null) {
                throw new ParamErrorException("邮箱已绑定！");
            }
            // 校验邮箱验证码
            Integer mailCode = userDTO.getMailCode();
            if (mailCode == null) {
                throw new ParamErrorException("验证码不能为空！");
            }
            if (sendMailService.getCode(userDTO.getEmail()) == null) { // 校验是否发送验证码
                throw new ParamErrorException("未获取验证码");
            }
            Integer code = (Integer) sendMailService.getCode(userDTO.getEmail())[0];
            if (!mailCode.equals(code)) {
                throw new ParamErrorException("验证码错误，请确认后再试！");
            }
            Date currentTime = (Date) sendMailService.getCode(userDTO.getEmail())[1];
            if(new Date().getTime()-currentTime.getTime() > 5*60*1000){
                throw new ParamErrorException("验证码失效，请重新获取！");
            }
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
        user.setEmail(userDTO.getEmail());


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