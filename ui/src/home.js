import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import VueRouter from "vue-router";
import homeIndex from "./component/home/index.vue";
import userInfo from "./component/home/userInfo.vue";

// 路由
let vueRouter = new VueRouter({
    routes: [
        {
            path: "/",
            redirect: "/index"
        },
        {
            path: "/index",
            component: homeIndex,
            meta: {
                title: `首页`
            }
        },
        {
            path: "/userInfo",
            component: userInfo,
            meta: {
                title: `用户信息`
            }
        }
    ]
});

new Vue({
    el: "#main",

    router: vueRouter
});