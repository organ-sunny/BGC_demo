import baseApi from "./baseApi.js";

let u = "/apiTestCase";

export default {
    query(data) {
        return baseApi.send({
            url: `${u}/query`,
            type: "POST",
            header: baseApi.getHeader(),
            data,
            check: {
                urlData: {
                    apiId: ""
                }
            }
        });
    },

    add(data) {
        return baseApi.send({
            url: `${u}`,
            type: "POST",
            header: baseApi.getHeader(),
            data,
            check: {
                data: {
                    apiId: "",
                    apiCaseName: "",
                    apiCaseNum: "",
                    apiCaseDescription: "",
                    apiCaseRequestHeader: "",
                    apiCaseRequestParam: "",
                    apiCaseExpectedResult: "",
                    apiCaseActualResult: "",
                    isPassed: "",
                    apiCaseRemark: ""
                }
            }
        });
    }
};