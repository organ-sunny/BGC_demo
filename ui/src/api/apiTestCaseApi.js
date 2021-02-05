import baseApi from "./baseApi.js";

let uri = "apiTestCase";

export default {
    add(data) {
        return baseApi.send({
            url: `${uri}/add`,
            type: "POST",
            data: data
        });
    },

    query(data) {
        return baseApi.send({
            url: `${uri}/query`,
            type: "GET",
            data: data
        });
    }
};