import baseApi from "./baseApi.js";

let uri = "objectModule";

export default {
    add(data) {
        return baseApi.send({
            url: `${uri}/add`,
            type: "POST",
            data: data
        });
    },

    query(objsystemId) {
        return baseApi.send({
            url: `${uri}/query?objsystemId=${objsystemId}`,
            type: "GET"
        });
    }
};