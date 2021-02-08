package com.sunny.service;

import com.sunny.dto.ObjectModuleDTO;
import com.sunny.entity.ObjectModuleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjectModuleService {

    ObjectModuleEntity addModule(ObjectModuleDTO objectModuleDTO);

    List<ObjectModuleEntity> queryModule(ObjectModuleDTO objectModuleDTO);
}
