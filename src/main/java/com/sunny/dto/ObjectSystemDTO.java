package com.sunny.dto;

import com.sunny.entity.ObjectSystemEntity;

public class ObjectSystemDTO extends ParentDTO<ObjectSystemEntity> {

    @NotNull
    private String objectSystem;

    @Override
    public String toString() {
        return "ObjectSystemDTO{" +
                "objectSystem='" + objectSystem + '\'' +
                '}';
    }

    public String getObjectSystem() {
        return objectSystem;
    }

    public void setObjectSystem(String objectSystem) {
        this.objectSystem = objectSystem;
    }
}
