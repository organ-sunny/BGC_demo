package com.sunny.service;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    UserEntity login(UserDTO userDTO);


}
