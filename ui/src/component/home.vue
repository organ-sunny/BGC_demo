<template>
    <div style="width: 100%;height: 100%;">
        <div style="height: 5%;background-color: #001529;display: flex;box-shadow: 0 1px 5px rgba(0,0,0,.2), 0 2px 2px rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.12);z-index: 1;position: relative;">
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
            <div style="width: 10%;height: 100%;box-shadow: 0 1px 5px rgba(0,0,0,.2), 0 2px 2px rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.12);background-color: #001529;">
                <ep-menu :default-index="currentRoute" class="ep-menu-demo-vertical" theme="dark">
                    <ep-menu-item @click="switchRouter('/index')" index="/index" icon="ios-home">首页</ep-menu-item>
                    <ep-submenu icon="person" title="信息维护">
                        <ep-menu-item icon="information" @click="switchRouter('/baseUserInfo')" index="/baseUserInfo">基础信息</ep-menu-item>
                        <ep-menu-item icon="information" @click="switchRouter('/securityUserInfo')" index="/securityUserInfo">安全中心</ep-menu-item>
                    </ep-submenu>
                </ep-menu>
            </div>
            <div style="width: 90%;height: 100%;background-color: #00000014;padding: 50px;box-sizing: border-box;overflow: auto;">
                <router-view v-wechat-title="$route.meta.title"></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    import user from "../js/user.js";
    import pageConfig from "../config/pageConfig.js";
    import token from "../js/token.js";
    import userManageApi from "../api/userManageApi.js";

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
            },

            refreshUser() {
                userManageApi.showUser().then((data) => {
                    this.user = data;
                    user.set(data);
                });
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