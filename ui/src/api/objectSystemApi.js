import baseApi from "./baseApi.js";

let u = "/objectSystem";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}/query`,
            type: "GET",
            urlData: urlData,
            header: baseApi.getHeader(),
            check: {
                urlData: {
                    objectSystem: ""
                }
            }
        });
    },

    add(data) {
        return baseApi.send({
            url: `${u}/add`,
            type: "POST",
            header: baseApi.getHeader(),
            data: data,
            check: {
                data: {
                    objectSystem: ""
                }
            }
        });
    },

    update(id, data) {
        return baseApi.send({
            url: `${u}/${id}`,
            type: "put",
            header: baseApi.getHeader(),
            data: data,
            check: {
                data: {
                    objectSystem: ""
                }
            }
        });
    },

    delete(data) {
        return baseApi.send({
            url: `${u}`,
            type: "DELETE",
            header: baseApi.getHeader(),
            data: data
        });
    }
};