import baseApi from "./baseApi.js";

let uri = "userManage";

export default {
    showUser() {
        return baseApi.send({
            url: `${uri}/showUser`,
            type: "POST"
        });
    },

    deleteUser() {
        return baseApi.send({
            url: `${uri}/deleteUser`,
            type: "POST"
        });
    },

    editUser(data) {
        return baseApi.send({
            url: `${uri}/editUser`,
            type: "POST",
            data: data
        });
    }
};