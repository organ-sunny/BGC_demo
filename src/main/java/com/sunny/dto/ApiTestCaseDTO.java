package com.sunny.dto;

import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.ApiTestCaseEntity;

public class ApiTestCaseDTO extends ParentDTO<ApiTestCaseEntity> {

    private Integer id;

    @NotNull
    private String objectSystemName;

    @NotNull
    private String objectModuleName;

    @NotNull
    private String apiCaseNum;

    @NotNull
    private String apiCaseName;

    @NotNull
    private String apiCaseDescription;

    @NotNull
    private String apiCaseRequestAddress;

    @NotNull
    private String apiCaseRequestMethod;

    private String apiCaseRequestHeader;

    @NotNull
    private String apiCaseRequestParam;

    @NotNull
    private String apiCaseExpectedResult;

    private String apiCaseActualResult;

    private String apiCaseRemark;

    @Override
    public String toString() {
        return "ApiTestCaseDTO{" +
                "id=" + id +
                ", objectSystemName='" + objectSystemName + '\'' +
                ", objectModuleName='" + objectModuleName + '\'' +
                ", apiCaseNum='" + apiCaseNum + '\'' +
                ", apiCaseName='" + apiCaseName + '\'' +
                ", apiCaseDescription='" + apiCaseDescription + '\'' +
                ", apiCaseRequestAddress='" + apiCaseRequestAddress + '\'' +
                ", apiCaseRequestMethod='" + apiCaseRequestMethod + '\'' +
                ", apiCaseRequestHeader='" + apiCaseRequestHeader + '\'' +
                ", apiCaseRequestParam='" + apiCaseRequestParam + '\'' +
                ", apiCaseExpectedResult='" + apiCaseExpectedResult + '\'' +
                ", apiCaseActualResult='" + apiCaseActualResult + '\'' +
                ", apiCaseRemark='" + apiCaseRemark + '\'' +
                '}';
    }

    public String getApiCaseRequestHeader() {
        return apiCaseRequestHeader;
    }

    public void setApiCaseRequestHeader(String apiCaseRequestHeader) {
        this.apiCaseRequestHeader = apiCaseRequestHeader;
    }

    public String getObjectSystemName() {
        return objectSystemName;
    }

    public void setObjectSystemName(String objectSystemName) {
        this.objectSystemName = objectSystemName;
    }

    public String getObjectModuleName() {
        return objectModuleName;
    }

    public void setObjectModuleName(String objectModuleName) {
        this.objectModuleName = objectModuleName;
    }

    public String getApiCaseNum() {
        return apiCaseNum;
    }

    public void setApiCaseNum(String apiCaseNum) {
        this.apiCaseNum = apiCaseNum;
    }

    public String getApiCaseName() {
        return apiCaseName;
    }

    public void setApiCaseName(String apiCaseName) {
        this.apiCaseName = apiCaseName;
    }

    public String getApiCaseDescription() {
        return apiCaseDescription;
    }

    public void setApiCaseDescription(String apiCaseDescription) {
        this.apiCaseDescription = apiCaseDescription;
    }

    public String getApiCaseRequestAddress() {
        return apiCaseRequestAddress;
    }

    public void setApiCaseRequestAddress(String apiCaseRequestAddress) {
        this.apiCaseRequestAddress = apiCaseRequestAddress;
    }

    public String getApiCaseRequestMethod() {
        return apiCaseRequestMethod;
    }

    public void setApiCaseRequestMethod(String apiCaseRequestMethod) {
        this.apiCaseRequestMethod = apiCaseRequestMethod;
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

    public String getApiCaseRemark() {
        return apiCaseRemark;
    }

    public void setApiCaseRemark(String apiCaseRemark) {
        this.apiCaseRemark = apiCaseRemark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
