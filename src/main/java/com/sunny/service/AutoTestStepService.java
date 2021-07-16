package com.sunny.service;

import com.sunny.entity.AutoTestStepEntity;
import org.springframework.stereotype.Service;

@Service
public interface AutoTestStepService {

    /**
     * 保存
     *
     * @param autoTestStepEntity autoTestStepEntity
     * */
    void save(AutoTestStepEntity autoTestStepEntity);

    /**
     * 校验
     *
     * @param autoTestStepEntity autoTestStepEntity
     * */
    void check(AutoTestStepEntity autoTestStepEntity);

    /**
     * 通过id查询
     *
     * @param id id
     *
     * @return AutoTestStepEntity
     * */
    AutoTestStepEntity getById(Integer id);

}