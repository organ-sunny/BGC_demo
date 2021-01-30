<template>
    <div style="padding: 0 25%;">
        <ep-card layout="contents">
            <div slot="contents">
                <div style="display: flex;flex-direction: row-reverse;">
                    <ep-button @click="deleteUser()" size="small" type="danger">注销</ep-button>
                </div>
            </div>
        </ep-card>

        <ep-card layout="contents" style="margin-top: 50px;">
            <div slot="contents">
                <div class="title">
                    密码修改
                </div>

                <div class="font" style="margin-top: 50px;">
                    <div>
                        旧密码<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="password.data.password" type="password" show-password size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        新密码<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="password.data.newPassword" type="password" show-password size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        再次确认密码<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="password.data.newPassword2" type="password" show-password size="small"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <div>
                        <ep-button @click="updatePassword()" style="width: 100%;" size="small" type="primary">确定</ep-button>
                    </div>
                </div>
            </div>
        </ep-card>

        <ep-card v-loading="email.loading" layout="contents" style="margin-top: 50px;">
            <div slot="contents">
                <div class="title" style="font-size: 16px;">
                    修改邮箱
                </div>

                <div class="font" style="margin-top: 50px;">
                    <div>
                        邮箱地址<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="email.data.email" size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        验证码<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="display: flex;margin-top: 10px;">
                        <ep-input v-model="email.data.email" size="small"></ep-input>
                        <ep-button @click="sendMailCode()" size="small">获取验证码</ep-button>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <div>
                        <ep-button @click="updateEmail()" style="width: 100%;" size="small" type="primary">确定</ep-button>
                    </div>
                </div>
            </div>
        </ep-card>
    </div>
</template>

<script>
    import userManageApi from "../../../api/userManageApi.js";
    import alter from "../../../util/alter.js";
    import token from "../../../js/token.js";
    import user from "../../../js/user.js";
    import pageConfig from "../../../config/pageConfig.js";
    import securityCenterApi from "../../../api/securityCenterApi.js";
    import userApi from "../../../api/userApi.js";

    export default {
        name: "securityUserInfo",

        data() {
            return {
                password: {
                    data: {
                        password: "",
                        newPassword: "",
                        newPassword2: ""
                    }
                },

                email: {
                    loading: false,

                    data: {
                        email: "",
                        mailCode: ""
                    }
                }
            };
        },

        methods: {
            init() {},

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
                let data = this.password.data;
                let password = data.password;
                let newPassword = data.newPassword;
                let newPassword2 = data.newPassword2;

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

            sendMailCode() {
                let email = this.email;
                let emailData = email.data;
                let mailAddress = emailData.email;

                if (mailAddress === "" || mailAddress === null) {
                    alter.error("请输入邮箱地址");
                    return;
                }

                email.loading = true;
                userApi.sendMailCode({
                    mailAddress
                }).then(() => {
                    alter.success("邮件发送成功，注意查收");
                }).catch((message) => {
                    alter.error(message);
                }).finally(() => {
                    email.loading = false;
                });
            },

            updateEmail() {
                let emailData = this.email.data;
                let code = emailData.mailCode;

                if (code.length > 6) {
                    alter.error("验证码最长为6位");
                    return;
                }
                if (isNaN(Number(code))) {
                    alter.error("验证码只能为数字");
                    return;
                }

                securityCenterApi.updateEmail(emailData).then(() => {
                    alter.success("成功");
                }).catch((message) => {
                    alter.error(message);
                });
            }
        },

        created() {
            this.init();
        }
    }
</script>