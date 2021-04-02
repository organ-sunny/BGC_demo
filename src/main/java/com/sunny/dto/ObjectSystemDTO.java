package com.sunny.dto;

import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.ObjectSystemEntity;

public class ObjectSystemDTO extends ParentDTO<ObjectSystemEntity> {

    @NotNull
    private String objectSystem;

    private Integer id;

    @Override
    public String toString() {
        return "ObjectSystemDTO{" +
                ", objectSystem='" + objectSystem + '\'' +
                '}';
    }

    public String getObjectSystem() {
        return objectSystem;
    }

    public void setObjectSystem(String objectSystem) {
        this.objectSystem = objectSystem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
