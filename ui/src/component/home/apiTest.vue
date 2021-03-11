<template>
    <div @click="isSelectProject = false" style="width: 100%;height: 100%;display: flex;">
        <!-- 左侧 -->
        <div style="width: 13%;border-right: 1px solid #DCDFE6;overflow: auto;" class="scrollbar">
            <!-- 项目选择 -->
            <div>
                <div class="select" @click.stop="isSelectProject = !isSelectProject;getProject()" style="display: flex;height: 100%;align-items: center;padding: 20px;border-bottom: 1px solid #DCDFE6;">
                    <div style="white-space: nowrap;" class="title">
                        <span v-if="variableUtil.isEmpty(project.select.objectSystem)">
                            请选择项目
                        </span>
                        <span v-else>
                            项目：{{project.select.objectSystem}}
                        </span>
                    </div>
                    <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                        <i v-if="!isSelectProject" class="ion-chevron-right"></i>
                        <i v-if="isSelectProject" class="ion-chevron-down"></i>
                    </div>
                </div>

                <!-- 下拉框 -->
                <div style="position: relative;width: 100%;">
                    <div v-if="isSelectProject" class="ep-drop-wrap ep-dropdown-menu scrollbar" style="position: absolute;width: 100%;">
                        <ul>
                            <li @click="project.select = item;allModuleIsShow = false;module.select = {};" v-for="(item, key) in project.data" :key="key" class="ep-dropdown-menu-item">{{item.objectSystem}}</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- 所有模块 -->
            <div @click="allModuleIsShow = !allModuleIsShow;allModuleIsShow ? getModule() : ''" style="display: flex;padding: 10px 20px;cursor: pointer;" class="select">
                <div>
                    <i class="ion-android-folder-open"></i>
                </div>
                <div style="white-space: nowrap;margin-left: 10px;font-weight: bold;">
                    所有模块
                </div>
                <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                    <i v-if="!allModuleIsShow" class="ion-chevron-right"></i>
                    <i v-else class="ion-chevron-down"></i>
                </div>
            </div>

            <!-- 子模块集合 -->
            <div v-if="allModuleIsShow" style="margin-left: 20px;">
                <div v-for="(item, key) in module.data" :key="key">
                    <div @click="module.select = item" style="display: flex;padding: 10px 20px;" class="select" :class="module.select.id === item.id ? 'action' : ''">
                        <div>
                            <i class="ion-navicon-round" style="margin-right: 10px;"></i>
                        </div>
                        <div style="white-space: nowrap;margin-left: 10px;">
                            {{item.moduleName}}
                        </div>
                        <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                            <i v-if="!item._apiIsShow" @click.stop="item._apiIsShow = true;loadApi(key)" class="ion-chevron-right icon"></i>
                            <i @click="item._apiIsShow = false;$forceUpdate()" v-else class="ion-chevron-down icon"></i>
                        </div>
                    </div>

                    <!-- api集合 -->
                    <div v-if="item._apiIsShow" style="margin-left: 20px;">
                        <div v-for="(api, k) in item._apiList" :key="k" style="padding: 10px 20px;" class="select">
                            <i class="ion-information" style="margin-right: 10px;"></i>
                            {{api.apiName}}
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧 -->
        <div style="width: 87%;">
            123
        </div>
    </div>
</template>

<script>
    import objectSystemApi from "../../api/objectSystemApi.js";
    import variableUtil from "../../util/variableUtil.js";
    import objectModuleApi from "../../api/objectModuleApi.js";
    import objectApiApi from "../../api/objectApiApi.js";

    export default {
        name: "home_apiTest.vue",

        data() {
            return {
                // 是否显示选择项目下拉框
                isSelectProject: false,

                // 所有模块是否展开
                allModuleIsShow: false,

                // 项目
                project: {
                    // 选择的项目
                    select: {},

                    // 所有数据
                    data: []
                },

                // 模块
                module: {
                    // 当前选择的模块
                    select: {},

                    // 所有数据
                    data: []
                },

                variableUtil
            };
        },

        methods: {
            init() {

            },

            getProject() {
                objectSystemApi.query().then((data) => {
                    this.project.data = data;
                });
            },

            getModule() {
                this.module.data = [];
                if (variableUtil.isEmpty(this.project.select.id)) {
                    return;
                }
                objectModuleApi.query({
                    objsystemId: this.project.select.id
                }).then((data) => {
                    this.module.data = data;
                    for (let item of this.module.data) {
                        item._apiIsShow = false;
                        item._apiList = [];
                    }
                });
            },

            loadApi(moduleIndex) {
                let moduleId = this.module.data[moduleIndex].id;

                objectApiApi.query({
                    moduleId
                }).then((data) => {
                    this.module.data[moduleIndex]._apiList = data;
                    this.$forceUpdate();
                });
            }
        },

        created() {
            this.init();
        }
    }
</script>

<style scoped>
    .select {
        cursor: pointer;
        transition: 0.1s;
    }
    .select.action {
        background-color: #e6e6e6;
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

    .icon {
        transition: 0.1s;
    }
    .icon:hover {
        color: #409EFF;
    }
</style>