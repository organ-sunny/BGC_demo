package com.sunny.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "object_system")
public class ObjectSystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OBJSYSTEM")
    private String objectSystem;

    @Column(name = "CREATEOR")
    private String creator;

    @Column(name = "CREATEDTIME")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDTIME")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @Override
    public String toString() {
        return "ObjectSystemEntity{" +
                "id=" + id +
                ", objectSystem='" + objectSystem + '\'' +
                ", creator='" + creator + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectSystem() {
        return objectSystem;
    }

    public void setObjectSystem(String objectSystem) {
        this.objectSystem = objectSystem;
    }



}
