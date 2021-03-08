export default {
    isEmpty(data) {
        return data === undefined || data === null || data === "";
    },

    extend(o1, o2) {
        for (let key in o1) {
            if (o1.hasOwnProperty(key)) {
                o1[key] = o2[key];
            }
        }
    }
};