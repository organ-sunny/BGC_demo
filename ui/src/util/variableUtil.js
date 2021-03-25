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
    },

    // 数据还原
    reset(data) {
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                let value = data[key];
                if (this.IsTypeof(value, String)) {
                    data[key] = "";
                }
                if (this.IsTypeof(value, Array)) {
                    data[key] = [];
                }
            }
        }
    },

    IsTypeof(data, type) {
        if (type === String) {
            return typeof data === "string";
        }
        return data instanceof type;
    }
};