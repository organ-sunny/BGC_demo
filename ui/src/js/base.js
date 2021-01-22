import Vue from "vue/dist/vue.esm.js";
import "ionicons-npm/css/ionicons.css";
import "ep-ui/theme/lib/epui.min.css";
import epUi from "ep-ui";
import "../css/base.css";
import login from "../component/login.vue";

Vue.use(epUi);
Vue.component("login", login);