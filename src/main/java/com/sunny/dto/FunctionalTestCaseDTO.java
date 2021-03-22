package com.sunny.dto;

import com.sunny.dto.annotation.NotNull;
import com.sunny.entity.FunctionalTestCaseEntity;

public class FunctionalTestCaseDTO extends ParentDTO<FunctionalTestCaseEntity> {

    @NotNull
    private String objectSystemName;

    @NotNull
    private String objectModuleName;

    @NotNull
    private String caseName;

    @NotNull
    private String caseNum;

    @NotNull
    private String caseDescription;

    @NotNull
    private String preconditions;

    @NotNull
    private String testSteps;

    @NotNull
    private String expectedResult;

    private String actualResult;
    private String isPassed;
    private String caseRemark;

    @Override
    public String toString() {
        return "FunctionalTestCaseDTO{" +
                "objectSystemName='" + objectSystemName + '\'' +
                ", objectModuleName='" + objectModuleName + '\'' +
                ", caseName='" + caseName + '\'' +
                ", caseNum='" + caseNum + '\'' +
                ", caseDescription='" + caseDescription + '\'' +
                ", preconditions='" + preconditions + '\'' +
                ", testSteps='" + testSteps + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", actualResult='" + actualResult + '\'' +
                ", isPassed='" + isPassed + '\'' +
                ", caseRemark='" + caseRemark + '\'' +
                '}';
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

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getTestSteps() {
        return testSteps;
    }

    public void setTestSteps(String testSteps) {
        this.testSteps = testSteps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }

    public String getCaseRemark() {
        return caseRemark;
    }

    public void setCaseRemark(String caseRemark) {
        this.caseRemark = caseRemark;
    }
}
