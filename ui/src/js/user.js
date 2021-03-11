import cookieUtil from "../util/cookieUtil.js";
import pageConfig from "../config/pageConfig.js";

let key = "user";

export default {
    set(data) {
        cookieUtil.set(key, JSON.stringify(data));
    },

    get() {
        let user = cookieUtil.get(key);
        if (user === null) {
            window.location.href = pageConfig.index;
            return;
        }
        return JSON.parse(user);
    },

    delete() {
        cookieUtil.delete(key);
    }
};
