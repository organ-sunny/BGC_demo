<template>
    <div style="width: 100%; height: 100%;">
        <div :style="baseConfig" style="height: 40px;display: flex;padding: 0 20px;">
            <div class="center menu-item" style="color: white;font-weight: bold;font-size: 18px;width: 7%;"
                 @click="switchMenu('/')">
                BGC_demo
            </div>
            <div style="width: 86%;display: flex;">
                <div :class="path === '/apiTest' ? 'action' : ''" @click="switchMenu('/apiTest')" class="menu-item">
                    接口测试
                </div>
                <div :class="path === '/autoTest' ? 'action' : ''" @click="switchMenu('/autoTest')" class="menu-item">
                    自动化测试
                </div>
                <div :class="path === '/system' ? 'action' : ''" @click="switchMenu('/system')" class="menu-item">
                    系统配置
                </div>
            </div>
            <div style="width: 7%;display: flex;flex-direction: row-reverse;">
                <div class="center" style="width: 100%;flex-direction: row;">
                    <div class="center" style="padding: 0 11px;color: white;">
                        {{ user.username }}
                    </div>
                    <div class="center" style="padding: 0 5px;color: white;">
                        |
                    </div>
                    <div class="center">
                        <ep-button @click="logout()" type="text" style="color: white;">退出登录</ep-button>
                    </div>
                </div>
            </div>
        </div>

        <!-- body -->
        <div style="height: calc(100% - 40px);">
            <div v-if="path === '/'" style="padding: 50px 15%;">
                <ep-row>
                    <ep-col :col="4" style="height: 100%;">
                        <div style="display: flex;padding: 0 20px;border-right: 1px solid #e6e6e6;">
                            <div>
                                <div style="line-height: 40px;font-size: 26px;font-weight: 300;margin-bottom: 10px;">
                                    测试平台
                                </div>
                                <div style="display: flex;justify-content: center;">
                                    BGC_demo
                                </div>
                            </div>
                        </div>
                    </ep-col>
                    <ep-col :col="20" style="height: 100%;">
                        <div style="height: 100%;display: flex;flex-direction: row-reverse;">
                            <div style="display: flex;">
                                <div style="flex-direction: column;padding: 0 20px;" class="center">
                                    <div
                                        style="display: flex;justify-content: center;color: #0747a6;font-size: 32px;border-bottom: 1px solid #0747a6;cursor: pointer;">
                                        12
                                    </div>
                                    <div style="margin-top: 5px;">
                                        已集成项目
                                    </div>
                                </div>

                                <div style="padding: 20px;">
                                    <div style="display: flex;">
                                        <div style="width: 30px;">
                                            <span class="num">100</span>
                                        </div>
                                        <div style="margin-left: 10px;">
                                            模块
                                        </div>
                                    </div>

                                    <div style="display: flex;margin-top: 10px;">
                                        <div style="width: 30px;">
                                            <span class="num">500</span>
                                        </div>
                                        <div style="margin-left: 10px;">
                                            API
                                        </div>
                                    </div>

                                    <div style="display: flex;margin-top: 10px;">
                                        <div style="width: 30px;">
                                            <span class="num">974</span>
                                        </div>
                                        <div style="margin-left: 10px;">
                                            用例
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ep-col>
                </ep-row>

                <ep-divider style="margin-bottom: 50px;"></ep-divider>

                <div style="padding: 0 20px;">
                    <div style="font-size: 18px;font-weight: 300;margin-bottom: 50px;">
                        接口测试
                    </div>
                    <ep-row style="display: flex;">
                        <ep-col :col="6">
                            <div style="margin-bottom: 10px;display: flex;justify-content: center;font-weight: bold;">
                                执行率
                            </div>
                            <div style="display: flex;justify-content: center;">
                                <ep-progress type="circle" :percentage="90"></ep-progress>
                            </div>
                        </ep-col>
                        <ep-col :col="6">
                            <div style="border-right: 1px solid #e6e6e6;">
                                <div
                                    style="margin-bottom: 10px;display: flex;justify-content: center;font-weight: bold;">
                                    通过率
                                </div>
                                <div style="display: flex;justify-content: center;">
                                    <ep-progress type="circle" :percentage="100" status="success"></ep-progress>
                                </div>
                            </div>
                        </ep-col>
                        <ep-col :col="4"></ep-col>
                        <ep-col :col="8" style="display: flex;flex-direction: column;justify-content: center;">
                            <div style="display: flex;margin-bottom: 10px;">
                                <div style="width: 100px;font-weight: bold;display: flex;flex-direction: row-reverse;">
                                    API
                                </div>
                                <div class="num" style="margin-left: 20px;">
                                    12
                                </div>
                            </div>

                            <div style="display: flex;margin-bottom: 50px;">
                                <div style="width: 100px;font-weight: bold;display: flex;flex-direction: row-reverse;">
                                    用例
                                </div>
                                <div class="num" style="margin-left: 20px;">
                                    120
                                </div>
                            </div>

                            <div style="display: flex;margin-bottom: 10px;">
                                <div style="width: 100px;font-weight: bold;display: flex;flex-direction: row-reverse;">
                                    上次执行时间
                                </div>
                                <div style="margin-left: 20px;">
                                    2021-01-01 22:22:22
                                </div>
                            </div>

                            <div style="display: flex;">
                                <div style="width: 100px;font-weight: bold;display: flex;flex-direction: row-reverse;">
                                    执行人
                                </div>
                                <div style="margin-left: 20px;">
                                    zhoujun
                                </div>
                            </div>
                        </ep-col>
                    </ep-row>
                </div>

                <ep-divider style="margin-top: 50px;"></ep-divider>

                <div style="margin-top: 50px;padding: 0 20px;">
                    <div style="font-size: 18px;font-weight: 300;">
                        公告
                    </div>

                    <div style="margin-top: 20px;">
                        <ep-table :data="notice.data">
                            <ep-table-item column="title" title="标题">
                                <template slot-scope="props">
                                    <div style="color: blue;text-decoration: underline;cursor: pointer;">
                                        {{ props.row.title }}
                                    </div>
                                </template>
                            </ep-table-item>
                            <ep-table-item column="createTime" title="时间"></ep-table-item>
                        </ep-table>
                    </div>
                </div>
            </div>

            <router-view v-else></router-view>
        </div>
    </div>
</template>

<script>
import baseConfig from "../config/baseConfig.js";
import user from "../js/user.js";
import variableUtil from "../util/variableUtil.js";
import pageConfig from "../config/pageConfig.js";

export default {
    name: "home.vue",

    data() {
        return {
            path: "",

            user: {
                username: ""
            },

            notice: {
                data: [
                    {
                        title: "这是公告1",
                        createTime: "2021-03-10 10:12:34"
                    },
                    {
                        title: "这是公告2",
                        createTime: "2021-03-05 10:12:34"
                    }
                ]
            },

            baseConfig
        };
    },

    methods: {
        init() {
            this.path = this.$route.path;
            variableUtil.extend(this.user, user.get());
        },

        switchMenu(path) {
            if (path === this.path) {
                return;
            }

            this.$router.push(path);

            let route = this.$route;
            let title;
            if (route.path === "/") {
                title = "首页";
            } else {
                title = route.meta.title;
            }
            document.title = title;

            this.init();
        },

        logout() {
            user.delete();
            window.location.href = pageConfig.index;
        }
    },

    created() {
        this.init();
    }
}
</script>

<style scoped>
.menu-item {
    padding: 0 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
}

.menu-item.action {
    background-color: #033681;
}

.menu-item:hover {
    background-color: #033681;
    cursor: pointer;
}

.num {
    color: #0747a6;
    border-bottom: 1px solid #0747a6;
    font-weight: bold;
}
</style>