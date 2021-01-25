<template>
    <div style="padding: 0 30%;">
        <ep-card layout="contents">
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
                    <div style="margin-top: 10px;">
                        <ep-input size="small" v-model="user.email"></ep-input>
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
                        <ep-button style="width: 100%;" size="small" type="primary">保存</ep-button>
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

    export default {
        name: "homeUserInfo",

        data() {
            return {
                user: {
                    username: "",
                    telephoneNum: "",
                    sex: "",
                    email: "",
                    age: "",
                    address: ""
                }
            };
        },

        methods: {
            getUser() {
                userManageApi.showUser().then((data) => {
                    this.user = data;
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
            }
        },

        created() {
            this.getUser();
        }
    }
</script>