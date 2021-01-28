import baseApi from "./baseApi.js";

let uri = "securityCenter";

export default {
    updatePassword(data) {
        return baseApi.send({
            url: `${uri}/updatePassword`,
            type: "POST",
            data: data
        });
    },

    updateEmail(data) {
        return baseApi.send({
            url: `${uri}/updateEmail`,
            type: "POST",
            data: data
        });
    }
};