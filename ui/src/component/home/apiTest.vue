<template>
    <div @click="isSelectProject = false" style="width: 100%;height: 100%;">
        <!-- 最上面一条 -->
        <div style="height: 40px;border-bottom: 1px solid #e6e6e6;display: flex;">
            <!-- 项目选择 -->
            <div class="select" style="width: 13%;">
                <div @click.stop="isSelectProject = !isSelectProject;" style="display: flex;height: 100%;align-items: center;padding: 0 20px;">
                    <div style="white-space: nowrap;" class="font">
                        项目：项目1
                    </div>
                    <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                        <i v-if="!isSelectProject" class="ion-chevron-down"></i>
                        <i v-if="isSelectProject" class="ion-chevron-up"></i>
                    </div>
                </div>
                <div v-if="isSelectProject" class="ep-drop-wrap ep-dropdown-menu" style="position: relative;">
                    <ul>
                        <li class="ep-dropdown-menu-item">进口换单业务</li>
                        <li class="ep-dropdown-menu-item">进口放箱</li>
                        <li class="ep-dropdown-menu-item">进口费用(人民币)</li>
                    </ul>
                </div>
            </div>

            <div class="tab action">
                <div>
                    用例管理
                </div>
                <div></div>
            </div>
            <div class="tab">
                <div>
                    测试报告
                </div>
                <div></div>
            </div>
        </div>

        <!-- body -->
        <div style="height: calc(100% - 40px);">
            <!-- row -->
            <div style="display: flex;height: 100%;">
                <!-- 左侧 -->
                <div style="width: 13%;border-right: 1px solid #e6e6e6;padding: 10px;">
                    <!-- 所有模块 -->
                    <div style="display: flex;padding: 10px 10px;cursor: pointer;" class="select">
                        <div>
                            <i class="ion-android-folder-open"></i>
                        </div>
                        <div style="white-space: nowrap;margin-left: 10px;font-weight: bold;">
                            所有模块
                        </div>
                        <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                            <i class="ion-chevron-down"></i>
<!--                            <i class="ion-chevron-up"></i>-->
                        </div>
                    </div>

                    <!-- 子模块集合 -->
                    <div style="margin-left: 20px;">
                        <div>
                            <div style="display: flex;padding: 10px;" class="select">
                                <div>
                                    <i class="ion-navicon-round" style="margin-right: 10px;"></i>
                                </div>
                                <div style="white-space: nowrap;margin-left: 10px;">
                                    模块1
                                </div>
                                <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                                    <i class="ion-chevron-down"></i>
<!--                                    <i class="ion-chevron-up"></i>-->
                                </div>
                            </div>

                            <!-- api集合 -->
                            <div style="margin-left: 20px;">
                                <div style="padding: 10px;" class="select">
                                    <i class="ion-information" style="margin-right: 10px;"></i>
                                    api-1
                                </div>
                                <div style="padding: 10px;" class="select">
                                    <i class="ion-information" style="margin-right: 10px;"></i>
                                    api-2
                                </div>
                            </div>
                        </div>
                        <div>
                            <div style="display: flex;padding: 10px;" class="select">
                                <div>
                                    <i class="ion-navicon-round" style="margin-right: 10px;"></i>
                                </div>
                                <div style="white-space: nowrap;margin-left: 10px;">
                                    模块1
                                </div>
                                <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                                    <i class="ion-chevron-down"></i>
                                    <!--                                    <i class="ion-chevron-up"></i>-->
                                </div>
                            </div>

                            <!-- api集合 -->
                            <div style="margin-left: 20px;">
                                <div style="padding: 10px;" class="select">
                                    <i class="ion-information" style="margin-right: 10px;"></i>
                                    api-1
                                </div>
                                <div style="padding: 10px;" class="select">
                                    <i class="ion-information" style="margin-right: 10px;"></i>
                                    api-2
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 87%;padding: 20px;">
                    456
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import objectSystemApi from "../../api/objectSystemApi.js";

    export default {
        name: "home_apiTest.vue",

        data() {
            return {
                // 是否显示选择项目下拉框
                isSelectProject: false,

                // 项目
                project: {
                    data: []
                }
            };
        },

        methods: {
            getProject() {

            }
        }
    }
</script>

<style scoped>
    .select {
        cursor: pointer;
        transition: 0.1s;
    }
    .select:hover {
        background-color: #e6e6e6;
    }

    .tab {
        cursor: pointer;
    }
    .tab > div:first-child {
        height: calc(100% - 2px);
        padding: 0 20px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .tab > div:last-child {
        height: 2px;
        background-color: white;
    }
    .tab:hover > div:last-child {
        background-color: #0747a6;
    }

    .tab.action > div:last-child {
        background-color: #0747a6;
    }
</style>