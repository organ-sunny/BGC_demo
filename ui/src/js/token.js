import cookieUtil from "../util/cookieUtil.js";
import cookieConfig from "../config/cookieConfig.js";

export default {
    set(value) {
        cookieUtil.set(cookieConfig.token, value);
    },

    get() {
        return cookieUtil.get(cookieConfig.token);
    },

    delete() {
        cookieUtil.delete(cookieConfig.token);
    }
};