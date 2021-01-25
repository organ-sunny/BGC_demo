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
                <ep-card v-if="isLogin" layout="contents" style="width: 350px;">
                    <div slot="contents">
                        <div style="font-size: 18px;font-weight: bold;color: #2296F3;">
                            登录
                        </div>

                        <div style="margin-top: 20px;display: flex;">
                            <div style="width: 20%;display: flex;align-items: center;flex-direction: row-reverse;">
                                用户名
                            </div>
                            <ep-input v-model="login.form.username" style="margin-left: 10px;" placeholder=""></ep-input>
                        </div>

                        <div style="margin-top: 20px;display: flex;">
                            <div style="width: 20%;display: flex;align-items: center;flex-direction: row-reverse;">
                                密码
                            </div>
                            <ep-input v-model="login.form.password" type="password" style="margin-left: 10px;" placeholder=""></ep-input>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                <ep-button @click="loginMethod()" style="width: 100%;" type="primary">登录</ep-button>
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-button @click="isLogin = false;" style="width: 100%;">注册</ep-button>
                            </div>
                        </div>
                    </div>
                </ep-card>

                <ep-card v-loading="register.loading" v-if="!isLogin" layout="contents" style="width: 350px;">
                    <div slot="contents">
                        <div style="font-size: 18px;font-weight: bold;color: #2296F3;">
                            注册
                        </div>
                        <div style="margin-top: 20px;">
                            <div>
<!--                                <ep-radio v-model="register.form.type" class="type" label="1">用户名</ep-radio>-->
<!--                                <ep-radio v-model="register.form.type" class="type" label="2">电话</ep-radio>-->
<!--                                <ep-radio v-model="register.form.type" class="type" label="3">邮箱</ep-radio>-->
                                用户名/电话/邮箱
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input @change="usernameChange()" v-model="register.form.username" placeholder=""></ep-input>
                            </div>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                密码
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input type="password" v-model="register.form.password" placeholder=""></ep-input>
                            </div>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                确认密码
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input type="password" v-model="register.form.password2" placeholder=""></ep-input>
                            </div>
                        </div>

                        <div v-if="register.isMail" style="margin-top: 20px;">
                            <div>
                                验证码
                            </div>
                            <div style="margin-top: 10px;display: flex;">
                                <ep-input v-model="register.form.mailCode" placeholder=""></ep-input>
                                <ep-button @click="getCode()">获取验证码</ep-button>
                            </div>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                <div>
                                    <ep-button @click="registerMethod()" style="width: 100%;" type="primary">注册</ep-button>
                                </div>
                                <div style="margin-top: 10px;">
                                    <ep-button @click="isLogin = true;" style="width: 100%;">返回登录</ep-button>
                                </div>
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
    import token from "../js/token.js";
    import user from "../js/user.js";
    import pageConfig from "../config/pageConfig.js";

    export default {
        name: "login",

        data() {
            return {
                isLogin: true,

                login: {
                    form: {
                        username: "",
                        password: ""
                    }
                },

                register: {
                    loading: false,

                    form: {
                        username: "",
                        password: "",
                        password2: "",
                        mailCode: ""
                    },

                    isMail: false
                }
            };
        },

        methods: {
            loginMethod() {
                userApi.login(this.login.form).then((data) => {
                    // 设置token
                    token.set(data.token);
                    // 设置用户信息
                    user.set(data);

                    window.location.href = pageConfig.home;
                }).catch((message) => {
                    alter.error(message);
                });
            },

            registerMethod() {
                let form = this.register.form;
                if (form.password !== form.password2) {
                    alter.error("两次输入的密码不一致");
                    return;
                }
                this.register.loading = true;
                userApi.register({
                    username: form.username,
                    password: form.password,
                    mailCode: form.mailCode
                }).then(() => {
                    alter.success("注册成功");
                }).catch((message) => {
                    alter.error(message);
                }).finally(() => {
                    this.register.loading = false;
                });
            },

            getCode() {
                let mailAddress = this.register.form.username;
                if (mailAddress === "") {
                    alter.error("请输入邮箱号");
                    return;
                }
                this.register.loading = true;
                userApi.sendMailCode({
                    mailAddress
                }).then(() => {
                    alter.success("邮件发送成功，注意查收");
                }).catch((message) => {
                    alter.error(message);
                }).finally(() => {
                    this.register.loading = false;
                });
            },

            usernameChange() {
                let reg =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                let re = new RegExp(reg);
                this.register.isMail = re.test(this.register.form.username);
            }
        }
    }
</script>