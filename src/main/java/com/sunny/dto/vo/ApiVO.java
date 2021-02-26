package com.sunny.dto.vo;

public class ApiVO {
    private Integer apiId;
    private String objectSystemName;
    private String objectModuleName;
    private String objectApiName;
    private String apiName;
    private String apiAddress;
    private String apiMethod;

    @Override
    public String toString() {
        return "ApiVO{" +
                "apiId=" + apiId +
                ", objectSystemName='" + objectSystemName + '\'' +
                ", objectModuleName='" + objectModuleName + '\'' +
                ", objectApiName='" + objectApiName + '\'' +
                ", apiName='" + apiName + '\'' +
                ", apiAddress='" + apiAddress + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
                '}';
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
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
}
