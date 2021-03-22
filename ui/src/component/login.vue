<template>
    <div style="width: 100%;height: 100%;background-image: url('../image/80.jpg')">
        <!--        <div style="height: 5%;background-color: #2296F3;display: flex;">-->
        <!--            <div style="width: 10%;color: white;font-weight: bold;font-size: 18px;" class="center">-->
        <!--                BGC_demo-->
        <!--            </div>-->
        <!--            <div style="width: 90%;"></div>-->
        <!--        </div>-->

        <div style="height: 100%;flex-direction: column;" class="center">
            <div style="margin-bottom: 50px;font-weight: bold;font-size: 30px;color: #546E7A;">
                BGC_demo
            </div>

            <div class="panel" style="width: 20%;background-color: #f5f7fa26;">
                <div style="display: flex;">
                    <div style="width: 20%;" class="center">
                        账号
                    </div>
                    <ep-input v-model="login.username" style="margin-left: 10px;" placeholder=""></ep-input>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="width: 20%;" class="center">
                        密码
                    </div>
                    <ep-input v-model="login.password" style="margin-left: 10px;" placeholder=""></ep-input>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="loginMethod()" style="width: 100%;" type="primary">登录</ep-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import userApi from "../api/userApi.js";
import alterUtil from "../util/alterUtil.js";
import user from "../js/user.js";
import pageConfig from "../config/pageConfig.js";
import baseConfig from "../config/baseConfig.js";

export default {
    name: "login.vue",

    data() {
        return {
            login: {
                username: "",
                password: ""
            },

            baseConfig
        };
    },

        methods: {
            loginMethod() {
                userApi.login(this.login).then((data) => {
                    user.set(data);
                    window.location.href = pageConfig.home;
                }).catch((m) => {
                    alterUtil.error(m);
                });
            }
        },

        created() {
            window.addEventListener("keydown", (e) => {
                if (e.key === "Enter") {
                    this.loginMethod();
                }
            });
        }
}
</script>