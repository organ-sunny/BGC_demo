import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import home from "./component/home.vue";
import VueRouter from "vue-router";
import home_system from "./component/home/system.vue";
import home_apiTest from "./component/home/apiTest.vue";

new Vue({
    el: "#main",

    router: new VueRouter({
        routes: [
            {
                path: "/apiTest",
                component: home_apiTest,
                meta: {
                    title: `接口测试`
                }
            },
            {
                path: "/system",
                component: home_system,
                meta: {
                    title: `系统配置`
                }
            }
        ]
    }),

    components: {
        "home": home
    }
});