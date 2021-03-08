<template>
    <div>
<!--        <div class="block-title">-->
<!--            接口管理-->
<!--        </div>-->

        <ep-card layout="contents">
            <div slot="contents">
                <div style="display: flex;">
                    <div style="display: flex;align-items: center;margin-left: 50px;">
                        系统名
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-select @change="() => {objectApiQuery.moduleId = null}" @open="getSystems()" size="small" placeholder="请选择" v-model="objectApiQuery.systemId" filterable>
                            <ep-select-item v-for="(item, key) in systems" :key="key" :index="item.id" :label="item.objectSystem"></ep-select-item>
                        </ep-select>
                    </div>

                    <div style="display: flex;align-items: center;margin-left: 50px;">
                        模块名
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-select @open="getModules()" size="small" placeholder="请选择" v-model="objectApiQuery.moduleId" filterable>
                            <ep-select-item v-for="(item, key) in modules" :key="key" :index="item.id" :label="item.moduleName"></ep-select-item>
                        </ep-select>
                    </div>

                    <div style="display: flex;align-items: center;margin-left: 50px;">
                        接口名称
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-input v-model="objectApiQuery.apiName" size="small"></ep-input>
                    </div>

                    <div style="margin-left: 50px;">
                        <ep-button @click="getObjectApis()" size="small" type="primary">查询</ep-button>
                    </div>
                </div>
            </div>
        </ep-card>

        <ep-card layout="contents" style="margin-top: 50px;">
            <div slot="contents">
                <div>
                    <ep-button @click="popup.addApi.open()" size="small" type="primary">新增</ep-button>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :height="550" :data="objectApis" size="small">
                        <ep-table-item column="apiName" title="接口名称"></ep-table-item>
                        <ep-table-item column="apiAddress" title="接口地址"></ep-table-item>
                        <ep-table-item column="apiMethod" title="请求方式"></ep-table-item>
                        <ep-table-item column="creator" title="创建人"></ep-table-item>
                        <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                        <ep-table-item column="updatedBy" title="修改人"></ep-table-item>
                        <ep-table-item column="updatedTime" title="修改时间"></ep-table-item>
                    </ep-table>
                </div>
            </div>
        </ep-card>

        <!-- 新增弹框 -->
        <ep-modal title="新增" v-model="popup.addApi.show" width="500px">
            <div>
                <div>
                    <div style="display: flex;align-items: center;margin-left: 50px;">
                        系统名
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-select @change="() => {objectApiQuery.moduleId = null}" @open="getSystems()" size="small" placeholder="请选择" v-model="objectApiQuery.systemId" filterable>
                            <ep-select-item v-for="(item, key) in systems" :key="key" :index="item.id" :label="item.objectSystem"></ep-select-item>
                        </ep-select>
                    </div>
                </div>

                <div style="display: flex;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        接口名
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        请求地址
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        请求方式
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button style="width: 100%;" size="small" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import objectApi from "../../../api/objectApi.js";
    import objectModuleApi from "../../../api/objectModuleApi.js";
    import objectSystemApi from "../../../api/objectSystemApi.js";
    import variableUtil from "../../../util/variableUtil.js";

    export default {
        name: "api.vue",

        data() {
            return {
                // api数据
                objectApis: [],

                // api数据查询条件
                objectApiQuery: {
                    systemId: null,
                    moduleId: null,
                    apiName: ""
                },

                // 模块数据
                modules: [],

                // 系统数据
                systems: [],

                // 弹框
                popup: {
                    addApi: {
                        show: false,

                        open() {
                            this.show = true;
                        }
                    }
                }
            };
        },

        methods: {
            init() {
                this.getObjectApis();
            },

            getObjectApis() {
                let req = {
                    moduleId: null,
                    apiName: ""
                };
                variableUtil.extend(req, this.objectApiQuery);

                objectApi.query(req).then((data) => {
                    this.objectApis = data;
                });
            },

            getModules() {
                let systemId = this.objectApiQuery.systemId;
                if (variableUtil.isEmpty(systemId)) {
                    this.modules = [];
                    return;
                }
                objectModuleApi.query(systemId).then((data) => {
                    this.modules = data;
                });
            },

            getSystems() {
                objectSystemApi.query().then((data) => {
                    this.systems = data;
                });
            },

            addApi(data) {
                objectApi.add(data).then(() => {

                });
            }
        },

        created() {
            this.init();
        }
    }
</script>