package com.sunny.dto;

import com.sunny.entity.ObjectModuleEntity;

public class ObjectModuleDTO extends ParentDTO<ObjectModuleEntity> {

    private Integer id;

    private String moduleName;

    private Integer objsystemId;

    @Override
    public String toString() {
        return "ObjectModuleDTO{" +
                "moduleName='" + moduleName + '\'' +
                ", objsystemId=" + objsystemId +
                '}';
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getObjsystemId() {
        return objsystemId;
    }

    public void setObjsystemId(Integer objsystemId) {
        this.objsystemId = objsystemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

