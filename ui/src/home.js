import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import VueRouter from "vue-router";
import homeIndex from "./component/home/index.vue";
import baseUserInfo from "./component/home/user/baseUserInfo.vue";
import securityUserInfo from "./component/home/user/securityUserInfo.vue";
import system from "./component/home/system.vue";
import apiTestcase from "./component/home/apiAuto/testcase.vue";
import api from "./component/home/apiAuto/api.vue";

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
            path: "/system",
            component: system,
            meta: {
                title: `系统管理`
            }
        },
        {
            path: "/apiAutoApi",
            component: api,
            meta: {
                title: `接口自动化 - 接口管理`
            }
        },
        {
            path: "/apiAutoTestcase",
            component: apiTestcase,
            meta: {
                title: `接口自动化 - 案例管理`
            }
        },
        {
            path: "/baseUserInfo",
            component: baseUserInfo,
            meta: {
                title: `信息维护 - 基础信息`
            }
        },
        {
            path: "/securityUserInfo",
            component: securityUserInfo,
            meta: {
                title: `信息维护 - 安全中心`
            }
        }
    ]
});

new Vue({
    el: "#main",

    router: vueRouter
});