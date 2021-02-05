<template>
    <div>
        <ep-card layout="contents">
            <div slot="contents">
                <div style="display: flex;justify-content: center;padding: 0 35%;">
                    <ep-input v-model="system.query.objectSystem" placeholder="系统名"></ep-input>
                    <ep-button @click="getSystem()" style="margin-left: 10px;" type="primary">搜索</ep-button>
                </div>
            </div>
        </ep-card>

        <ep-card layout="contents" style="margin-top: 50px;">
            <div slot="contents">
                <div>
                    <ep-button @click="addPopup.open()" size="small" type="primary">新增系统</ep-button>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :height="550" :data="data.systems" size="small">
                        <ep-table-item column="objectSystem" title="系统名"></ep-table-item>
                        <ep-table-item column="creator" title="创建人"></ep-table-item>
                        <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
<!--                        <ep-table-item column="updatedBy" title="修改人"></ep-table-item>-->
<!--                        <ep-table-item column="updatedTime" title="修改时间"></ep-table-item>-->
                        <ep-table-item column="action" title="操作">
                            <template slot-scope="props">
                                <ep-button @click="modulePopup.open(props.row.id)" type="text">模块详情</ep-button>
                            </template>
                        </ep-table-item>
                    </ep-table>
                </div>
            </div>
        </ep-card>

        <!-- 新增弹框 -->
        <ep-modal width="300px" title="新增" v-model="addPopup.show">
            <div>
                <div style="display: flex;">
                    <div style="white-space: nowrap;display: flex;align-items: center;">
                        系统名
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input style="width: 100%;" v-model="addPopup.data.objectSystem" size="small"></ep-input>
                    </div>
                </div>
                <div style="margin-top: 20px;display: flex;flex-direction: row-reverse;">
                    <ep-button @click="addPopup.add()" size="small" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 模块详情弹框 -->
        <ep-modal width="600px" title="模块详情" v-model="modulePopup.show">
            <div v-loading="modulePopup.loading">
                <div>
                    <ep-button @click="addModulePopup.open(modulePopup.data.systemId)" size="small" type="primary">添加</ep-button>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :data="data.modules" size="small">
                        <ep-table-item column="moduleName" title="模块名"></ep-table-item>
                        <ep-table-item column="action" title="操作">
                            <template slot-scope="props">
                                <ep-button type="text">编辑</ep-button>
                                <ep-button style="color: #FF4D4F;" type="text">删除</ep-button>
                            </template>
                        </ep-table-item>
                    </ep-table>
                </div>
            </div>
        </ep-modal>

        <!-- 添加模块弹框 -->
        <ep-modal width="300px" title="添加模块" v-model="addModulePopup.show">
            <div>
                <div style="display: flex;">
                    <div style="white-space: nowrap;display: flex;align-items: center;">
                        模块名
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addModulePopup.data.moduleName" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>
                <div style="margin-top: 20px;display: flex;flex-direction: row-reverse;">
                    <ep-button @click="addModulePopup.add()" size="small" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import objectSystemApi from "../../api/objectSystemApi.js";
    import alter from "../../util/alter.js";
    import objectModuleApi from "../../api/objectModuleApi.js";

    export default {
        name: "system",

        data() {
            let current = this;

            return {
                data: {
                    systems: [],

                    modules: []
                },

                system: {
                    query: {
                        objectSystem: ""
                    }
                },

                addPopup: {
                    show: false,
                    data: {
                        objectSystem: ""
                    },

                    open() {
                        this.show = true;
                        this.data.objectSystem = "";
                    },

                    async add() {
                        await current.addSystem(this.data.objectSystem);
                        current.init();
                        alter.success("完成");
                        this.show = false;
                        this.data = {
                            objectSystem: ""
                        };
                    }
                },

                modulePopup: {
                    loading: false,

                    show: false,

                    data: {
                        systemId: null
                    },

                    async init(systemId) {
                        this.loading = true;
                        this.data.systemId = systemId;
                        await current.getModules(systemId);
                        this.loading = false;
                    },

                    open(systemId) {
                        this.show = true;
                        this.init(systemId);
                    }
                },

                addModulePopup: {
                    show: false,

                    data: {
                        systemId: null,
                        moduleName: ""
                    },

                    open(systemId) {
                        this.show = true;
                        this.data.systemId = systemId;
                    },

                    async add() {
                        await current.addModule(this.data.systemId, this.data.moduleName);
                        alter.success("完成");
                        this.show = false;
                        await current.modulePopup.init(this.data.systemId);
                    }
                }
            };
        },

        methods: {
            init() {
                this.getSystem();
            },

            getSystem() {
                let objectSystem = this.system.query.objectSystem;

                objectSystemApi.query(objectSystem).then((data) => {
                    this.data.systems = data;
                });
            },

            async addSystem(objectSystem) {
                await objectSystemApi.add({
                    objectSystem
                }).then(() => {}).catch((message) => {
                    alter.error(message);
                });
            },

            async getModules(systemId) {
                this.data.modules = [];
                await objectModuleApi.query(systemId).then((data) => {
                    this.data.modules = data;
                }).catch((m) => {
                    alter.error(m);
                });
            },

            async addModule(objsystemId, moduleName) {
                let request = {
                    objsystemId, moduleName
                };

                await objectModuleApi.add(request).then(() => {}).catch((m) => {
                    alter.error(m);
                });
            }
        },

        created() {
            this.init();
        }
    }
</script>