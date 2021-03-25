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
                urlData: {
                    moduleId: ""
                }
            }
        });
    },

    add(data) {
        return baseApi.send({
            url: `${u}`,
            type: "POST",
            header: baseApi.getHeader(),
            data,
            check: {
                data: {
                    moduleId: "",
                    apiName: "",
                    apiAddress: "",
                    apiMethod: ""
                }
            }
        });
    },

    delete(idList) {
        return baseApi.send({
            url: `${u}`,
            type: "DELETE",
            header: baseApi.getHeader(),
            data: idList
        });
    },

    update(id, data) {
        return baseApi.send({
            url: `${u}/${id}`,
            type: "PUT",
            header: baseApi.getHeader(),
            data: data,
            check: {
                data: {
                    apiName: "",
                    apiAddress: "",
                    apiMethod: ""
                }
            }
        });
    }
};