import baseApi from "./baseApi.js";

let u = "/autoTestModule";

export default {
    query() {
        return baseApi.send({
            url: `${u}/query`,
            type: "POST",
            header: baseApi.getHeader()
        });
    },

    save(data) {
        return baseApi.send({
            url: `${u}/save`,
            type: "POST",
            header: baseApi.getHeader(),
            data: data
        });
    }
};