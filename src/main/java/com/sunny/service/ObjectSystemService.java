package com.sunny.service;

import com.sunny.dto.ObjectSystemDTO;
import com.sunny.entity.ObjectSystemEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjectSystemService {

    void addObjectSystem(ObjectSystemDTO objectSystemDTO);

    List<ObjectSystemEntity> queryObjectSystem(ObjectSystemDTO objectSystemDTO);
}