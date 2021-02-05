<template>
    <div>
        <ep-card layout="contents">
            <div slot="contents">
                <div style="display: flex;">
                    <div style="display: flex;align-items: center;">
                        系统名
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-select size="small" @open="getSystems()" placeholder="请选择" filterable v-model="query.systemId">
                            <ep-select-item v-for="(item, key) in data.systems" :key="key" :index="item.id" :label="item.objectSystem"></ep-select-item>
                        </ep-select>
                    </div>

                    <div style="display: flex;align-items: center;margin-left: 50px;">
                        模块名
                    </div>

                    <div style="margin-left: 10px;">
                        <ep-select size="small" @open="getModules()" placeholder="请选择" v-model="query.moduleName" filterable>
                            <ep-select-item v-for="(item, key) in data.modules" :key="key" :index="item.moduleName" :label="item.moduleName"></ep-select-item>
                        </ep-select>
                    </div>

                    <div style="margin-left: 50px;">
                        <ep-button @click="getTestcases()" size="small" type="primary">查询</ep-button>
                    </div>
                </div>
            </div>
        </ep-card>

        <ep-card layout="contents" style="margin-top: 50px;">
            <div slot="contents">
                <div>
                    <ep-button @click="addPopup.open()" size="small" type="primary">新增</ep-button>
                </div>

                <div style="margin-top: 20px;">
                    <ep-table :height="550" :data="data.testcases" size="small">
                        <ep-table-item column="objectSystemName" title="系统名"></ep-table-item>
                        <ep-table-item column="objectModuleName" title="模块名"></ep-table-item>
                        <ep-table-item column="apiCaseNum" title="用例编号"></ep-table-item>
                        <ep-table-item column="apiCaseName" title="用例名称"></ep-table-item>
                        <ep-table-item column="apiCaseDescription" title="用例描述"></ep-table-item>
                        <ep-table-item column="apiCaseRequestAddress" title="请求地址"></ep-table-item>
                        <ep-table-item column="apiCaseRequestMethod" title="请求方式"></ep-table-item>
                        <ep-table-item column="apiCaseRequestParam" title="请求参数"></ep-table-item>
                        <ep-table-item column="apiCaseExpectedResult" title="预期结果"></ep-table-item>
                        <ep-table-item column="apiCaseActualResult" title="实际结果"></ep-table-item>
                        <ep-table-item column="api_testcase_remark" title="备注"></ep-table-item>
                        <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                        <ep-table-item column="creator" title="创建人"></ep-table-item>
                        <ep-table-item column="isPassed" title="是否通过"></ep-table-item>
                        <ep-table-item column="action" title="操作" fixed="right">
                            <template slot-scope="props">
                                <ep-button type="text">执行此用例</ep-button>
                            </template>
                        </ep-table-item>
                    </ep-table>
                </div>
            </div>
        </ep-card>

        <!-- 新增用例弹框 -->
        <ep-modal title="新增" v-model="addPopup.show" width="500px">
            <div>
                <div style="display: flex;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        系统名
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.objectSystemName" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        模块名
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.objectModuleName" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        用例编号
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseNum" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        用例名称
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseName" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        用例描述
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseDescription" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        请求地址
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseRequestAddress" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        请求方式
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseRequestMethod" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        请求参数
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseRequestParam" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;white-space: nowrap;" class="font">
                        预期结果
                    </div>
                    <div style="width: 100%;margin-left: 10px;">
                        <ep-input v-model="addPopup.data.apiCaseExpectedResult" style="width: 100%;" size="small"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="addPopup.add()" style="width: 100%;" size="small" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import apiTestCaseApi from "../../../api/apiTestCaseApi.js";
    import alter from "../../../util/alter.js";
    import objectSystemApi from "../../../api/objectSystemApi.js";
    import objectModuleApi from "../../../api/objectModuleApi.js";
    import variableUtil from "../../../util/variableUtil.js";

    export default {
        name: "home_api_testcase",

        data() {
            let current = this;

            return {
                data: {
                    testcases: [],

                    systems: [],

                    modules: []
                },

                query: {
                    systemId: null,
                    moduleName: ""
                },

                addPopup: {
                    show: false,

                    data: {
                        objectSystemName: "",
                        objectModuleName: "",
                        apiCaseNum: "",
                        apiCaseName: "",
                        apiCaseDescription: "",
                        apiCaseRequestAddress: "",
                        apiCaseRequestMethod: "",
                        apiCaseRequestParam: "",
                        apiCaseExpectedResult: ""
                    },

                    open() {
                        this.show = true;
                    },

                    add() {
                        current.addTestcase(this.data).then(() => {
                            this.show = false;
                            this.data.testcase = {
                                objectSystemName: "",
                                objectModuleName: "",
                                apiCaseNum: "",
                                apiCaseName: "",
                                apiCaseDescription: "",
                                apiCaseRequestAddress: "",
                                apiCaseRequestMethod: "",
                                apiCaseRequestParam: "",
                                apiCaseExpectedResult: ""
                            };
                            alter.success("完成");
                            current.init();
                        }).catch((m) => {
                            alter.error(m);
                        });
                    }
                }
            };
        },

        methods: {
            init() {
                this.getTestcases();
            },

            getTestcases() {
                apiTestCaseApi.query().then((data) => {
                    this.data.testcases = data;
                });
            },

            async addTestcase(data) {
                await apiTestCaseApi.add(data).then(() => {}).catch((m) => {
                    return Promise.reject(m);
                });
            },

            getSystems() {
                objectSystemApi.query().then((data) => {
                    this.data.systems = data;
                });
            },

            getModules() {
                this.data.modules = [];

                let systemId = this.query.systemId;

                if (variableUtil.isEmpty(systemId)) {
                    return;
                }

                objectModuleApi.query(systemId).then((data) => {
                    this.data.modules = data;
                });
            }
        },

        created() {
            this.init();
        }
    }
</script>