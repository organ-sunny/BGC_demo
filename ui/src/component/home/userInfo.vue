<template>
    <div style="padding: 0 30%;">
        <ep-card v-loading="loading" layout="contents">
            <div slot="contents">
                <div class="title">
                    用户信息
                </div>

                <div class="font" style="margin-top: 50px;">
                    <div>
                        用户名
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.username"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        密码
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input type="password" size="small" v-model="user.password"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        年龄
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.age"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        电话
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.telephoneNum"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        性别
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-radio class="radio" v-model="user.sex" label="男">男</ep-radio>
                        <ep-radio class="radio" v-model="user.sex" label="女">女</ep-radio>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        电子邮件
                    </div>
                    <div style="margin-top: 10px;display: flex;">
                        <ep-input size="small" v-model="user.email"></ep-input>
                        <ep-button @click="sendMailCode()" size="small">获取验证码</ep-button>
                    </div>
                </div>

                <div v-if="user.email !== null && user.email !== ''" class="font" style="margin-top: 20px;">
                    <div>
                        验证码
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.mailCode"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        地址
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.address"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 50px;">
                    <div>
                        <ep-button @click="save()" style="width: 100%;" size="small" type="primary">保存</ep-button>
                    </div>
                    <div style="margin-top: 20px;">
                        <ep-button @click="deleteUser()" style="width: 100%;" size="small" type="danger">注销</ep-button>
                    </div>
                </div>
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

    export default {
        name: "homeUserInfo",

        data() {
            return {
                loading: false,

                user: {
                    username: null,
                    telephoneNum: null,
                    sex: null,
                    email: null,
                    age: null,
                    address: null,
                    password: null,
                    mailCode: null
                }
            };
        },

        methods: {
            getUser() {
                userManageApi.showUser().then((data) => {
                    ObjectUtil.extend(this.user, data);
                });
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

            save() {
                userManageApi.editUser(this.user).then(() => {
                    this.getUser();
                    alter.success("成功");
                }).catch((message) => {
                    alter.error(message);
                });
            },

            sendMailCode() {
                let mailAddress = this.user.email;
                if (mailAddress === "" || mailAddress === null) {
                    alter.error("请输入邮箱号");
                    return;
                }
                this.loading = true;
                userApi.sendMailCode({
                    mailAddress
                }).then(() => {
                    alter.success("邮件发送成功，注意查收");
                }).catch((message) => {
                    alter.error(message);
                }).finally(() => {
                    this.loading = false;
                });
            }
        },

        created() {
            this.getUser();
        }
    }
</script>