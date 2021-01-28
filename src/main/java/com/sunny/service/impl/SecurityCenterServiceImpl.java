package com.sunny.service.impl;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import com.sunny.exception.ParamErrorException;
import com.sunny.repository.UserRepository;
import com.sunny.service.SecurityCenterService;
import com.sunny.service.SendMailService;
import com.sunny.util.RegexUtil;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class SecurityCenterServiceImpl implements SecurityCenterService {
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private SendMailService sendMailService;

    @Resource
    private UserRepository userRepository;

    @Override
    public void updateEmail(UserDTO userDTO) {
        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

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
            if (!RegexUtil.isMailAddress(userDTO.getEmail())) {
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
            if (new Date().getTime() - currentTime.getTime() > 5 * 60 * 1000) {
                throw new ParamErrorException("验证码失效，请重新获取！");
            }
        }

        user.setEmail(userDTO.getEmail());

        userRepository.save(user);

    }

    @Override
    public void updatePassword(UserDTO userDTO) {
        // 定位当前用户
        UserEntity user = (UserEntity) httpServletRequest.getAttribute("user");

        /**
         * 密码校验：
         */
        // 校验原密码
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new ParamErrorException("原密码错误！");
        }
        if (!RegexUtil.isPassword(userDTO.getNewPassword(), user.getUsername())) {
            throw new ParamErrorException("新密码不符合规则！规则如下：数字、字母、特殊字符三选二且不能包含用户名，长度为6-16位！");
        }
        user.setPassword(userDTO.getNewPassword());
        user.setToken(null);

        userRepository.save(user);
    }
}
