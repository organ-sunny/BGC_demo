import baseApi from "./baseApi.js";

let uri = "objectApi";

export default {
    query(data) {
        return baseApi.send({
            url: `${uri}`,
            type: "GET",
            data: data
        });
    },

    add(data) {
        return baseApi.send({
            url: `${uri}`,
            type: "POST",
            data: data
        });
    }
};