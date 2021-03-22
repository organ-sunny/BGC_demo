package com.sunny.dto;

import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.ApiTestCaseEntity;

public class ApiTestCaseDTO extends ParentDTO<ApiTestCaseEntity> {

    private Integer id;

    // 系统名
    @NotNull
    private String objectSystemName;

    // 模块名
    @NotNull
    private String objectModuleName;

    // 接口名
    @NotNull
    private String objectApiName;

    // 接口用例编号
    @NotNull
    private String apiCaseNum;

    // 接口用例名
    @NotNull
    private String apiCaseName;

    // 用例描述
    @NotNull
    private String apiCaseDescription;

    // 请求地址
    @NotNull
    private String apiCaseRequestAddress;

    // 请求方式
    @NotNull
    private String apiCaseRequestMethod;

    // 请求头内容
    private String apiCaseRequestHeader;

    // 请求入参
    @NotNull
    private String apiCaseRequestParam;

    // 预期结果
    @NotNull
    private String apiCaseExpectedResult;

    // 实际结果
    private String apiCaseActualResult;

    // 备注
    private String apiCaseRemark;

    @Override
    public String toString() {
        return "ApiTestCaseDTO{" +
                "id=" + id +
                ", objectSystemName='" + objectSystemName + '\'' +
                ", objectModuleName='" + objectModuleName + '\'' +
                ", objectApiName='" + objectApiName + '\'' +
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getObjectApiName() {
        return objectApiName;
    }

    public void setObjectApiName(String objectApiName) {
        this.objectApiName = objectApiName;
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

    public String getApiCaseRemark() {
        return apiCaseRemark;
    }

    public void setApiCaseRemark(String apiCaseRemark) {
        this.apiCaseRemark = apiCaseRemark;
    }
}
