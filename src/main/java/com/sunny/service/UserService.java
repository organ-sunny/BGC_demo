package com.sunny.service;

import com.sunny.dto.UserDTO;
import com.sunny.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void editUser(UserDTO userDTO);

    void deleteUser();

    UserEntity showUser();
}
