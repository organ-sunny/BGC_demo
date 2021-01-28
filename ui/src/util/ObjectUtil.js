export default {
    extend(o1, o2) {
        for (let key in o2) {
            if (o2.hasOwnProperty(key)) {
                o1[key] = o2[key];
            }
        }
    }
};