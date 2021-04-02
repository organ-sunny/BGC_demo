package com.sunny.service;

import com.sunny.dto.ObjectModuleDTO;
import com.sunny.entity.ObjectModuleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjectModuleService {

    ObjectModuleEntity addModule(ObjectModuleDTO objectModuleDTO);

    void deleteObjectModule(List<Integer> idList);

    void editObjectModule(Integer moduleId, ObjectModuleDTO objectModuleDTO);

    List<ObjectModuleEntity> queryObjectModule(ObjectModuleDTO objectModuleDTO);

    List<ObjectModuleEntity> getBySystemId(Integer systemId);

    List<ObjectModuleEntity> query(ObjectModuleDTO objectModuleDTO);

}
