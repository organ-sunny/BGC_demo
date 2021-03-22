package com.sunny.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "t_functional_test_case")
public class FunctionalTestCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OBJECT_MODULE_ID")
    private Integer moduleId;

    @Column(name = "FUNCTIONAL_TESTCASE_NAME")
    private String caseName;

    @Column(name = "FUNCTIONAL_TESTCASE_NUM")
    private String caseNum;

    @Column(name = "FUNCTIONAL_TESTCASE_DESCRIPTION")
    private String caseDescription;

    @Column(name = "FUNCTIONAL_TESTCASE_PRECONDITIONS")
    private String preconditions;

    @Column(name = "FUNCTIONAL_TESTCASE_STEPS")
    private String testSteps;

    @Column(name = "FUNCTIONAL_TESTCASE_EXPECTEDRESULT")
    private String expectedResult;

    @Column(name = "FUNCTIONAL_TESTCASE_ACTUALRESULT")
    private String actualResult;

    @Column(name = "ISPASSED")
    private String isPassed;

    @Column(name = "FUNCTIONAL_TESTCASE_REMARK")
    private String caseRemark;

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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        moduleId = moduleId;
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
        return "FunctionalTestCaseEntity{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", caseName='" + caseName + '\'' +
                ", caseNum='" + caseNum + '\'' +
                ", caseDescription='" + caseDescription + '\'' +
                ", preconditions='" + preconditions + '\'' +
                ", testSteps='" + testSteps + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", actualResult='" + actualResult + '\'' +
                ", isPassed='" + isPassed + '\'' +
                ", caseRemark='" + caseRemark + '\'' +
                ", creator='" + creator + '\'' +
                ", createdTime=" + createdTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
