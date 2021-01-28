package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.repository.UserRepository;
import com.sunny.service.RegisterService;
import com.sunny.service.SendMailService;
import com.sunny.util.RegexUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SendMailService sendMailService;

    @Override
    public void register(UserDTO userDTO) {

        UserEntity userEntity;

        // 密码校验
        if (!RegexUtil.isPassword(userDTO.getPassword(), userDTO.getUsername())) {
            throw new ParamErrorException("密码不符合规则！");
        }

        // 手机注册
        if (RegexUtil.isMobileNum(userDTO.getUsername())) {
            userDTO.setTelephoneNum(userDTO.getUsername());
            userEntity = userRepository.findByTelephoneNum(userDTO.getUsername());
            if (userEntity != null) {
                throw new ParamErrorException("该手机号已注册，请去登录！");
            }
        }

        // 邮箱注册
        else if (RegexUtil.isMailAddress(userDTO.getUsername())) {
            userDTO.setEmail(userDTO.getUsername());
            userEntity = userRepository.findByEmail(userDTO.getUsername());
            if (userEntity != null) {
                throw new ParamErrorException("该邮箱已注册，请去登录！");
            }
            Integer mailCode = userDTO.getMailCode();
            if (mailCode == null) {
                throw new ParamErrorException("验证码不能为空！");
            }
            Integer code = (Integer) sendMailService.getCode(userDTO.getUsername())[0];
            if (!mailCode.equals(code)) {
                throw new ParamErrorException("验证码错误，请确认后再试！");
            }
            Date currentTime = (Date) sendMailService.getCode(userDTO.getUsername())[1];
            if (new Date().getTime() - currentTime.getTime() > 5 * 60 * 1000) {
                throw new ParamErrorException("验证码失效，请重新获取！");
            }
        }

        // 用户名注册
        else {
            UserEntity byUsername = userRepository.findByUsername(userDTO.getUsername());
            if (byUsername != null) {
                throw new ParamErrorException("该用户名已存在，请换用其他用户名注册！");
            }
        }

        // 保存用户入库
        UserEntity userEntity1 = userDTO.getEntity();
        userRepository.save(userEntity1);

    }
}
