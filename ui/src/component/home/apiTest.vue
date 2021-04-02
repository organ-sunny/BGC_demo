<template>
    <div v-loading="loading" @click="isSelectProject = false" style="width: 100%;height: 100%;display: flex;">
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
                        routerQuery.moduleId = item.id;
                        routerQuery.apiId = undefined;
                        _loadingVueQuery();
                    }"
                    v-for="(item, key) in module.data"
                    :key="key"
                    :module="item"
                    @apiClick="(moduleItem, apiItem) => {
                        module.select = moduleItem;
                        selectApi = apiItem;
                        selectApi._testcaseRunResult = {
                            num: 0,
                            execution: 0,
                            pass: 0
                        };
                        tab = 'api';
                        getTestcase();
                        routerQuery.moduleId = module.select.id;
                        routerQuery.apiId = apiItem.id;
                        _loadingVueQuery();
                    }"
                    :api-action-id="selectApi.id" ref="moduleItem">
                </module-item>
            </div>
        </div>

        <!-- 右侧 -->
        <div style="width: 87%;padding: 20px;overflow: auto;" class="scrollbar">
            <div v-if="tab === ''" style="height: 100%;color: #909399;font-size: 20px;font-weight: bold;" class="center">
                选择项目和模块
            </div>

            <!-- module -->
            <div v-if="tab === 'module'">
                <card :head="'模块：' + module.select.moduleName">
                    <div class="bgc_panel">
                        <div class="title" style="margin-bottom: 20px;">
                            API信息
                        </div>

                        <div>
                            <ep-button @click="addApiPopup.open()" :size="baseConfig.size" type="primary">新增</ep-button>
                            <ep-button @click="() => {
                                    alterUtil.confirm('确定删除？').then(() => {
                                        api.delete.idList = [];
                                        for (let i = 0; i < api.selectList.length; i++) {
                                            api.delete.idList.push(api.selectList[i].id);
                                        }
                                        deleteApi().then(() => {
                                            getApi();
                                            _reloadModuleItem();
                                            alterUtil.success('删除成功');
                                        });
                                    }).catch(() => {});
                                }" :size="baseConfig.size" type="danger">删除</ep-button>
                            <ep-button @click="apiRunPopup.open()" :size="baseConfig.size" type="success">执行</ep-button>
                        </div>

                        <ep-table @selection-change="(allData) => {
                                api.selectList = [];
                                for (let i = 0 ; i < allData.length; i++) {
                                    api.selectList.push(allData[i]);
                                }
                            }" style="margin-top: 10px;" :size="baseConfig.size" :data="api.data">
                            <ep-table-item type="select"></ep-table-item>
                            <ep-table-item type="expand">
                                <template slot-scope="props">
                                    <div style="margin: 20px 0;" class="bgc_panel">
                                        <div style="display: flex;">
                                            <div style="width: 50%;">
                                                <div style="width: 50%;">
                                                    <span class="smallFont">
                                                        创建人:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.creator}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;margin-top: 10px;">
                                                    <span class="smallFont">
                                                        创建时间:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.createdTime}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;margin-top: 10px;">
                                                    <span class="smallFont">
                                                        更新人:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.updatedBy}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;margin-top: 10px;">
                                                    <span class="smallFont">
                                                        更新时间:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.updatedTime}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;display: flex;align-items: center;margin-top: 20px;" class="smallFont">
                                                    用例数：<span style="color: #2296F3;text-decoration: underline;margin-left: 10px;cursor: pointer;">{{props.row._testcaseRunResult.num}}</span>
                                                </div>
                                            </div>
                                            <div style="width: 50%;display: flex;">
                                                <div>
                                                    <div>
                                                        <ep-progress type="circle" :percentage="props.row._testcaseRunResult.execution"></ep-progress>
                                                    </div>
                                                    <div class="center smallFont" style="margin-top: 10px;">
                                                        执行率
                                                    </div>
                                                </div>
                                                <div style="margin-left: 10px;">
                                                    <div>
                                                        <ep-progress type="circle" :percentage="props.row._testcaseRunResult.pass" :status="props.row._testcaseRunResult.pass === 100 ? 'success': ''"></ep-progress>
                                                    </div>
                                                    <div class="center smallFont" style="margin-top: 10px;">
                                                        通过率
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </ep-table-item>
                            <ep-table-item width="50px" title="">
                                <template slot-scope="props">
                                    <div>
                                        <i v-if="props.row._testcaseRunResult.pass === 100" style="color: #27AE60;" class="ion-checkmark-circled"></i>
                                        <i v-else-if="props.row._testcaseRunResult.num !== 0" style="color: #E7963B;" class="ion-information-circled"></i>
                                    </div>
                                </template>
                            </ep-table-item>
                            <ep-table-item column="apiName" title="api名称"></ep-table-item>
                            <ep-table-item column="apiAddress" title="请求地址">
                                <template slot-scope="props">
                                    <div style="color: #2296F3;text-decoration: underline;cursor: pointer;">
                                        {{props.row.apiAddress}}
                                    </div>
                                </template>
                            </ep-table-item>
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
                            <ep-table-item column="action" title="操作">
                                <template slot-scope="props">
                                    <ep-button @click="() => {
                                            updateApiPopup.show = true;
                                            variableUtil.extend(api.update, props.row);
                                        }" :size="baseConfig.size" type="text">编辑</ep-button>
                                </template>
                            </ep-table-item>
                        </ep-table>
                    </div>
                </card>
            </div>

            <!-- api -->
            <div v-if="tab === 'api'">
                <card :head="'API：' + selectApi.apiName">
                    <div class="bgc_panel">
                        <div style="display: flex;">
                            <div style="width: 50%;display: flex;flex-direction: column;">
<!--                                <div class="apiInfo">-->
<!--                                    <div>-->
<!--                                        名称-->
<!--                                    </div>-->
<!--                                    <div>-->
<!--                                        {{selectApi.apiName}}-->
<!--                                    </div>-->
<!--                                </div>-->

                                <div class="apiInfo">
                                    <div>
                                        接口地址
                                    </div>
                                    <div style="text-decoration: underline;color: #2296F3;">
                                        {{selectApi.apiAddress}}
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        请求方式
                                    </div>
                                    <div>
                                        <ep-tag v-if="selectApi.apiMethod === 'GET'" type="success" size="small">
                                            GET
                                        </ep-tag>
                                        <ep-tag v-if="selectApi.apiMethod === 'POST'" type="primary" size="small">
                                            POST
                                        </ep-tag>
                                        <ep-tag v-if="selectApi.apiMethod === 'PUT'" type="warning" size="small">
                                            PUT
                                        </ep-tag>
                                        <ep-tag v-if="selectApi.apiMethod === 'DELETE'" type="danger" size="small">
                                            DELETE
                                        </ep-tag>
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        创建人
                                    </div>
                                    <div>
                                        {{selectApi.creator}}
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        创建时间
                                    </div>
                                    <div>
                                        {{selectApi.createdTime}}
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        修改人
                                    </div>
                                    <div>
                                        {{selectApi.updatedBy}}
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        修改时间
                                    </div>
                                    <div>
                                        {{selectApi.updatedTime}}
                                    </div>
                                </div>

                                <div class="apiInfo" style="margin-top: 10px;">
                                    <div>
                                        用例数
                                    </div>
                                    <div>
                                        {{selectApi._testcaseRunResult.num}}
                                    </div>
                                </div>
                            </div>
                            <div style="width: 50%;display: flex;align-items: center;">
                                <div>
                                    <div>
                                        <ep-progress type="circle" :percentage="selectApi._testcaseRunResult.execution"></ep-progress>
                                    </div>
                                    <div class="center smallFont" style="margin-top: 10px;">
                                        执行率
                                    </div>
                                </div>
                                <div style="margin-left: 20px;">
                                    <div>
                                        <ep-progress type="circle" :percentage="selectApi._testcaseRunResult.pass" :status="selectApi._testcaseRunResult.pass === 100 ? 'success': ''"></ep-progress>
                                    </div>
                                    <div class="center smallFont" style="margin-top: 10px;">
                                        通过率
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style="margin-top: 20px;" class="bgc_panel">
                        <div style="margin-bottom: 20px;" class="title">
                            用例信息
                        </div>

                        <div>
                            <ep-button @click="addTestcasePopup.open(true, null)" :size="baseConfig.size" type="primary">新增</ep-button>
                            <ep-button @click="() => {
                                if (apiCase.selectList.length === 0) {
                                    alterUtil.info('未选择用例');
                                    return;
                                }
                                if (apiCase.selectList.length > 1) {
                                    alterUtil.info('只能选择一条用例');
                                    return;
                                }
                                addTestcasePopup.open(true, apiCase.selectList[0]);
                            }" :size="baseConfig.size" type="primary">复制</ep-button>
                            <ep-button :size="baseConfig.size" type="danger" @click="() => {
                                if (apiCase.selectList.length === 0) {
                                    alterUtil.info('未选择用例');
                                    return;
                                }
                                alterUtil.confirm('确定删除？').then(() => {
                                    apiCase.delete.idList = [];
                                    for (let i = 0; i < apiCase.selectList.length; i++) {
                                        apiCase.delete.idList.push(apiCase.selectList[i].id);
                                    }
                                    deleteTestcase().then(() => {
                                        getTestcase();
                                        alterUtil.success('删除成功');
                                    });
                                }).catch(() => {});
                            }">删除</ep-button>
                            <ep-button @click="() => {
                                if (apiCase.selectList.length === 0) {
                                    alterUtil.info('未选择用例');
                                    return;
                                }

                                apiCase.run.idList = [];
                                for (let i = 0; i < apiCase.selectList.length; i++) {
                                    apiCase.run.idList.push(apiCase.selectList[i].id);
                                }
                                alterUtil.confirm('确定执行？').then(() => {
                                    loading = true;
                                    runTestcase().then(() => {
                                        getTestcase();
                                        alterUtil.success('执行完成');
                                    }).finally(() => {
                                        loading = false;
                                    });
                                }).catch(() => {});
                            }" :size="baseConfig.size" type="success">执行</ep-button>
                        </div>

                        <div style="margin-top: 20px;">
                            <ep-table :size="baseConfig.size" :data="apiCase.data" @selection-change="(allData) => {
                                apiCase.selectList = allData;
                            }">
                                <ep-table-item type="select"></ep-table-item>
                                <ep-table-item type="expand">
                                    <template slot-scope="props">
                                        <div class="bgc_panel" style="margin: 20px 0;">
                                            <!--                                        <div style="padding: 20px;">-->
                                            <div class="smallFont">
                                                请求参数
                                            </div>
                                            <ep-tabs>
                                                <ep-tab-item name="params" label="params">
                                                    <div v-for="(value, key, index) in props.row._param" :key="index" style="display: flex;overflow: hidden;margin-top: 10px;">
                                                        <div style="width: 50%;padding-right: 10px;">
                                                            <ep-input disabled :value="key" :size="baseConfig.size"></ep-input>
                                                        </div>
                                                        <div style="width: 50%;padding-left: 10px;">
                                                            <ep-input disabled :value="value" :size="baseConfig.size"></ep-input>
                                                        </div>
                                                    </div>
                                                </ep-tab-item>
                                                <ep-tab-item name="header" label="header">
                                                    <div v-for="(value, key, index) in props.row._header" :key="index" style="display: flex;overflow: hidden;margin-top: 10px;">
                                                        <div style="width: 50%;padding-right: 10px;">
                                                            <ep-input disabled :value="key" :size="baseConfig.size"></ep-input>
                                                        </div>
                                                        <div style="width: 50%;padding-left: 10px;">
                                                            <ep-input disabled :value="value" :size="baseConfig.size"></ep-input>
                                                        </div>
                                                    </div>
                                                </ep-tab-item>
                                                <ep-tab-item name="body" label="body">
                                                    <ep-input disabled type="textarea" :value="props.row._body"></ep-input>
                                                </ep-tab-item>
                                            </ep-tabs>

                                            <div style="margin-top: 20px;" class="smallFont">
                                                预期结果
                                            </div>
                                            <div style="margin-top: 10px;">
                                                <ep-input disabled type="textarea" v-model="props.row.apiCaseExpectedResult"></ep-input>
                                            </div>

                                            <div style="margin-top: 20px;" class="smallFont">
                                                实际结果
                                            </div>
                                            <div style="margin-top: 10px;">
                                                <ep-input disabled type="textarea" :value="props.row.apiCaseActualResult"></ep-input>
                                            </div>

                                            <div style="display: flex;margin-top: 20px;">
                                                <div style="width: 50%;">
                                                    <span class="smallFont">
                                                        创建人:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.creator}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;">
                                                    <span class="smallFont">
                                                        创建时间:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.createdTime}}
                                                    </span>
                                                </div>
                                            </div>

                                            <div style="display: flex;margin-top: 20px;">
                                                <div style="width: 50%;">
                                                    <span class="smallFont">
                                                        更新人:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.updatedBy}}
                                                    </span>
                                                </div>
                                                <div style="width: 50%;">
                                                    <span class="smallFont">
                                                        更新时间:
                                                    </span>
                                                    <span style="margin-left: 10px;" class="smallFont">
                                                        {{props.row.updatedTime}}
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </template>
                                </ep-table-item>
                                <ep-table-item column="apiCaseNum" title="用例编号"></ep-table-item>
                                <ep-table-item column="apiCaseName" title="用例名称"></ep-table-item>
                                <ep-table-item column="apiCaseDescription" title="用例描述"></ep-table-item>

                                <ep-table-item column="isPassed" title="是否通过">
                                    <template slot-scope="props">
                                        <ep-tag v-if="props.row.isPassed === 'Pass'" type="success" size="small">
                                            PASS
                                        </ep-tag>
                                        <ep-tag v-else-if="props.row.isPassed === 'Failed'" type="danger" size="small">
                                            FAILED
                                        </ep-tag>
                                        <ep-tag v-else type="gray" size="small">
                                            未执行
                                        </ep-tag>
                                    </template>
                                </ep-table-item>
                                <ep-table-item column="apiCaseRemark" title="备注"></ep-table-item>
                                <ep-table-item column="action" title="操作">
                                    <template slot-scope="props">
                                        <div style="display: flex;">
                                            <ep-button @click="addTestcasePopup.open(false, props.row)" type="text">编辑</ep-button>
                                            <ep-button @click="() => {
                                                loading = true;
                                                apiCase.debug.id = props.row.id;
                                                debugTestcase().then((response) => {
                                                    alterUtil.confirm(`响应：${response.response}，备注：${response.remark}`).then(() => {

                                                    }).catch(() => {});
                                                }).finally(() => {
                                                    loading = false;
                                                });
                                            }" style="color: #E7963B;" type="text">调试</ep-button>
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
                            _reloadModuleItem();
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
                <card head="基本信息">
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

                <card head="请求参数" style="margin-top: 50px;">
                    <ep-tabs>
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
                        <ep-tab-item name="header" label="header">
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
                        <ep-tab-item name="body" label="body">
                            <ep-input type="textarea" v-model="addTestcasePopup.requestData.body"></ep-input>
                        </ep-tab-item>
                    </ep-tabs>
                </card>

                <card head="预期结果" style="margin-top: 50px;">
                    <div>
                        <ep-input type="textarea" v-model="apiCase.add.apiCaseExpectedResult"></ep-input>
                    </div>
                </card>

                <card v-if="addTestcasePopup.isAdd" head="实际结果" style="margin-top: 50px;">
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

        <!-- api执行弹框 -->
        <ep-modal title="执行" width="500px" v-model="apiRunPopup.show" :wrap-close="false">
            <div style="max-height: 700px;overflow: auto;" class="scrollbar">
                <ep-steps :space="100" direction="vertical" :active="apiRunPopup.active" finish-status="success">
                    <ep-step
                        v-for="(item, key) in apiRunPopup.itemList"
                        :key="key"
                        :title="'API：' + item.apiName"
                        :description="item.status"
                    >
                    </ep-step>
                </ep-steps>
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
            // 加载
            loading: false,

            // 是否显示选择项目下拉框
            isSelectProject: false,

            // 所有模块是否展开
            allModuleIsShow: false,

            // 右侧的tab
            tab: "",

            // 路由query
            routerQuery: {
                projectId: undefined,
                moduleId: undefined,
                apiId: undefined
            },

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

                // 选中的
                selectList: [],

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
                // 添加 | 修改
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

                // 运行
                run: {
                    idList: []
                },

                // 调试
                debug: {
                    id: undefined
                },

                // 选择的用例
                selectList: [],

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
                        current._reloadModuleItem();
                        this.show = false;
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

                open(isAdd, testcase) {
                    this.show = true;

                    // 数据还原
                    this.isAdd = isAdd;
                    variableUtil.reset(current.apiCase.add);
                    variableUtil.reset(this.requestData);
                    this.expectedResult = [];

                    current.apiCase.add.objectApiId = current.selectApi.id;
                    if (!variableUtil.isEmpty(testcase)) {
                        this.testcase = testcase;
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

                    if (this.isAdd) {
                        current.addTestcase().then(() => {
                            alterUtil.success("成功");
                            current.getTestcase();
                            this.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    } else {
                        current.updateTestcase().then(() => {
                            alterUtil.success("成功");
                            current.getTestcase();
                            this.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                }
            },

            // api执行弹框
            apiRunPopup: {
                show: false,

                active: 0,

                itemList: [],

                Item: class {
                    constructor(api) {
                        this.id = api.id;
                        this.apiName = api.apiName;
                        this.status = "";
                    }

                    async run() {
                        this.status = "执行中...";
                        let testcaseList = await apiTestCaseApi.query(this.id);
                        current.apiCase.run.idList = [];
                        for (let testcase of testcaseList) {
                            current.apiCase.run.idList.push(testcase.id);
                        }
                        await current.runTestcase().then(() => {
                            this.status = "执行完成！";
                            current.apiRunPopup.active = current.apiRunPopup.active + 1;
                        });
                    }
                },

                async open() {
                    if (current.api.selectList.length === 0) {
                        alterUtil.info("未选择API");
                        return;
                    }

                    this.show = true;
                    this.active = 0;
                    this.itemList = [];
                    for (let api of current.api.selectList) {
                        this.itemList.push(new this.Item(api));
                    }

                    for (let item of this.itemList) {
                        await item.run();
                    }
                }
            },

            variableUtil,

            baseConfig,

            alterUtil,

            window: window
        };
    },

    methods: {
        async init() {
            let query = this.$route.query;
            variableUtil.extend(this.routerQuery, query);

            // 加载项目
            if (!variableUtil.isEmpty(this.routerQuery.projectId)) {
                let projectData = await objectSystemApi.query({
                    id: this.routerQuery.projectId
                });
                if (projectData.length === 1) {
                    this.project.select = projectData[0];

                    // 加载模块
                    if (!variableUtil.isEmpty(this.routerQuery.moduleId)) {
                        let moduleData = await objectModuleApi.query({
                            id: this.routerQuery.moduleId
                        });
                        if (moduleData.length === 1) {
                            this.allModuleIsShow = true;
                            this.getModule();
                            this.module.select = moduleData[0];
                            this.tab = "module";
                            this.getApi();

                            // 加载api
                            if (!variableUtil.isEmpty(this.routerQuery.apiId)) {
                                let apiData = await objectApiApi.query({
                                    id: this.routerQuery.apiId
                                });

                                if (apiData.length === 1) {
                                    this.selectApi = apiData[0];
                                    this.selectApi._testcaseRunResult = {};
                                    this.tab = "api";
                                    this.getTestcase();
                                }
                            }
                        }
                    }
                }
            }
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
            }).then(async (data) => {
                for (let item of data) {
                    await this._loadTestcaseRunResult(item);
                }
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
            apiTestCaseApi.query(this.selectApi.id).then(async (data) => {
                for (let item of data) {
                    item["_header"] = variableUtil.isEmpty(item.apiCaseRequestHeader) ? {} : JSON.parse(item.apiCaseRequestHeader);
                    item["_param"] = {};
                    item["_body"] = "";
                    let params = variableUtil.isEmpty(item.apiCaseRequestParam) ? [] : JSON.parse(item.apiCaseRequestParam);
                    for (let i of params) {
                        let key = i.type;
                        let content = i.content;
                        if (variableUtil.isEmpty(content)) {
                            continue;
                        }
                        if (key === "params") {
                            item["_param"] = JSON.parse(content);
                        }
                        if (key === "body") {
                            item["_body"] = content;
                        }
                    }
                }
                this.apiCase.data = data;
                await this._loadTestcaseRunResult(this.selectApi);
                this.$forceUpdate();
            });
        },

        // 切换项目
        switchProject(project) {
            this.project.select = project;
            this.allModuleIsShow = false;
            this.tab = "";
            this.routerQuery.projectId = project.id;
            this.routerQuery.moduleId = undefined;
            this.routerQuery.apiId = undefined;
            this._loadingVueQuery();
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
        },

        // 执行用例
        async runTestcase() {
            return await apiTestCaseApi.run(this.apiCase.run.idList).catch((m) => {
                return Promise.reject(m);
            });
        },

        // 调试用例
        debugTestcase() {
            return apiTestCaseApi.debug(this.apiCase.debug);
        },

        // 加载用例通过率
        async _loadTestcaseRunResult(api) {
            await apiTestCaseApi.query(api.id).then((data) => {
                let size = data.length;
                let execution = 0;
                let pass = 0;
                if (size !== 0) {
                    let executionSize = 0;
                    let passSize = 0;
                    for (let i = 0; i < size; i++) {
                        let isPass = data[i].isPassed;
                        if (!variableUtil.isEmpty(isPass)) {
                            executionSize = executionSize + 1;
                            if (isPass === "Pass") {
                                passSize = passSize + 1;
                            }
                        }
                    }
                    execution = parseInt(((executionSize / size) * 100).toFixed(2));
                    pass = executionSize === 0 ? 0 : parseInt(((passSize / executionSize) * 100).toFixed(2));
                }
                api["_testcaseRunResult"] = {
                    num: size,
                    execution,
                    pass
                };
            });
        },

        // 加载路由的query
        _loadingVueQuery() {
            for (let key in this.routerQuery) {
                if (this.routerQuery.hasOwnProperty(key)) {
                    if (`${this.$route.query[key]}` !== `${this.routerQuery[key]}`) {
                        this.$router.push({
                            query: this.routerQuery
                        });
                        return;
                    }
                }
            }
        },

        // 重新加载moduleItem
        _reloadModuleItem() {
            for (let item of this.$refs["moduleItem"]) {
                if (item.actionId === item.module.id) {
                    item.getApi();
                }
            }
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