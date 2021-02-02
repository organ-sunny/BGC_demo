package com.sunny.entity;

import javax.persistence.*;

@Entity
@Table(name = "api_module")
public class ApiTestModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OBJSYSTEM_ID")
    private Integer getObjsystem_id;

    @Column(name = "API_NAME")
    private String api_name;

    @Column(name = "PERCENTOFPASS")
    private String percent_of_pass;

    @Override
    public String toString() {
        return "ApiTestModuleEntity{" +
                "id=" + id +
                ", getObjsystem_id=" + getObjsystem_id +
                ", api_name='" + api_name + '\'' +
                ", percent_of_pass='" + percent_of_pass + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGetObjsystem_id() {
        return getObjsystem_id;
    }

    public void setGetObjsystem_id(Integer getObjsystem_id) {
        this.getObjsystem_id = getObjsystem_id;
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public String getPercent_of_pass() {
        return percent_of_pass;
    }

    public void setPercent_of_pass(String percent_of_pass) {
        this.percent_of_pass = percent_of_pass;
    }
}
