export default {
    isEmpty(data) {
        return data === undefined || data === null || data === "";
    },

    extend(o1, o2) {
        for (let key in o1) {
            if (o1.hasOwnProperty(key)) {
                let o2V = o2[key];
                if (!this.isEmpty(o2V)) {
                    o1[key] = o2V;
                }
            }
        }
    }
};