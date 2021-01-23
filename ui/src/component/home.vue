<template>
    <div style="width: 100%;height: 100%;">
        <div style="height: 5%;background-color: #2f89fc;display: flex;box-shadow: 0 1px 5px rgba(0,0,0,.2), 0 2px 2px rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.12);">
            <div style="font-size: 18px;font-weight: bold;color: white;width: 10%;" class="center">
                BGC_demo
            </div>
            <div style="width: 90%;display: flex;flex-direction: row-reverse;">
                <div style="display: flex;">
                    <div class="center" style="padding: 0 20px;color: white;">
                        欢迎，用户名！
                    </div>
                    <div style="padding: 0 20px;" class="center">
                        <ep-icon icon="power" theme="light" tooltip-content="注销"></ep-icon>
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
            <div style="width: 90%;height: 100%;background-color: #00000014;padding: 20px;box-sizing: border-box;">
                <router-view v-wechat-title="$route.meta.title"></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "home",

        data() {
            return {
                currentRoute: "",
                routes: []
            };
        },

        methods: {
            switchRouter(path) {
                this.$router.push(path);
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
        }
    }
</script>