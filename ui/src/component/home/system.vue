<template>
    <div style="width: 100%;height: 100%;display: flex;">
        <div style="width: 13%;height: 100%;border-right: 1px solid #e6e6e6;background-color: white;">
            <div style="padding: 20px;" class="title">
                系统配置
            </div>

            <div style="padding-left: 40px;">
                <ep-menu :default-index="tab" class="ep-menu-demo-vertical" theme="light">
                    <ep-menu-item style="border: none;" @click="tab = 'project'" index="project" icon="navicon-round">
                        项目管理
                    </ep-menu-item>
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
                                <ep-input :size="baseConfig.size" v-model="project.query.objectSystem"
                                          placeholder="项目名"></ep-input>
                            </ep-col>
                            <ep-col :col="3">
                                <ep-button :size="baseConfig.size" @click="getProject()" type="primary">查询</ep-button>
                                <ep-button :size="baseConfig.size" @click="popup.addProject.open()">创建项目</ep-button>
                            </ep-col>
                        </ep-row>
                    </div>

                    <ep-divider></ep-divider>

                    <div>
                        <ep-table :size="baseConfig.size" :data="project.data" highlight-row
                                  @row-click="(e, data) => {popup.module.open(data.id, data.objectSystem)}"
                                  :height="500">
                            <ep-table-item column="objectSystem" title="项目名"></ep-table-item>
                            <ep-table-item column="creator" title="创建人"></ep-table-item>
                            <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                            <ep-table-item column="updatedBy" title="更新人"></ep-table-item>
                            <ep-table-item column="updatedTime" title="更新时间"></ep-table-item>
                            <ep-table-item column="action" title="操作">
                                <template slot-scope="props">
                                    <ep-button @click.stop="popup.updateProject.open(props.row)" type="text"
                                               style="color: #E7963B;padding-left: 0;">编辑
                                    </ep-button>
                                    <ep-button @click.stop="alterUtil.confirm('确认删除?').then(() => {
                                        project.delete.idList = [];
                                        project.delete.idList.push(props.row.id);
                                        deleteProject().then(() => {
                                            alterUtil.success('已删除');
                                            getProject();
                                        }).catch((m) => {
                                            alterUtil.error(m);
                                        });
                                    })" type="text" style="color: #FF4D4F;">删除
                                    </ep-button>
                                </template>
                            </ep-table-item>
                        </ep-table>
                    </div>
                </card>
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
                    <ep-button :size="baseConfig.size" @click="popup.addProject.ok()" style="width: 100%;"
                               type="primary">确定
                    </ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 模块弹框 -->
        <ep-modal :title="popup.module.projectName" v-model="popup.module.show" width="1000px">
            <div>
                <div style="display: flex;">
                    <div>
                        <ep-input v-model="module.query.moduleName" :size="baseConfig.size"
                                  placeholder="模块名"></ep-input>
                    </div>
                    <div style="margin-left: 20px;">
                        <ep-button @click="getModule()" :size="baseConfig.size" type="primary">查询</ep-button>
                    </div>
                    <div style="margin-left: 10px;">
                        <ep-button @click="popup.addModule.open()" :size="baseConfig.size">新增模块</ep-button>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :size="baseConfig.size" :data="module.data" :height="500">
                        <ep-table-item column="moduleName" title="模块名">
                            <template slot-scope="props">
                                <div class="noBorder">
                                    <ep-input @blur="(i) => {
                                        let newData = i.path[0].value;
                                        if (newData === props.row.moduleName) {
                                            return;
                                        }

                                        module.selectModuleId = props.row.id;
                                        props.row.moduleName = newData;
                                        variableUtil.extend(module.update, props.row);
                                        updateModule().then(() => {
                                            getModule();
                                        });
                                    }" :size="baseConfig.size" :value="props.row.moduleName"></ep-input>
                                </div>
                            </template>
                        </ep-table-item>
                        <ep-table-item column="creator" title="创建人"></ep-table-item>
                        <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                        <ep-table-item column="updatedBy" title="更新人"></ep-table-item>
                        <ep-table-item column="updatedTime" title="更新时间"></ep-table-item>
                        <ep-table-item column="action" title="操作">
                            <template slot-scope="props">
                                <ep-button @click="() => {
                                    module.delete = [];
                                    module.delete.push(props.row.id);
                                    deleteModule().then(() => {
                                        getModule();
                                    });
                                }" type="text" style="color: #FF4D4F;padding-left: 0;">删除
                                </ep-button>
                            </template>
                        </ep-table-item>
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
                    <ep-button @click="popup.addModule.ok()" :size="baseConfig.size" style="width: 100%;"
                               type="primary">确定
                    </ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 编辑项目弹框 -->
        <ep-modal title="编辑" v-model="popup.updateProject.show" width="300px">
            <div>
                <div style="display: flex;">
                    <div style="width: 70px;display: flex;align-items: center;">
                        项目名
                    </div>
                    <div style="width: 100%;">
                        <ep-input :size="baseConfig.size" v-model="project.update.objectSystem"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="popup.updateProject.ok()" :size="baseConfig.size" style="width: 100%;"
                               type="primary">确定
                    </ep-button>
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
                // 当前选择的项目
                selectProjectId: null,

                query: {
                    objectSystem: ""
                },

                add: {
                    objectSystem: ""
                },

                update: {
                    id: 0,
                    objectSystem: ""
                },

                delete: {
                    idList: []
                },

                data: []
            },

            // 模块数据
            module: {
                // 当前选择的模块
                selectModuleId: null,

                query: {
                    objsystemId: null,
                    moduleName: ""
                },

                add: {
                    objsystemId: "",
                    moduleName: ""
                },

                delete: [],

                update: {
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
                            this.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                },

                updateProject: {
                    show: false,

                    open(data) {
                        variableUtil.extend(current.project.update, data);
                        this.show = true;
                    },

                    ok() {
                        current.updateProject().then(() => {
                            alterUtil.success("完成");
                            current.getProject();
                            this.show = false;
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
                        current.module.query.moduleName = "";
                        current.project.selectProjectId = projectId;
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
                            this.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                }
            },

            baseConfig,
            alterUtil,
            variableUtil
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
        },

        async updateProject() {
            return await objectSystemApi.update(this.project.update.id, this.project.update).catch((m) => {
                return Promise.reject(m);
            });
        },

        async deleteProject() {
            return await objectSystemApi.delete(this.project.delete.idList).catch((m) => {
                return Promise.reject(m);
            });
        },

        async deleteModule() {
            return await objectModuleApi.delete(this.module.delete).catch((m) => {
                return Promise.reject(m);
            });
        },

        async updateModule() {
            return await objectModuleApi.update(this.module.selectModuleId, this.module.update).catch((m) => {
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

<style>
a {
    text-decoration: none;
}

a:link {
    text-decoration: none;
}

a:visited {
    text-decoration: none;
}

a:hover {
    text-decoration: none;
}

a:active {
    text-decoration: none;
}

a:focus {
    text-decoration: none;
}
</style>