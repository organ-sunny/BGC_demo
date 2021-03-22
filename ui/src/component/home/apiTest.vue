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
                            <li @click="switchProject(item)" v-for="(item, key) in project.data" :key="key" class="ep-dropdown-menu-item">{{item.objectSystem}}</li>
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
                <module-item
                    :action-id="module.select.id"
                    @click="(moduleItem) => {
                        module.select = item;
                        tab = 'module';
                        getApi();
                        moduleItemVue = moduleItem;
                        selectApi = {};
                    }"
                    v-for="(item, key) in module.data"
                    :key="key"
                    :module="item"
                    @apiClick="(moduleItem, apiItem) => {
                        module.select = moduleItem;
                        selectApi = apiItem;
                        tab = 'api';
                        getTestcase();
                    }" :api-action-id="selectApi.id">
                </module-item>
            </div>
        </div>

        <!-- 右侧 -->
        <div style="width: 87%;padding: 20px;">
            <!-- module -->
            <div v-if="tab === 'module'">
                <div>
                    <card :head="module.select.moduleName">
                        <div>
                            <ep-button @click="addApiPopup.open()" :size="baseConfig.size">新增api</ep-button>
                        </div>

                        <div style="margin-top: 20px;">
                            <ep-table :size="baseConfig.size" :data="api.data" :height="500">
                                <ep-table-item column="apiName" title="api名称"></ep-table-item>
                                <ep-table-item column="apiAddress" title="请求地址"></ep-table-item>
                                <ep-table-item column="apiMethod" title="请求方式">
                                    <template slot-scope="props">
                                        <ep-tag v-if="props.row.apiMethod === 'GET'" type="success" size="small">GET</ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'POST'" type="primary" size="small">POST</ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'PUT'" type="warning" size="small">PUT</ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'DELETE'" type="danger" size="small">DELETE</ep-tag>
                                    </template>
                                </ep-table-item>
                                <ep-table-item column="apiName" title="api名称"></ep-table-item>
                                <ep-table-item column="action" title="操作">
                                    <template slot-scope="props">
                                        <ep-button disabled :size="baseConfig.size">编辑</ep-button>
                                        <ep-button disabled :size="baseConfig.size" type="danger">删除</ep-button>
                                    </template>
                                </ep-table-item>
                            </ep-table>
                        </div>
                    </card>
                </div>
            </div>

            <!-- api -->
            <div v-if="tab === 'api'">
                <card :head="module.select.moduleName + ' - ' + selectApi.apiName">
                    <ep-table :size="baseConfig.size" :data="testcase.data" :height="500">
                        <ep-table-item column="apiCaseName" title="用例名"></ep-table-item>
                    </ep-table>
                </card>
            </div>
        </div>

        <!-- 新增api弹框 -->
        <ep-modal title="新增api" v-model="addApiPopup.show" width="500px">
            <div>
                <div style="display: flex;">
                    <div style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        名称
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.add.apiName" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        请求地址
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.add.apiAddress" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        请求方式
                    </div>
                    <div style="width: 100%;">
                        <ep-select :size="baseConfig.size" placeholder="请选择" v-model="api.add.apiMethod">
                            <ep-select-item index="GET" label="GET">
                                <ep-tag type="success" size="small">GET</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="POST" label="POST">
                                <ep-tag type="primary" size="small">POST</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="PUT" label="PUT">
                                <ep-tag type="warning" size="small">PUT</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="DELETE" label="DELETE">
                                <ep-tag type="danger" size="small">DELETE</ep-tag>
                            </ep-select-item>
                        </ep-select>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="addApiPopup.ok()" :size="baseConfig.size" style="width: 100%;" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import objectSystemApi from "../../api/objectSystemApi.js";
    import variableUtil from "../../util/variableUtil.js";
    import objectModuleApi from "../../api/objectModuleApi.js";
    import moduleItem from "../component/moduleItem.vue";
    import card from "../component/card.vue";
    import objectApiApi from "../../api/objectApiApi.js";
    import baseConfig from "../../config/baseConfig.js";
    import alterUtil from "../../util/alterUtil.js";
    import apiTestCaseApi from "../../api/apiTestCaseApi.js";

    export default {
        name: "home_apiTest.vue",

        data() {
            let current = this;

            return {
                // 是否显示选择项目下拉框
                isSelectProject: false,

                // 所有模块是否展开
                allModuleIsShow: false,

                // 右侧的tab
                tab: "",

                // 选择的模块vue
                moduleItemVue: null,

                // 选择的api
                selectApi: {},

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

                // api
                api: {
                    // 添加
                    add: {
                        apiName: "",
                        apiAddress: "",
                        apiMethod: ""
                    },

                    // 数据
                    data: []
                },

                // testcase
                testcase: {
                    data: []
                },

                // 新增api弹框
                addApiPopup: {
                    show: false,

                    open() {
                        this.show = true;
                    },

                    ok() {
                        current.addApi().then(() => {
                            alterUtil.success("完成");
                            current.getApi();
                            current.moduleItemVue.getApi();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                },

                variableUtil,

                baseConfig
            };
        },

        methods: {
            init() {},

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
                });
            },

            getApi() {
                objectApiApi.query({
                    moduleId: this.module.select.id
                }).then((data) => {
                    this.api.data = data;
                });
            },

            async addApi() {
                let req = {
                    moduleId: "",
                    apiName: "",
                    apiAddress: "",
                    apiMethod: ""
                };

                req.moduleId = this.module.select.id;
                variableUtil.extend(req, this.api.add);

                return await objectApiApi.add(req).catch((m) => {
                    return Promise.reject(m);
                });
            },

            getTestcase() {
                let req = {
                    apiId: this.selectApi.id
                };

                apiTestCaseApi.query(req).then((data) => {
                    this.testcase.data = data;
                });
            },

            // 切换项目
            switchProject(project) {
                this.project.select = project;
                this.allModuleIsShow = false;
                this.tab = "";
            }
        },

        created() {
            this.init();
        },

        components: {
            moduleItem,
            card
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
</style>