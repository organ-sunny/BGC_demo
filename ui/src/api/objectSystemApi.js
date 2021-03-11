import baseApi from "./baseApi.js";

let u = "/objectSystem";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}/query`,
            type: "GET",
            urlData: urlData,
            header: baseApi.getHeader(),
            check: {
                url: {
                    objectSystem: ""
                }
            }
        });
    },

    add(data) {
        return baseApi.send({
            url: `${u}/add`,
            type: "POST",
            header: baseApi.getHeader(),
            data: data,
            check: {
                data: {
                    objectSystem: ""
                }
            }
        });
    }
};