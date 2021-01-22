package com.sunny.service;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserEntity editUser(UserDTO userDTO);

    void deleteUser(String token);
}
