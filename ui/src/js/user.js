import cookieUtil from "../util/cookieUtil.js";

let key = "user";

export default {
    set(data) {
        cookieUtil.set(key, JSON.stringify(data));
    },

    get() {
        let user = cookieUtil.get(key);
        if (user === null) {
            return null;
        }
        return JSON.parse(user);
    },

    delete() {
        cookieUtil.delete(key);
    }
};
