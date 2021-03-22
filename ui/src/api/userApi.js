import baseApi from "./baseApi.js";

let u = "/user";

export default {
    login(data) {
        return baseApi.send({
            url: `${u}/login`,
            type: "POST",
            data: data,
            check: {
                data: {
                    username: "",
                    password: ""
                }
            }
        });
    },
};