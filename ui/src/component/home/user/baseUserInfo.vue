<template>
    <div style="padding: 0 25%;">
        <ep-card layout="contents">
            <div slot="contents">
                <div class="title">
                    基础信息
                </div>

                <div class="font" style="margin-top: 50px;">
                    <div>
                        用户名<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="user.data.username" size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        年龄
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="user.data.age" size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        电话<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="user.data.telephoneNum" size="small"></ep-input>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        性别<span style="color: red;margin-left: 10px;">*</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-radio v-model="user.data.sex" class="radio" label="男">男</ep-radio>
                        <ep-radio v-model="user.data.sex" class="radio" label="女">女</ep-radio>
                    </div>
                </div>

                <div class="font" style="margin-top: 20px;">
                    <div>
                        地址
                    </div>
                    <div style="margin-top: 10px;">
                        <ep-input v-model="user.data.address" size="small"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <div>
                        <ep-button @click="saveUser()" style="width: 100%;" size="small" type="primary">保存</ep-button>
                    </div>
                </div>
            </div>
        </ep-card>
    </div>
</template>

<script>
    import userManageApi from "../../../api/userManageApi.js";
    import ObjectUtil from "../../../util/ObjectUtil.js";
    import alter from "../../../util/alter";

    export default {
        name: "baseUserInfo",

        data() {
            return {
                user: {
                    data: {
                        username: null,
                        telephoneNum: null,
                        sex: null,
                        age: null,
                        address: null
                    }
                }
            };
        },

        methods: {
            init() {
                this.getUserInfo();
            },

            getUserInfo() {
                let userData = this.user.data;

                userManageApi.showUser().then((data) => {
                    ObjectUtil.extend(userData, data);
                });
            },

            saveUser() {
                let userData = this.user.data;
                let age = userData.age;

                if (isNaN(Number(age))) {
                    alter.error("年龄错误");
                    return;
                }

                userManageApi.editUser(userData).then(() => {
                    this.getUserInfo();
                    this.$parent.refreshUser();
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