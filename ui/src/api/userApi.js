import baseApi from "./baseApi.js";

let uri = "user";

export default {
    login(data) {
        return baseApi.send({
            url: `${uri}/login`,
            type: "POST",
            data: data
        });
    }
};