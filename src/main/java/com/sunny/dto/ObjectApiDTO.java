package com.sunny.dto;

import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.ObjectApiEntity;

public class ObjectApiDTO extends ParentDTO<ObjectApiEntity> {

    private Integer id;

    @NotNull
    private Integer moduleId;

    @NotNull
    private String apiName;

    @NotNull
    private String apiAddress;

    @NotNull
    private String apiMethod;

    @Override
    public String toString() {
        return "ObjectApiDTO{" +
                "id=" + id +
                "moduleId=" + moduleId +
                ", apiName='" + apiName + '\'' +
                ", apiAddress='" + apiAddress + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiAddress() {
        return apiAddress;
    }

    public void setApiAddress(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }
}
