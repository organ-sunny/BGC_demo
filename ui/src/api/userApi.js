import baseApi from "./baseApi.js";

let uri = "user";

export default {
    login(data) {
        return baseApi.send({
            url: `${uri}/login`,
            type: "POST",
            data: data
        });
    },

    sendMailCode(data) {
        return baseApi.send({
            url: `${uri}/sendMailCode`,
            type: "POST",
            data: data
        });
    },

    register(data) {
        return baseApi.send({
            url: `${uri}/register`,
            type: "POST",
            data: data
        });
    }
};