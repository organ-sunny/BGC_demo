package com.sunny.service.impl;

import com.sunny.dto.AutoTestModuleDTO;
import com.sunny.entity.AutoTestModuleEntity;
import com.sunny.entity.AutoTestStepEntity;
import com.sunny.exception.BusinessException;
import com.sunny.repository.AutoTestModuleRepository;
import com.sunny.service.AutoTestModuleService;
import com.sunny.service.AutoTestStepService;
import com.sunny.util.StringUtil;
import com.zj0724.common.util.ObjectUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public final class AutoTestModuleServiceImpl implements AutoTestModuleService {

    @Resource
    private AutoTestModuleRepository autoTestModuleRepository;

    @Resource
    private AutoTestStepService autoTestStepService;

    @Override
    public List<AutoTestModuleDTO> query() {
        List<AutoTestModuleEntity> all = autoTestModuleRepository.findAll();
        List<AutoTestModuleDTO> result = new ArrayList<>();
        for (AutoTestModuleEntity autoTestModuleEntity : all) {
            AutoTestModuleDTO autoTestModuleDTO = ObjectUtil.parse(autoTestModuleEntity, AutoTestModuleDTO.class);
            String stepIdList = autoTestModuleEntity.getStepIdList();
            List<AutoTestStepEntity> autoTestStepEntities = new LinkedList<>();
            if (!com.zj0724.common.util.StringUtil.isEmpty(stepIdList)) {
                String[] split = stepIdList.split(";");
                for (String s : split) {
                    autoTestStepEntities.add(autoTestStepService.getById(Integer.parseInt(s)));
                }
            }
            autoTestModuleDTO.setSteps(autoTestStepEntities);
            result.add(autoTestModuleDTO);
        }
        return result;
    }

    @Override
    public void save(AutoTestModuleDTO autoTestModuleDTO) {
        if (autoTestModuleDTO == null) {
            throw new BusinessException("参数缺失");
        }
        if (StringUtil.isEmpty(autoTestModuleDTO.getName())) {
            throw new BusinessException("名称不能为空");
        }
        List<AutoTestStepEntity> steps = autoTestModuleDTO.getSteps();
        if (steps == null || steps.size() == 0) {
            throw new BusinessException("step至少一项");
        }
        for (AutoTestStepEntity autoTestStepEntity : steps) {
            autoTestStepService.check(autoTestStepEntity);
        }
        if (getByName(autoTestModuleDTO.getName()) != null) {
            throw new BusinessException("名称不能重复");
        }

        String stepIdList = "";
        for (AutoTestStepEntity autoTestStepEntity : steps) {
            autoTestStepService.save(autoTestStepEntity);
            stepIdList = com.zj0724.common.util.StringUtil.append(stepIdList, ";", autoTestStepEntity.getId().toString());
        }
        stepIdList = stepIdList.substring(1);
        autoTestModuleDTO.setStepIdList(stepIdList);

        // 转换
        AutoTestModuleEntity autoTestModuleEntity = ObjectUtil.parse(autoTestModuleDTO, AutoTestModuleEntity.class);
        // 保存
        autoTestModuleRepository.save(autoTestModuleEntity);
    }

    @Override
    public AutoTestModuleEntity getByName(String name) {
        if (com.zj0724.common.util.StringUtil.isEmpty(name)) {
            return null;
        }
        List<AutoTestModuleEntity> allByName = autoTestModuleRepository.findAllByName(name);
        if (allByName.size() == 0) {
            return null;
        }
        if (allByName.size() != 1) {
            throw new BusinessException("通过名称查到到多个");
        }
        return allByName.get(0);
    }

}