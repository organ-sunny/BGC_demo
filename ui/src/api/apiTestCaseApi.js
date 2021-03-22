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
    }
};