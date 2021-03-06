package com.sunny.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_object_module")
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

    @Column(name = "CREATEOR")
    private String creator;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

    @Override
    public String toString() {
        return "ObjectModuleEntity{" +
                "id=" + id +
                ", objsystemId=" + objsystemId +
                ", moduleName='" + moduleName + '\'' +
                ", percentOfPass='" + percentOfPass + '\'' +
                ", creator='" + creator + '\'' +
                ", createdTime=" + createdTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
