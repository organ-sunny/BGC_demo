import Vue from "vue/dist/vue.esm.js";
import "ionicons-npm/css/ionicons.css";
import "ep-ui/theme/lib/epui.min.css";
import epUi from "ep-ui";
import "../css/base.css";
import login from "../component/login.vue";
import VueRouter from "vue-router";
import VueWechatTitle from "vue-wechat-title";
import home from "../component/home.vue";

Vue.use(epUi);
Vue.use(VueRouter);
Vue.use(VueWechatTitle);
Vue.component("login", login);
Vue.component("home", home);