package com.sunny.service;

import com.sunny.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface SecurityCenterService {

    void updateEmail(UserDTO userDTO);

    void updatePassword(UserDTO userDTO);
}
