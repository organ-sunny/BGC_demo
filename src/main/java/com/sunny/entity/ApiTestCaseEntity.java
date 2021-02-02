package com.sunny.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "api_test_case")
public class ApiTestCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "API_MODULE_ID")
    private Integer api_module_id;

    @Column(name = "API_MODULE_NAME")
    private String api_module_name;

    @Column(name = "API_TESTCASE_ID")
    private String api_testcase_id;

    @Column(name = "API_TESTCASE_NAME")
    private String api_testcase_name;

    @Column(name = "API_TESTCASE_DESCRIPTION")
    private String api_testcase_description;

    @Column(name = "API_TESTCASE_REQUESTADDRESS")
    private String api_testcase_requestAddress;

    @Column(name = "API_TESTCASE_REQUESTMETHOD")
    private String api_testcase_requestMethod;

    @Column(name = "API_TESTCASE_REQUESTPARA")
    private String api_testcase_requestParam;

    @Column(name = "API_TESTCASE_EXPECTEDRESULT")
    private String api_testcase_expectedResult;

    @Column(name = "API_TESTCASE_ACTUALRESULT")
    private String api_testcase_actualResult;

    @Column(name = "CREATEOR")
    private String creator;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

    @Column(name = "API_TESTCASE_REMARK")
    private String api_testcase_remark;

    @Override
    public String toString() {
        return "ApiTestCaseEntity{" +
                "id=" + id +
                ", api_module_id=" + api_module_id +
                ", api_module_name='" + api_module_name + '\'' +
                ", api_testcase_id='" + api_testcase_id + '\'' +
                ", api_testcase_name='" + api_testcase_name + '\'' +
                ", api_testcase_description='" + api_testcase_description + '\'' +
                ", api_testcase_requestAddress='" + api_testcase_requestAddress + '\'' +
                ", api_testcase_requestMethod='" + api_testcase_requestMethod + '\'' +
                ", api_testcase_requestParam='" + api_testcase_requestParam + '\'' +
                ", api_testcase_expectedResult='" + api_testcase_expectedResult + '\'' +
                ", api_testcase_actualResult='" + api_testcase_actualResult + '\'' +
                ", api_testcase_remark='" + api_testcase_remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApi_module_id() {
        return api_module_id;
    }

    public void setApi_module_id(Integer api_module_id) {
        this.api_module_id = api_module_id;
    }

    public String getApi_module_name() {
        return api_module_name;
    }

    public void setApi_module_name(String api_module_name) {
        this.api_module_name = api_module_name;
    }

    public String getApi_testcase_id() {
        return api_testcase_id;
    }

    public void setApi_testcase_id(String api_testcase_id) {
        this.api_testcase_id = api_testcase_id;
    }

    public String getApi_testcase_name() {
        return api_testcase_name;
    }

    public void setApi_testcase_name(String api_testcase_name) {
        this.api_testcase_name = api_testcase_name;
    }
}
