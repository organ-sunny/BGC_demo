<template>
    <div style="width: 100%;height: 100%;background-color: #00a0e9;">
        <div style="height: 7%;background-color: rgba(0,0,0,.1);font-weight: bold;color: white;font-size: 18px;" class="center">
            BGC_demo
        </div>
        <div style="display: flex;height: 93%;">
            <div style="width: 50%;" class="center">
                <img src="../img/login.png"  alt="登录" />
            </div>
            <div style="width: 50%;" class="center">
                <ep-card layout="contents" style="width: 350px;">
                    <div slot="contents">
                        <div style="font-size: 18px;font-weight: bold;color: #2296F3;">
                            登录
                        </div>

                        <div style="margin-top: 20px;display: flex;">
                            <div style="width: 20%;display: flex;align-items: center;flex-direction: row-reverse;">
                                用户名
                            </div>
                            <ep-input v-model="username" style="margin-left: 10px;" placeholder="请输入内容"></ep-input>
                        </div>

                        <div style="margin-top: 20px;display: flex;">
                            <div style="width: 20%;display: flex;align-items: center;flex-direction: row-reverse;">
                                密码
                            </div>
                            <ep-input v-model="password" type="password" style="margin-left: 10px;" placeholder="请输入内容"></ep-input>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                <ep-button @click="login()" style="width: 100%;" type="primary">登录</ep-button>
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-button style="width: 100%;">注册</ep-button>
                            </div>
                        </div>
                    </div>
                </ep-card>
            </div>
        </div>
    </div>
</template>

<script>
    import userApi from "../api/userApi.js";
    import alter from "../util/alter.js";

    export default {
        name: "login",

        data() {
            return {
                username: "",
                password: ""
            };
        },

        methods: {
            login() {
                userApi.login({
                    username: this.username,
                    password: this.password
                }).then(() => {
                    alter.success("登录成功");
                }).catch((message) => {
                    alter.error(message);
                });
            }
        }
    }
</script>