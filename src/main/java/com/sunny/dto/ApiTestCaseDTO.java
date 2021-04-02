package com.sunny.dto;

import com.alibaba.fastjson.JSON;
import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.ApiTestCaseEntity;

import java.util.Map;

public class ApiTestCaseDTO extends ParentDTO<ApiTestCaseEntity> {

    // 接口编号
    @NotNull
    private Integer objectApiId;

    // 接口用例编号
    @NotNull
    private String apiCaseNum;

    // 接口用例名
    @NotNull
    private String apiCaseName;

    // 用例描述
    @NotNull
    private String apiCaseDescription;

    // 请求头内容
    private String apiCaseRequestHeader;

    // 请求入参
    private String apiCaseRequestParam;

    // 预期结果
    @NotNull
    private String apiCaseExpectedResult;

    // 实际结果
    private String apiCaseActualResult;

    // 是否通过
    private String isPassed;

    // 备注
    private String apiCaseRemark;

    @Override
    public String toString() {
        return "ApiTestCaseDTO{" +
                ", objectApiId=" + objectApiId +
                ", apiCaseNum='" + apiCaseNum + '\'' +
                ", apiCaseName='" + apiCaseName + '\'' +
                ", apiCaseDescription='" + apiCaseDescription + '\'' +
                ", apiCaseRequestHeader='" + apiCaseRequestHeader + '\'' +
                ", apiCaseRequestParam='" + apiCaseRequestParam + '\'' +
                ", apiCaseExpectedResult='" + apiCaseExpectedResult + '\'' +
                ", apiCaseActualResult='" + apiCaseActualResult + '\'' +
                ", isPassed='" + isPassed + '\'' +
                ", apiCaseRemark='" + apiCaseRemark + '\'' +
                '}';
    }

    public Integer getObjectApiId() {
        return objectApiId;
    }

    public void setObjectApiId(Integer objectApiId) {
        this.objectApiId = objectApiId;
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

    // 解析请求入参
    public static class analysisApiCaseRequestParam{

        String type;
        String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @SuppressWarnings("unchecked")
        public Map<String, Object> getContent() {
            return (Map<String, Object>) JSON.parseObject(content, Map.class);
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
