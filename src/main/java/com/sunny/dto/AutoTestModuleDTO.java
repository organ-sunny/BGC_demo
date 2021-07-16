package com.sunny.dto;

import com.sunny.entity.AutoTestModuleEntity;
import com.sunny.entity.AutoTestStepEntity;
import java.util.LinkedList;
import java.util.List;

public final class AutoTestModuleDTO extends AutoTestModuleEntity {

    private List<AutoTestStepEntity> steps = new LinkedList<>();

    public List<AutoTestStepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<AutoTestStepEntity> steps) {
        this.steps = steps;
    }

}