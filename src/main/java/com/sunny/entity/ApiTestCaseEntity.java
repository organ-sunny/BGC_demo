package com.sunny.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_api_test_case")
public class ApiTestCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OBJECT_API_ID")
    private Integer objectApiId;

    @Column(name = "API_TESTCASE_NAME")
    private String apiCaseName;

    @Column(name = "API_TESTCASE_NUM")
    private String apiCaseNum;

    @Column(name = "API_TESTCASE_DESCRIPTION")
    private String apiCaseDescription;

    @Column(name = "API_TESTCASE_REQUESTHEADER")
    private String apiCaseRequestHeader;

    @Column(name = "API_TESTCASE_REQUESTPARA")
    private String apiCaseRequestParam;

    @Column(name = "API_TESTCASE_EXPECTEDRESULT")
    private String apiCaseExpectedResult;

    @Column(name = "API_TESTCASE_ACTUALRESULT")
    private String apiCaseActualResult;

    @Column(name = "ISPASSED")
    private String isPassed;

    @Column(name = "API_TESTCASE_REMARK")
    private String apiCaseRemark;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATEDTIME")
    private Date createdTime;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDTIME")
    private Date updatedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjectApiId() {
        return objectApiId;
    }

    public void setObjectApiId(Integer objectApiId) {
        this.objectApiId = objectApiId;
    }

    public String getApiCaseName() {
        return apiCaseName;
    }

    public void setApiCaseName(String apiCaseName) {
        this.apiCaseName = apiCaseName;
    }

    public String getApiCaseNum() {
        return apiCaseNum;
    }

    public void setApiCaseNum(String apiCaseNum) {
        this.apiCaseNum = apiCaseNum;
    }

    public String getApiCaseDescription() {
        return apiCaseDescription;
    }

    public void setApiCaseDescription(String apiCaseDescription) {
        this.apiCaseDescription = apiCaseDescription;
    }

    public String getApiCaseRequestHeader() {
        return apiCaseRequestHeader;
    }

    public void setApiCaseRequestHeader(String apiCaseRequestHeader) {
        this.apiCaseRequestHeader = apiCaseRequestHeader;
    }

    public String getApiCaseRequestParam() {
        return apiCaseRequestParam;
    }

    public void setApiCaseRequestParam(String apiCaseRequestParam) {
        this.apiCaseRequestParam = apiCaseRequestParam;
    }

    public String getApiCaseExpectedResult() {
        return apiCaseExpectedResult;
    }

    public void setApiCaseExpectedResult(String apiCaseExpectedResult) {
        this.apiCaseExpectedResult = apiCaseExpectedResult;
    }

    public String getApiCaseActualResult() {
        return apiCaseActualResult;
    }

    public void setApiCaseActualResult(String apiCaseActualResult) {
        this.apiCaseActualResult = apiCaseActualResult;
    }

    public String getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }

    public String getApiCaseRemark() {
        return apiCaseRemark;
    }

    public void setApiCaseRemark(String apiCaseRemark) {
        this.apiCaseRemark = apiCaseRemark;
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

    @Override
    public String toString() {
        return "ApiTestCaseEntity{" +
                "id=" + id +
                ", apiId=" + objectApiId +
                ", apiCaseName='" + apiCaseName + '\'' +
                ", apiCaseNum='" + apiCaseNum + '\'' +
                ", apiCaseDescription='" + apiCaseDescription + '\'' +
                ", apiCaseRequestHeader='" + apiCaseRequestHeader + '\'' +
                ", apiCaseRequestParam='" + apiCaseRequestParam + '\'' +
                ", apiCaseExpectedResult='" + apiCaseExpectedResult + '\'' +
                ", apiCaseActualResult='" + apiCaseActualResult + '\'' +
                ", isPassed='" + isPassed + '\'' +
                ", apiCaseRemark='" + apiCaseRemark + '\'' +
                ", creator='" + creator + '\'' +
                ", createdTime=" + createdTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
                '}';
    }

}
