import cookieUtil from "../util/cookieUtil.js";
import cookieConfig from "../config/cookieConfig.js";

export default {
    set(data) {
        cookieUtil.set(cookieConfig.user, JSON.stringify(data));
    },

    get() {
        let user = cookieUtil.get(cookieConfig.user);
        if (user === null) {
            return null;
        }
        return JSON.parse(user);
    },

    delete() {
        cookieUtil.delete(cookieConfig.user);
    }
};
