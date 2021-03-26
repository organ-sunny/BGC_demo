<template>
    <div @click="isSelectProject = false" style="width: 100%;height: 100%;display: flex;">
        <!-- 左侧 -->
        <div style="width: 13%;border-right: 1px solid #DCDFE6;overflow: auto;" class="scrollbar">
            <!-- 项目选择 -->
            <div>
                <div class="select" @click.stop="isSelectProject = !isSelectProject;getProject()"
                     style="display: flex;height: 100%;align-items: center;padding: 20px;border-bottom: 1px solid #DCDFE6;">
                    <div style="white-space: nowrap;" class="title">
                        <span v-if="variableUtil.isEmpty(project.select.objectSystem)">
                            请选择项目
                        </span>
                        <span v-else>
                            项目：{{ project.select.objectSystem }}
                        </span>
                    </div>
                    <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                        <i v-if="!isSelectProject" class="ion-chevron-right"></i>
                        <i v-if="isSelectProject" class="ion-chevron-down"></i>
                    </div>
                </div>

                <!-- 下拉框 -->
                <div style="position: relative;width: 100%;">
                    <div v-if="isSelectProject" class="ep-drop-wrap ep-dropdown-menu scrollbar"
                         style="position: absolute;width: 100%;">
                        <ul>
                            <li @click="switchProject(item)" v-for="(item, key) in project.data" :key="key"
                                class="ep-dropdown-menu-item">{{ item.objectSystem }}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- 所有模块 -->
            <div @click="allModuleIsShow = !allModuleIsShow;allModuleIsShow ? getModule() : ''"
                 style="display: flex;padding: 10px 20px;cursor: pointer;" class="select">
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
                            <ep-button @click="addApiPopup.open()" :size="baseConfig.size" type="info">新增api</ep-button>
                        </div>

                        <div style="margin-top: 20px;">
                            <ep-table :size="baseConfig.size" :data="api.data" :height="500">
                                <ep-table-item column="apiName" title="api名称"></ep-table-item>
                                <ep-table-item column="apiAddress" title="请求地址"></ep-table-item>
                                <ep-table-item column="apiMethod" title="请求方式">
                                    <template slot-scope="props">
                                        <ep-tag v-if="props.row.apiMethod === 'GET'" type="success" size="small">
                                            GET
                                        </ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'POST'" type="primary" size="small">
                                            POST
                                        </ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'PUT'" type="warning" size="small">
                                            PUT
                                        </ep-tag>
                                        <ep-tag v-if="props.row.apiMethod === 'DELETE'" type="danger" size="small">
                                            DELETE
                                        </ep-tag>
                                    </template>
                                </ep-table-item>
                                <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                                <ep-table-item column="creator" title="创建人"></ep-table-item>
                                <ep-table-item column="updatedTime" title="更新时间"></ep-table-item>
                                <ep-table-item column="updatedBy" title="更新人"></ep-table-item>
                                <ep-table-item column="action" title="操作">
                                    <template slot-scope="props">
                                        <ep-button @click="() => {
                                            updateApiPopup.show = true;
                                            variableUtil.extend(api.update, props.row);
                                        }" :size="baseConfig.size">编辑</ep-button>
                                        <ep-button @click="() => {
                                            alterUtil.confirm('确认删除？').then(() => {
                                                api.delete.idList = [];
                                                api.delete.idList.push(props.row.id);
                                                deleteApi().then(() => {
                                                    alterUtil.success('成功');
                                                    getApi();
                                                });
                                            }).catch(() => {});
                                        }" :size="baseConfig.size" type="danger">删除</ep-button>
                                    </template>
                                </ep-table-item>
                            </ep-table>
                        </div>
                    </card>
                </div>
            </div>

            <!-- api -->
            <div v-if="tab === 'api'">
                <card head="api信息">
                    <div class="bgc_panel">
                        <div class="apiInfo">
                            <div>
                                名称
                            </div>
                            <div>
                                {{selectApi.apiName}}
                            </div>
                        </div>

                        <div class="apiInfo" style="margin-top: 10px;">
                            <div>
                                接口地址
                            </div>
                            <div>
                                {{selectApi.apiAddress}}
                            </div>
                        </div>

                        <div class="apiInfo" style="margin-top: 10px;">
                            <div>
                                请求方式
                            </div>
                            <div>
                                {{selectApi.apiMethod}}
                            </div>
                        </div>
                    </div>
                </card>


                <card head="接口用例" style="margin-top: 50px;">
                    <div style="margin-top: 20px;" class="bgc_panel">
                        <div>
                            <ep-button @click="addTestcasePopup.open()" :size="baseConfig.size">新增</ep-button>
                        </div>

                        <div style="margin-top: 20px;">
                            <ep-table :size="baseConfig.size" :data="apiCase.data" :height="500">
                                <ep-table-item column="apiCaseNum" title="用例编号"></ep-table-item>
                                <ep-table-item column="apiCaseName" title="用例名称"></ep-table-item>
                                <ep-table-item column="apiCaseDescription" title="用例描述"></ep-table-item>
                                <ep-table-item column="apiCaseRequestHeader" title="接口请求头"></ep-table-item>
                                <ep-table-item column="apiCaseRequestParam" title="接口请求入参"></ep-table-item>
                                <ep-table-item column="apiCaseExpectedResult" title="预期结果"></ep-table-item>
                                <ep-table-item column="apiCaseActualResult" title="实际结果"></ep-table-item>
                                <ep-table-item column="isPassed" title="是否通过"></ep-table-item>
                                <ep-table-item column="apiCaseRemark" title="备注"></ep-table-item>
                                <ep-table-item column="createdTime" title="创建时间"></ep-table-item>
                                <ep-table-item column="creator" title="创建人"></ep-table-item>
                                <ep-table-item column="updatedTime" title="更新时间"></ep-table-item>
                                <ep-table-item column="updatedBy" title="更新人"></ep-table-item>
                                <ep-table-item column="action" title="操作">
                                    <template slot-scope="props">
                                        <div style="display: flex;">
                                            <ep-button @click="addTestcasePopup.open(props.row)" type="text">编辑</ep-button>
                                            <ep-button @click="() => {
                                                alterUtil.confirm('确定删除？').then(() => {
                                                    apiCase.delete.idList = [];
                                                    apiCase.delete.idList.push(props.row.id);
                                                    deleteTestcase().then(() => {
                                                        alterUtil.success('删除成功');
                                                        getTestcase();
                                                    });
                                                }).catch(() => {});
                                            }" type="text" style="color: #FF4D4F;">删除</ep-button>
                                        </div>
                                    </template>
                                </ep-table-item>
                            </ep-table>
                        </div>
                    </div>
                </card>
            </div>
        </div>

        <!-- 新增api弹框 -->
        <ep-modal title="新增api" v-model="addApiPopup.show" width="500px">
            <div>
                <div style="display: flex;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        接口名称
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.add.apiName" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        请求地址
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.add.apiAddress" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
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
                    <ep-button @click="addApiPopup.ok()" :size="baseConfig.size" style="width: 100%;" type="primary">确定
                    </ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 编辑api弹框 -->
        <ep-modal title="编辑" width="500px" v-model="updateApiPopup.show">
            <div>
                <div style="display: flex;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        接口名称
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.update.apiName" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        请求地址
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="api.update.apiAddress" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div
                        style="width: 90px;display: flex;align-items: center;flex-direction: row-reverse;padding-right: 10px;">
                        请求方式
                    </div>
                    <div style="width: 100%;">
                        <ep-select :size="baseConfig.size" placeholder="请选择" v-model="api.update.apiMethod">
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
                    <ep-button @click="() => {
                        updateApi().then(() => {
                            alterUtil.success('成功');
                            getApi();
                            updateApiPopup.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }" :size="baseConfig.size" style="width: 100%;" type="primary">确定</ep-button>
                </div>
            </div>
        </ep-modal>

        <!-- 新增用例弹框 -->
        <ep-modal :title="(addTestcasePopup.isAdd ? '新增' : '编辑') + '用例'" width="1000px" v-model="addTestcasePopup.show" :wrap-close="false">
            <div style="max-height: 700px;overflow: auto;padding: 10px;" class="scrollbar">
                <div style="display: flex;">
                    <card head="api信息" style="padding-right: 10px;width: 50%;">
                        <div class="apiInfo">
                            <div>
                                名称
                            </div>
                            <div>
                                {{selectApi.apiName}}
                            </div>
                        </div>

                        <div class="apiInfo" style="margin-top: 10px;">
                            <div>
                                接口地址
                            </div>
                            <div>
                                {{selectApi.apiAddress}}
                            </div>
                        </div>

                        <div class="apiInfo" style="margin-top: 10px;">
                            <div>
                                请求方式
                            </div>
                            <div>
                                {{selectApi.apiMethod}}
                            </div>
                        </div>
                    </card>

                    <card head="基本信息" style="padding-left: 10px;width: 50%;">
                        <div style="display: flex;">
                            <div style="white-space: nowrap;display: flex;align-items: center;">
                                用例编号
                            </div>
                            <div style="width: 100%;margin-left: 10px;">
                                <ep-input v-model="apiCase.add.apiCaseNum" :size="baseConfig.size" style="width: 100%;"></ep-input>
                            </div>
                        </div>

                        <div style="display: flex;margin-top: 20px;">
                            <div style="white-space: nowrap;display: flex;align-items: center;">
                                用例名称
                            </div>
                            <div style="width: 100%;padding-left: 10px;">
                                <ep-input v-model="apiCase.add.apiCaseName" :size="baseConfig.size" style="width: 100%;"></ep-input>
                            </div>
                        </div>

                        <div style="margin-top: 20px;display: flex;">
                            <div style="white-space: nowrap;display: flex;align-items: center;">
                                用例描述
                            </div>
                            <div style="width: 100%;padding-left: 10px;">
                                <ep-input v-model="apiCase.add.apiCaseDescription" :size="baseConfig.size" style="width: 100%;"></ep-input>
                            </div>
                        </div>
                    </card>
                </div>

                <card head="请求参数" style="margin-top: 50px;">
                    <ep-tabs>
                        <ep-tab-item name="header" label="请求头">
                            <div v-for="(item, key) in addTestcasePopup.requestData.headers" :key="key" style="display: flex;overflow: hidden;margin-top: 10px;">
                                <div style="width: 45%;padding-right: 10px;">
                                    <ep-input v-model="item.key" :size="baseConfig.size" placeholder="键"></ep-input>
                                </div>
                                <div style="width: 45%;padding: 0 10px;">
                                    <ep-input v-model="item.value" :size="baseConfig.size" placeholder="值"></ep-input>
                                </div>
                                <div style="width: 10%;">
                                    <ep-button @click="() => {
                                            for (let i = 0; i < addTestcasePopup.requestData.headers.length; i++) {
                                                if (key === i) {
                                                    addTestcasePopup.requestData.headers.splice(i, 1);
                                                }
                                            }
                                        }" style="width: 100%;" :size="baseConfig.size">删除</ep-button>
                                </div>
                            </div>

                            <div style="margin-top: 10px;overflow: hidden;">
                                <ep-button @click="() => {
                                        addTestcasePopup.requestData.headers.push({
                                            key: '',
                                            value: ''
                                        });
                                    }" :size="baseConfig.size" style="width: 100%;">添加...</ep-button>
                            </div>
                        </ep-tab-item>
                        <ep-tab-item name="params" label="params">
                            <div v-for="(item, key) in addTestcasePopup.requestData.params" :key="key" style="display: flex;overflow: hidden;margin-top: 10px;">
                                <div style="width: 45%;padding-right: 10px;">
                                    <ep-input v-model="item.key" :size="baseConfig.size" placeholder="键"></ep-input>
                                </div>
                                <div style="width: 45%;padding: 0 10px;">
                                    <ep-input v-model="item.value" :size="baseConfig.size" placeholder="值"></ep-input>
                                </div>
                                <div style="width: 10%;">
                                    <ep-button @click="() => {
                                            for (let i = 0; i < addTestcasePopup.requestData.params.length; i++) {
                                                if (key === i) {
                                                    addTestcasePopup.requestData.params.splice(i, 1);
                                                }
                                            }
                                        }" style="width: 100%;" :size="baseConfig.size">删除</ep-button>
                                </div>
                            </div>

                            <div style="margin-top: 10px;overflow: hidden;">
                                <ep-button @click="() => {
                                        addTestcasePopup.requestData.params.push({
                                            key: '',
                                            value: ''
                                        });
                                    }" :size="baseConfig.size" style="width: 100%;">添加...</ep-button>
                            </div>
                        </ep-tab-item>
                        <ep-tab-item name="body" label="body">
                            <ep-input type="textarea" v-model="addTestcasePopup.requestData.body"></ep-input>
                        </ep-tab-item>
                    </ep-tabs>
                </card>

                <card head="预期响应" style="margin-top: 50px;">
                    <div v-for="(item, key) in addTestcasePopup.expectedResult" :key="key" style="display: flex;overflow: hidden;margin-top: 10px;">
                        <div style="width: 45%;padding-right: 10px;">
                            <ep-input v-model="item.key" :size="baseConfig.size" placeholder="键"></ep-input>
                        </div>
                        <div style="width: 45%;padding: 0 10px;">
                            <ep-input v-model="item.value" :size="baseConfig.size" placeholder="值"></ep-input>
                        </div>
                        <div style="width: 10%;">
                            <ep-button @click="() => {
                                            for (let i = 0; i < addTestcasePopup.expectedResult.length; i++) {
                                                if (key === i) {
                                                    addTestcasePopup.expectedResult.splice(i, 1);
                                                }
                                            }
                                        }" style="width: 100%;" :size="baseConfig.size">删除</ep-button>
                        </div>
                    </div>

                    <div style="margin-top: 10px;overflow: hidden;">
                        <ep-button @click="() => {
                                        addTestcasePopup.expectedResult.push({
                                            key: '',
                                            value: ''
                                        });
                                    }" :size="baseConfig.size" style="width: 100%;">添加...</ep-button>
                    </div>
                </card>

                <card head="实际响应" style="margin-top: 50px;">
                    <div v-if="!addTestcasePopup.isAdd">
                        <ep-alert v-if="apiCase.add.isPassed === 'Pass'" title="PASS" type="success" :closable="false"></ep-alert>

                        <ep-alert v-else-if="apiCase.add.isPassed === 'Failed'" title="FAIL" type="error" :closable="false"></ep-alert>

                        <ep-alert v-else title="未执行" type="info" :closable="false"></ep-alert>
                    </div>

                    <div v-if="addTestcasePopup.isAdd" style="display: flex;">
                        <div style="display: flex;align-items: center;">
                            是否通过
                        </div>
                        <ep-select :size="baseConfig.size" placeholder="请选择" style="margin-left: 10px;" v-model="apiCase.add.isPassed">
                            <ep-select-item index="Pass" label="PASS"></ep-select-item>
                            <ep-select-item index="Failed" label="FAIL"></ep-select-item>
                        </ep-select>
                    </div>

                    <div style="display: flex;margin-top: 10px;">
                        <div style="white-space: nowrap;">
                            响应体&emsp;
                        </div>
                        <div style="width: 100%;margin-left: 10px;">
                            <ep-input :disabled="!addTestcasePopup.isAdd" type="textarea" v-model="apiCase.add.apiCaseActualResult"></ep-input>
                        </div>
                    </div>

                    <div style="display: flex;margin-top: 10px;">
                        <div style="white-space: nowrap;">
                            备注&emsp;&emsp;
                        </div>
                        <div style="width: 100%;margin-left: 10px;">
                            <ep-input :disabled="!addTestcasePopup.isAdd" type="textarea" v-model="apiCase.add.apiCaseRemark"></ep-input>
                        </div>
                    </div>
                </card>

                <div style="margin-top: 30px;display: flex;flex-direction: row-reverse;">
                    <ep-button @click="addTestcasePopup.ok()" type="primary" style="width: 70px;">确定</ep-button>
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
                data: [],

                // 删除
                delete: {
                    idList: []
                },

                // 编辑
                update: {
                    id: "",
                    apiName: "",
                    apiAddress: "",
                    apiMethod: ""
                }
            },

            // apiCase
            apiCase: {
                // 添加
                add: {
                    id: "",
                    objectApiId: "",
                    apiCaseNum: "",
                    apiCaseName: "",
                    apiCaseDescription: "",
                    apiCaseRequestHeader: "",
                    apiCaseRequestParam: "",
                    apiCaseExpectedResult: "",
                    apiCaseActualResult: "",
                    isPassed: "",
                    apiCaseRemark: ""
                },

                // 删除
                delete: {
                    idList: []
                },

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

            // 编辑api弹框
            updateApiPopup: {
                show: false
            },

            // 新增用例弹框
            addTestcasePopup: {
                show: false,

                // 是否是新增
                isAdd: true,

                // 用例数据
                testcase: {},

                // 请求参数
                requestData: {
                    headers: [],
                    params: [],
                    body: ""
                },

                // 预期结果
                expectedResult: [],

                open(testcase) {
                    this.show = true;

                    // 数据还原
                    this.isAdd = true;
                    variableUtil.reset(current.apiCase.add);
                    variableUtil.reset(this.requestData);
                    this.expectedResult = [];

                    current.apiCase.add.objectApiId = current.selectApi.id;
                    if (!variableUtil.isEmpty(testcase)) {
                        this.testcase = testcase;
                        this.isAdd = false;
                        variableUtil.extend(current.apiCase.add, this.testcase);

                        // 装配请求头
                        if (!variableUtil.isEmpty(testcase.apiCaseRequestHeader)) {
                            let headerJson = JSON.parse(testcase.apiCaseRequestHeader);
                            for (let key in headerJson) {
                                if (headerJson.hasOwnProperty(key)) {
                                    this.requestData.headers.push({
                                        key: key,
                                        value: headerJson[key]
                                    });
                                }
                            }
                        }

                        // 装配请求参数
                        if (!variableUtil.isEmpty(testcase.apiCaseRequestParam)) {
                            let apiCaseRequestParamJson = JSON.parse(testcase.apiCaseRequestParam);
                            for (let item of apiCaseRequestParamJson) {
                                if (item.type === "params") {
                                    let content = item.content;
                                    if (!variableUtil.isEmpty(content)) {
                                        let contentJson = JSON.parse(content);
                                        for (let key in contentJson) {
                                            if (contentJson.hasOwnProperty(key)) {
                                                this.requestData.params.push({
                                                    key: key,
                                                    value: contentJson[key]
                                                });
                                            }
                                        }
                                    }
                                }
                                if (item.type === "body") {
                                    this.requestData.body = item.content;
                                }
                            }
                        }

                        // 装配预期结果
                        if (!variableUtil.isEmpty(testcase.apiCaseExpectedResult)) {
                            let apiCaseExpectedResultJson = JSON.parse(testcase.apiCaseExpectedResult);
                            for (let key in apiCaseExpectedResultJson) {
                                if (apiCaseExpectedResultJson.hasOwnProperty(key)) {
                                    this.expectedResult.push({
                                       key: key,
                                        value: apiCaseExpectedResultJson[key]
                                    });
                                }
                            }
                        }
                    }
                },

                ok() {
                    // 请求头
                    let head = {};
                    for (let item of this.requestData.headers) {
                        head[item.key] = item.value;
                    }
                    head = JSON.stringify(head);
                    if (head === "{}") {
                        head = "";
                    }
                    current.apiCase.add.apiCaseRequestHeader = head;

                    // 请求参数
                    let requestData = [];
                    if (this.requestData.params.length !== 0) {
                        let params = {
                            type: "params",
                            content: ""
                        };
                        let paramsJson = {};
                        for (let item of this.requestData.params) {
                            paramsJson[item.key] = item.value;
                        }
                        params.content = JSON.stringify(paramsJson);
                        requestData.push(params);
                    }
                    if (this.requestData.body !== "") {
                        let body = {
                            type: "body",
                            content: ""
                        };
                        body.content = this.requestData.body;
                        requestData.push(body);
                    }
                    requestData = JSON.stringify(requestData);
                    if (requestData === "[]") {
                        requestData = "";
                    }
                    current.apiCase.add.apiCaseRequestParam = requestData;

                    // 预期结果
                    let expectedResult = {};
                    if (this.expectedResult.length !== 0) {
                        for (let item of this.expectedResult) {
                            expectedResult[item.key] = item.value;
                        }
                    }
                    expectedResult = JSON.stringify(expectedResult);
                    if (requestData === "{}") {
                        requestData = "";
                    }
                    current.apiCase.add.apiCaseExpectedResult = expectedResult;

                    if (this.isAdd) {
                        current.addTestcase().then(() => {
                            alterUtil.success("成功");
                            current.getTestcase();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    } else {
                        current.updateTestcase().then(() => {
                            alterUtil.success("成功");
                            current.getTestcase();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                }
            },

            variableUtil,

            baseConfig,

            alterUtil
        };
    },

    methods: {
        init() {
            // this.addTestcase();
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
            apiTestCaseApi.query(this.selectApi.id).then((data) => {
                this.apiCase.data = data;
            });
        },

        // 切换项目
        switchProject(project) {
            this.project.select = project;
            this.allModuleIsShow = false;
            this.tab = "";
        },

        // 删除api
        async deleteApi() {
            return await objectApiApi.delete(this.api.delete.idList).catch((m) => {
                return Promise.reject(m);
            });
        },

        // 编辑api
        async updateApi() {
            return await objectApiApi.update(this.api.update.id, this.api.update).catch((m) => {
                return Promise.reject(m);
            });
        },

        // 新增用例
        async addTestcase() {
            return await apiTestCaseApi.add(this.apiCase.add).catch((m) => {
                return Promise.reject(m);
            });
        },

        // 编辑用例
        async updateTestcase() {
            return await apiTestCaseApi.update(this.apiCase.add.id, this.apiCase.add).catch((m) => {
                return Promise.reject(m);
            });
        },

        // 删除用例
        async deleteTestcase() {
            return await apiTestCaseApi.delete(this.apiCase.delete.idList).catch((m) => {
                return Promise.reject(m);
            });
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

.apiInfo {
    display: flex;
}
.apiInfo > div:nth-child(1) {
    width: 70px;
    font-weight: bold;
    /*display: flex;*/
    /*flex-direction: row-reverse;*/
}
.apiInfo > div:nth-child(2) {
    margin-left: 10px;
}
</style>