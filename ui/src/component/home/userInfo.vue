<template>
    <div style="padding: 0 25%;">
        <ep-card v-loading="loading" layout="contents">
            <div slot="contents">
                <el-tabs v-model="tab">
                    <el-tab-pane label="基础信息维护" name="1">
                        <div class="font" style="margin-top: 20px;">
                            <div>
                                用户名<span style="color: red;margin-left: 10px;">*</span>
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input size="small" v-model="user.data.username"></ep-input>
                            </div>
                        </div>

                        <div class="font" style="margin-top: 20px;">
                            <div>
                                年龄
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input size="small" v-model="user.data.age"></ep-input>
                            </div>
                        </div>

                        <div class="font" style="margin-top: 20px;">
                            <div>
                                电话<span style="color: red;margin-left: 10px;">*</span>
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input size="small" v-model="user.data.telephoneNum"></ep-input>
                            </div>
                        </div>

                        <div class="font" style="margin-top: 20px;">
                            <div>
                                性别<span style="color: red;margin-left: 10px;">*</span>
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-radio class="radio" v-model="user.data.sex" label="男">男</ep-radio>
                                <ep-radio class="radio" v-model="user.data.sex" label="女">女</ep-radio>
                            </div>
                        </div>

                        <div class="font" style="margin-top: 20px;">
                            <div>
                                地址
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input size="small" v-model="user.data.address"></ep-input>
                            </div>
                        </div>

                        <div style="margin-top: 20px;">
                            <div>
                                <ep-button @click="user.save()" style="width: 100%;" size="small" type="primary">保存</ep-button>
                            </div>
                        </div>
                    </el-tab-pane>

                    <el-tab-pane label="安全中心" name="2">
                        <div class="panel" style="margin-top: 20px;">
                            <div class="title" style="font-size: 16px;">
                                修改密码
                            </div>

                            <div class="font" style="margin-top: 20px;">
                                <div>
                                    旧密码<span style="color: red;margin-left: 10px;">*</span>
                                </div>
                                <div style="margin-top: 10px;">
                                    <ep-input type="password" show-password v-model="security.password.password" size="small"></ep-input>
                                </div>
                            </div>

                            <div class="font" style="margin-top: 20px;">
                                <div>
                                    新密码<span style="color: red;margin-left: 10px;">*</span>
                                </div>
                                <div style="margin-top: 10px;">
                                    <ep-input type="password" show-password v-model="security.password.newPassword" size="small"></ep-input>
                                </div>
                            </div>

                            <div class="font" style="margin-top: 20px;">
                                <div>
                                    再次确认密码<span style="color: red;margin-left: 10px;">*</span>
                                </div>
                                <div style="margin-top: 10px;">
                                    <ep-input type="password" show-password v-model="security.password.newPassword2" size="small"></ep-input>
                                </div>
                            </div>

                            <div style="margin-top: 20px;">
                                <div>
                                    <ep-button @click="security.updatePassword()" style="width: 100%;" size="small" type="primary">确定</ep-button>
                                </div>
                            </div>
                        </div>

                        <div class="panel" style="margin-top: 50px;">
                            <div class="title" style="font-size: 16px;">
                                修改邮箱
                            </div>

                            <div class="font" style="margin-top: 20px;">
                                <div>
                                    邮箱地址<span style="color: red;margin-left: 10px;">*</span>
                                </div>
                                <div style="margin-top: 10px;">
                                    <ep-input v-model="security.email.email" size="small"></ep-input>
                                </div>
                            </div>

                            <div class="font" style="margin-top: 20px;">
                                <div>
                                    验证码<span style="color: red;margin-left: 10px;">*</span>
                                </div>
                                <div style="display: flex;">
                                    <ep-input v-model="security.email.mailCode" size="small"></ep-input>
                                    <ep-button @click="security.sendMailCode()" size="small">获取验证码</ep-button>
                                </div>
                            </div>

                            <div style="margin-top: 20px;">
                                <div>
                                    <ep-button @click="security.updateEmail()" style="width: 100%;" size="small" type="primary">确定</ep-button>
                                </div>
                            </div>
                        </div>

                        <div style="margin-top: 50px;">
                            <ep-button @click="security.deleteUser()" style="width: 100%;" size="small" type="danger">注销</ep-button>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </ep-card>
    </div>
</template>

<script>
    import userManageApi from "../../api/userManageApi.js";
    import alter from "../../util/alter.js";
    import token from "../../js/token.js";
    import user from "../../js/user.js";
    import pageConfig from "../../config/pageConfig.js";
    import ObjectUtil from "../../util/ObjectUtil.js";
    import userApi from "../../api/userApi.js";
    import securityCenterApi from "../../api/securityCenterApi.js";

    export default {
        name: "homeUserInfo",

        data() {
            let current = this;

            return {
                loading: false,

                tab: "1",

                user: {
                    data: {
                        username: null,
                        telephoneNum: null,
                        sex: null,
                        age: null,
                        address: null
                    },

                    getUser() {
                        userManageApi.showUser().then((data) => {
                            ObjectUtil.extend(this.data, data);
                        });
                    },

                    save() {
                        let age = this.data.age;
                        if (isNaN(Number(age))) {
                            alter.error("年龄错误");
                            return;
                        }

                        userManageApi.editUser(this.data).then(() => {
                            this.getUser();
                            alter.success("成功");
                        }).catch((message) => {
                            alter.error(message);
                        });
                    }
                },

                security: {
                    password: {
                        password: "",
                        newPassword: "",
                        newPassword2: ""
                    },

                    email: {
                        email: "",
                        mailCode: ""
                    },

                    deleteUser() {
                        alter.confirm("注销后用户将被删除，请确认").then(() => {
                            userManageApi.deleteUser().then(() => {
                                token.delete();
                                user.delete();
                                window.location.href = pageConfig.index;
                            });
                        });
                    },

                    updatePassword() {
                        let password = this.password.password;
                        let newPassword = this.password.newPassword;
                        let newPassword2 = this.password.newPassword2;

                        if (newPassword !== newPassword2) {
                            alter.error("两次密码输入不一致");
                            return;
                        }
                        securityCenterApi.updatePassword({
                            password,newPassword
                        }).then(() => {
                            alter.success("成功");
                            window.location.href = pageConfig.index;
                        }).catch((message) => {
                            alter.error(message);
                        });
                    },

                    updateEmail() {
                        let code = this.email.mailCode;
                        if (code.length > 6) {
                            alter.error("验证码最长为6位");
                            return;
                        }
                        if (isNaN(Number(code))) {
                            alter.error("验证码只能为数字");
                            return;
                        }

                        securityCenterApi.updateEmail(this.email).then(() => {
                            alter.success("成功");
                        }).catch((message) => {
                            alter.error(message);
                        });
                    },

                    sendMailCode() {
                        let mailAddress = this.email.email;
                        if (mailAddress === "" || mailAddress === null) {
                            alter.error("请输入邮箱地址");
                            return;
                        }
                        current.loading = true;
                        userApi.sendMailCode({
                            mailAddress
                        }).then(() => {
                            alter.success("邮件发送成功，注意查收");
                        }).catch((message) => {
                            alter.error(message);
                        }).finally(() => {
                            current.loading = false;
                        });
                    }
                }
            };
        },

        methods: {
            init() {
                this.user.getUser();
            }
        },

        created() {
            this.init();
        }
    }
</script>