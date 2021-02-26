package com.sunny.entity;

import com.sunny.dto.vo.ApiVO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_object_api")
public class ObjectApiEntity extends ParentEntity<ApiVO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MODULE_ID")
    private Integer moduleId;

    @Column(name = "API_NAME")
    private String apiName;

    @Column(name = "API_ADDRESS")
    private String apiAddress;

    @Column(name = "API_METHOD")
    private String apiMethod;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

    @Override
    public String toString() {
        return "ObjectApiEntity{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", apiName='" + apiName + '\'' +
                ", apiAddress='" + apiAddress + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
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
