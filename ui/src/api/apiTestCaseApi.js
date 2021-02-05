import baseApi from "./baseApi.js";
import variableUtil from "../util/variableUtil.js";

let uri = "apiTestCase";

export default {
    add(data) {
        return baseApi.send({
            url: `${uri}/add`,
            type: "POST",
            data: data
        });
    },

    query(apiCaseNum) {
        let p = variableUtil.isEmpty(apiCaseNum) ? "" : `?apiCaseNum=${apiCaseNum}`;

        return baseApi.send({
            url: `${uri}/query${p}`,
            type: "GET"
        });
    }
};