import baseApi from "./baseApi.js";

let u = "/objectApi";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}`,
            type: "GET",
            header: baseApi.getHeader(),
            urlData,
            check: {
                urlData: {
                    moduleId: ""
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
                    moduleId: "",
                    apiName: "",
                    apiAddress: "",
                    apiMethod: ""
                }
            }
        });
    }
};