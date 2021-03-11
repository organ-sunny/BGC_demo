<template>
    <div style="width: 100%;height: 100%;display: flex;">
        <div style="width: 13%;height: 100%;border-right: 1px solid #e6e6e6;background-color: white;">
            <div style="padding: 20px;" class="title">
                系统配置
            </div>

            <div style="padding-left: 40px;">
                <ep-menu :default-index="tab" class="ep-menu-demo-vertical" theme="light" style="">
                    <ep-menu-item @click="tab = 'project'" index="project" icon="navicon-round">项目管理</ep-menu-item>
                    <ep-menu-item @click="tab = 'user'" index="user" icon="person">用户管理</ep-menu-item>
                    <ep-menu-item @click="tab = 'setting'" index="setting" icon="gear-a">设置</ep-menu-item>
                </ep-menu>
            </div>
        </div>

        <div style="width: 87%;padding: 20px;overflow: auto;">
            <div v-if="tab === 'project'">
                <card head="项目管理">
                    <div>
                        <ep-row :gutter="10">
                            <ep-col :col="3">
                                <ep-input :size="baseConfig.size" v-model="project.query.objectSystem" placeholder="项目名"></ep-input>
                            </ep-col>
                            <ep-col :col="3">
                                <ep-button :size="baseConfig.size" @click="getProject()" type="primary">查询</ep-button>
                                <ep-button :size="baseConfig.size" @click="popup.addProject.open()">创建项目</ep-button>
                            </ep-col>
                        </ep-row>
                    </div>

                    <ep-divider></ep-divider>

                    <div>
                        <ep-table :size="baseConfig.size" :data="project.data" highlight-row @row-click="(e, data) => {popup.module.open(data.id, data.objectSystem)}" :height="500">
                            <ep-table-item column="objectSystem" title="项目名"></ep-table-item>
                            <ep-table-item column="creator" title="创建人"></ep-table-item>
                            <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                            <ep-table-item column="updatedBy" title="更新人"></ep-table-item>
                            <ep-table-item column="updatedTime" title="更新时间"></ep-table-item>
                        </ep-table>
                    </div>
                </card>

<!--                <card v-if="project.select !== null" head="模块" style="margin-top: 50px;">-->
<!--                    <div>-->
<!--                        <ep-button :size="baseConfig.size">新增模块</ep-button>-->
<!--                    </div>-->

<!--                    <ep-divider></ep-divider>-->

<!--                    <div>-->
<!--                        <ep-table :size="baseConfig.size" :data="module.data" :height="500">-->
<!--                            <ep-table-item column="moduleName" title="模块名"></ep-table-item>-->
<!--                            <ep-table-item column="createdTime" title="创建时间"></ep-table-item>-->
<!--                        </ep-table>-->
<!--                    </div>-->
<!--                </card>-->
            </div>

            <div v-if="tab === 'user'">
                用户管理
            </div>

            <div v-if="tab === 'setting'">
                设置
            </div>
        </div>

        <!-- 创建项目弹框 -->
        <ep-modal title="创建项目" v-model="popup.addProject.show" width="300px">
            <div>
                <div style="display: flex;">
                    <div style="width: 70px;display: flex;align-items: center;">
                        项目名
                    </div>
                    <div style="width: 100%;">
                        <ep-input :size="baseConfig.size" v-model="project.add.objectSystem"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button :size="baseConfig.size" @click="popup.addProject.ok()" style="width: 100%;" type="primary">查询</ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 模块弹框 -->
        <ep-modal :title="popup.module.projectName" v-model="popup.module.show" width="800px">
            <div>
                <div>
                    <ep-button @click="popup.addModule.open()" :size="baseConfig.size">新增模块</ep-button>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :size="baseConfig.size" :data="module.data" :height="500">
                        <ep-table-item column="moduleName" title="模块名"></ep-table-item>
                        <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                    </ep-table>
                </div>
            </div>
        </ep-modal>

        <!-- 添加模块弹框 -->
        <ep-modal title="添加模块" v-model="popup.addModule.show" width="300px">
            <div>
                <div style="display: flex;">
                    <div style="width: 70px;display: flex;align-items: center;">
                        模块名
                    </div>
                    <div style="width: 100%;">
                        <ep-input :size="baseConfig.size" v-model="module.add.moduleName"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="popup.addModule.ok()" :size="baseConfig.size" style="width: 100%;" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import baseConfig from "../../config/baseConfig.js";
    import card from "../component/card.vue";
    import objectSystemApi from "../../api/objectSystemApi.js";
    import alterUtil from "../../util/alterUtil.js";
    import objectModuleApi from "../../api/objectModuleApi.js";
    import variableUtil from "../../util/variableUtil.js";

    export default {
        name: "home_system.vue",

        data() {
            let current = this;

            return {
                tab: "project",

                project: {
                    query: {
                        objectSystem: ""
                    },

                    add: {
                        objectSystem: ""
                    },

                    data: []
                },

                // 模块数据
                module: {
                    query: {
                        objsystemId: null,
                    },

                    add: {
                        objsystemId: "",
                        moduleName: ""
                    },

                    data: []
                },

                popup: {
                    addProject: {
                        show: false,

                        open() {
                            this.show = true;
                            for (let key in current.project.add) {
                                if (current.project.add.hasOwnProperty(key)) {
                                    current.project.add[key] = "";
                                }
                            }
                        },

                        ok() {
                            let add = current.project.add;

                            if (variableUtil.isEmpty(add.objectSystem)) {
                                alterUtil.info("请输入项目名");
                                return;
                            }

                            current.addProject().then(() => {
                                alterUtil.success("完成");
                                current.init();
                            }).catch((m) => {
                                alterUtil.error(m);
                            });
                        }
                    },

                    module: {
                        show: false,

                        projectName: null,

                        open(projectId, projectName) {
                            this.show = true;
                            this.projectName = projectName;
                            current.module.query.objsystemId = projectId;
                            current.getModule();
                        }
                    },

                    addModule: {
                        show: false,

                        open() {
                            current.module.add.objsystemId = current.module.query.objsystemId;
                            this.show = true;
                        },

                        ok() {
                            current.addModule().then(() => {
                                alterUtil.success("完成");
                                current.getModule();
                            }).catch((m) => {
                                alterUtil.error(m);
                            });
                        }
                    }
                },

                baseConfig
            };
        },

        methods: {
            init() {
                this.getProject();
            },

            getProject() {
                objectSystemApi.query(this.project.query).then((data) => {
                    this.project.select = null;
                    this.project.data = data;
                });
            },

            async addProject() {
                return await objectSystemApi.add(this.project.add).catch((m) => {
                    return Promise.reject(m);
                });
            },

            getModule() {
                this.module.data = [];
                objectModuleApi.query(this.module.query).then((data) => {
                    this.module.data = data;
                });
            },

            async addModule() {
                return await objectModuleApi.add(this.module.add).catch((m) => {
                    return Promise.reject(m);
                });
            }
        },

        created() {
            this.init();
        },

        components: {
            card
        }
    }
</script>