package com.sunny.service;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

@Service
public interface RegisterService {

    void register(UserDTO userDTO);
}
