import baseApi from "./baseApi.js";

let u = "/objectApi";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}`,
            type: "GET",
            header: baseApi.getHeader(),
            urlData,
            check: {
                url: {
                    moduleId: ""
                }
            }
        });
    }
};