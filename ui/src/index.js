import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import login from "./component/login.vue";

new Vue({
    el: "#main",

    components: {
        "login": login
    }
});