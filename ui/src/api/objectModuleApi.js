import baseApi from "./baseApi.js";

let u = "/objectModule";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}/query`,
            type: "GET",
            header: baseApi.getHeader(),
            urlData: urlData,
            check: {
                url: {
                    objsystemId: ""
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
                    objsystemId: "",
                    moduleName: ""
                }
            }
        });
    }
};