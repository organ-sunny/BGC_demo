import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import VueRouter from "vue-router";
import homeIndex from "./component/home/index.vue";
import baseUserInfo from "./component/home/user/baseUserInfo.vue";
import securityUserInfo from "./component/home/user/securityUserInfo.vue";

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
            path: "/baseUserInfo",
            component: baseUserInfo,
            meta: {
                title: `基础信息`
            }
        },
        {
            path: "/securityUserInfo",
            component: securityUserInfo,
            meta: {
                title: `安全中心`
            }
        }
    ]
});

new Vue({
    el: "#main",

    router: vueRouter
});