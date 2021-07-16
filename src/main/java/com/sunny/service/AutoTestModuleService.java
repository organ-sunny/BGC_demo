package com.sunny.service;

import com.sunny.dto.AutoTestModuleDTO;
import com.sunny.entity.AutoTestModuleEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface AutoTestModuleService {

    /**
     * 查询
     *
     * @return List<AutoTestModuleEntity>
     * */
    List<AutoTestModuleDTO> query();

    /**
     * 保存
     *
     * @param autoTestModuleDTO autoTestModuleDTO
     * */
    void save(AutoTestModuleDTO autoTestModuleDTO);

    /**
     * 通过名称查找
     *
     * @param name name
     *
     * @return AutoTestModuleEntity
     * */
    AutoTestModuleEntity getByName(String name);

}