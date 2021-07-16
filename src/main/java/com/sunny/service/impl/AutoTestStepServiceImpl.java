package com.sunny.service.impl;

import com.sunny.entity.AutoTestStepEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.AutoTestStepRepository;
import com.sunny.service.AutoTestStepService;
import com.sunny.util.StringUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public final class AutoTestStepServiceImpl implements AutoTestStepService {

    @Resource
    private AutoTestStepRepository autoTestStepRepository;

    @Override
    public void save(AutoTestStepEntity autoTestStepEntity) {
        check(autoTestStepEntity);
        AutoTestStepEntity save = autoTestStepRepository.save(autoTestStepEntity);
        autoTestStepEntity.setId(save.getId());
    }

    @Override
    public void check(AutoTestStepEntity autoTestStepEntity) {
        if (StringUtil.isEmpty(autoTestStepEntity.getName())) {
            throw new BusinessException("名称不能为空");
        }
        if (StringUtil.isEmpty(autoTestStepEntity.getAction())) {
            throw new BusinessException("action不能为空");
        }
        if (StringUtil.isEmpty(autoTestStepEntity.getValue())) {
            throw new BusinessException("value不能为空");
        }
    }

    @Override
    public AutoTestStepEntity getById(Integer id) {
        return autoTestStepRepository.getOne(id);
    }

}