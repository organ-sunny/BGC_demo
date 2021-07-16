package com.sunny.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_auto_test_module")
public class AutoTestModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STEP_ID_LIST")
    private String stepIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStepIdList() {
        return stepIdList;
    }

    public void setStepIdList(String stepIdList) {
        this.stepIdList = stepIdList;
    }

}