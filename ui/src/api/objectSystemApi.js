import baseApi from "./baseApi.js";
import variableUtil from "../util/variableUtil.js";

let uri = "objectSystem";

export default {
    add(data) {
        return baseApi.send({
            url: `${uri}/add`,
            type: "POST",
            data: data
        });
    },

    query(objectSystem) {
        let u = "";
        if (!variableUtil.isEmpty(objectSystem)) {
            u = `?objectSystem=${objectSystem}`;
        }

        return baseApi.send({
            url: `${uri}/query${u}`,
            type: "GET"
        });
    }
};