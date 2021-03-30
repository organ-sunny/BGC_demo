import baseApi from "./baseApi.js";

let u = "/apiTestCase";

export default {
    query(apiId) {
        return baseApi.send({
            url: `${u}/${apiId}`,
            type: "GET",
            header: baseApi.getHeader(),
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
                    objectApiId: "",
                    apiCaseNum: "",
                    apiCaseName: "",
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
    },

    update(id, data) {
        return baseApi.send({
            url: `${u}/${id}`,
            type: "PUT",
            header: baseApi.getHeader(),
            data,
            check: {
                data: {
                    objectApiId: "",
                    apiCaseNum: "",
                    apiCaseName: "",
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
    },

    delete(idList) {
        return baseApi.send({
            url: `${u}`,
            type: "DELETE",
            header: baseApi.getHeader(),
            data: idList
        });
    },

    run(idList) {
        return baseApi.send({
            url: `${u}/runApiCase`,
            type: "POST",
            header: baseApi.getHeader(),
            data: idList
        });
    }
};