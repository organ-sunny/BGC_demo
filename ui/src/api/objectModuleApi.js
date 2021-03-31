import baseApi from "./baseApi.js";

let u = "/objectModule";

export default {
    query(urlData) {
        return baseApi.send({
            url: `${u}/query`,
            type: "GET",
            header: baseApi.getHeader(),
            urlData: urlData,
            check: {
                urlData: {
                    id: "",
                    objsystemId: "",
                    moduleName: ""
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
                    objsystemId: "",
                    moduleName: ""
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
    },

    update(moduleId, data) {
        return baseApi.send({
            url: `${u}/${moduleId}`,
            type: "PUT",
            header: baseApi.getHeader(),
            data: data,
            check: {
                data: {
                    moduleName: ""
                }
            }
        });
    }
};