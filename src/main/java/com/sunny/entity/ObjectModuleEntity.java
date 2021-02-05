package com.sunny.entity;

import javax.persistence.*;

@Entity
@Table(name = "object_module")
public class ObjectModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OBJSYSTEM_ID")
    private Integer objsystemId;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "PERCENTOFPASS")
    private String percentOfPass;

    @Override
    public String toString() {
        return "ObjectModuleEntity{" +
                "id=" + id +
                ", objsystemId=" + objsystemId +
                ", moduleName='" + moduleName + '\'' +
                ", percentOfPass='" + percentOfPass + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjsystemId() {
        return objsystemId;
    }

    public void setObjsystemId(Integer objsystemId) {
        this.objsystemId = objsystemId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPercentOfPass() {
        return percentOfPass;
    }

    public void setPercentOfPass(String percentOfPass) {
        this.percentOfPass = percentOfPass;
    }
}
