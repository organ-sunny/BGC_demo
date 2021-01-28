<template>
    <div style="width: 100%;height: 100%;">
        <div style="height: 5%;background-color: #2f89fc;display: flex;box-shadow: 0 1px 5px rgba(0,0,0,.2), 0 2px 2px rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.12);z-index: 1;position: relative;">
            <div style="font-size: 18px;font-weight: bold;color: white;width: 10%;" class="center">
                BGC_demo
            </div>
            <div style="width: 90%;display: flex;flex-direction: row-reverse;">
                <div style="display: flex;">
                    <div class="center" style="color: white;">
                        欢迎，{{user.username}}！
                    </div>
                    <div style="padding: 0 20px;" class="center">
                        <ep-icon @click="loginOut()" icon="power" theme="light" tooltip-content="注销"></ep-icon>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 95%;display: flex;">
            <div style="width: 10%;height: 100%;box-shadow: 0 1px 5px rgba(0,0,0,.2), 0 2px 2px rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.12);">
                <ep-menu :default-index="currentRoute" class="ep-menu-demo-vertical" theme="light">
                    <ep-menu-item @click="switchRouter(value.path)" v-for="(value, key) in routes" :key="key" :index="value.path" icon="ios-home">{{value.meta.title}}</ep-menu-item>
                </ep-menu>
            </div>
            <div style="width: 90%;height: 100%;background-color: #00000014;padding: 20px;box-sizing: border-box;overflow: auto;">
                <router-view v-wechat-title="$route.meta.title"></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    import user from "../js/user.js";
    import pageConfig from "../config/pageConfig.js";
    import token from "../js/token.js";

    export default {
        name: "home",

        data() {
            return {
                currentRoute: "",
                routes: [],
                user: {}
            };
        },

        methods: {
            switchRouter(path) {
                this.$router.push(path);
            },

            loginOut() {
                token.delete();
                user.delete();
                window.location.href = pageConfig.index;
            }
        },

        created() {
            this.routes = [];
            for (let item of this.$router.options.routes) {
                if (item.path !== "/") {
                    this.routes.push(item);
                }
            }
            this.currentRoute = this.$route.path;

            this.user = user.get();
            if (this.user === null) {
                window.location.href = pageConfig.index;
            }
        }
    }
</script>