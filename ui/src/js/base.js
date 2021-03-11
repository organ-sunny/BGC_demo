import Vue from "vue/dist/vue.esm.js";
import "ionicons-npm/css/ionicons.css";
import "ep-ui/theme/lib/epui.min.css";
import epUi from "ep-ui";
import "../css/base.css";
import VueRouter from "vue-router";
import VueWechatTitle from "vue-wechat-title";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

// bootstrap
import "jquery";
import "bootstrap/dist/js/bootstrap.js";
import "bootstrap/dist/css/bootstrap.css";

Vue.use(epUi);
Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.use(VueWechatTitle);