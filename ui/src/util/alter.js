import Vue from "vue/dist/vue.esm.js";

export default {
    error(message) {
        Vue.prototype.$pop({
            type: "danger",
            message: message
        });
    },

    info(message) {
        Vue.prototype.$pop({
            type: "info",
            message: message
        });
    },

    success(message) {
        Vue.prototype.$pop({
            type: "success",
            message: message
        });
    },

    popup(message) {
        // Vue.prototype.$confirm({
        //     word: message,
        //     callback (errorInfo) {}
        // })
        window.alert(message);
    },

    confirm(message) {
        return new Promise((s, e) => {
            Vue.prototype.$confirm({
                word: message,
                callback (errorInfo) {
                    if (errorInfo) {
                        s();
                    } else {
                        e();
                    }
                }
            });
        });
    }
};