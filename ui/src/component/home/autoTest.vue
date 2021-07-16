<template>
    <div style="width: 100%;height: 100%;display: flex;">
        <div style="width: 13%;height: 100%;border-right: 1px solid #e6e6e6;background-color: white;">
            <div style="padding: 20px;border-bottom: 1px solid rgb(230, 230, 230);" class="title">
                自动化测试
            </div>

            <div style="">
                <ep-menu :default-index="tab" class="ep-menu-demo-vertical" theme="light">
                    <ep-menu-item style="border: none;" index="case" icon="navicon-round">用例</ep-menu-item>
                    <ep-menu-item style="border: none;" index="module" icon="navicon-round">模块维护</ep-menu-item>
                </ep-menu>
            </div>
        </div>

        <div style="width: 87%;padding: 20px;overflow: auto;">
            <div v-if="tab === 'module'">
                <card head="模块维护">
                    <div class="bgc_panel">
                        <ep-button @click="saveAutoTestModulePopup.open(null)" type="primary" :size="baseConfig.size">新增</ep-button>

                        <ep-table :size="baseConfig.size" :data="autoTestModules" style="margin-top: 20px;">
                            <ep-table-item column="name" title="名称"></ep-table-item>
                            <ep-table-item column="steps" title="stepIdList">
                                <template slot-scope="props">
                                    <div v-for="(item, key) in props.row.steps" :key="key" style="margin-top: 5px;">
                                        <ep-tag type="primary">{{item.name}}</ep-tag>
                                    </div>
                                </template>
                            </ep-table-item>
                            <ep-table-item column="action" title="操作">
                                <template slot-scope="props">
                                    <ep-button type="text" @click="saveAutoTestModulePopup.open(props.row)" :size="baseConfig.size">编辑</ep-button>
                                </template>
                            </ep-table-item>
                        </ep-table>
                    </div>
                </card>
            </div>
        </div>

        <!-- 保存module弹框 -->
        <ep-modal :title="saveAutoTestModule.id === null ? '新增' : '编辑'" v-model="saveAutoTestModulePopup.show" width="1000px" :close-on-press-escape="false" :wrap-close="false">
            <div style="max-height: 700px;overflow-y: auto;padding: 10px;" class="scrollbar">
                <div style="display: flex;">
                    <div style="width: 40px;display: flex;flex-direction: row-reverse;align-items: center;padding-right: 10px;">
                        名称
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="saveAutoTestModule.name" :size="baseConfig.size"></ep-input>
                    </div>
                </div>

                <card head="步骤列" style="margin-top: 30px;">
                    <div>
                        <ep-button @click="saveAutoTestModule.steps.push(new AutoTestStep('未命名 - ' + (saveAutoTestModule.steps.length + 1)))" :size="baseConfig.size" style="width: 100%;">新增步骤</ep-button>
                    </div>

                    <ep-tabs layout="left" style="margin-top: 20px;">
                        <ep-tab-item v-for="(item, key) in saveAutoTestModule.steps" :key="key" :name="key" :label="key">
                            <template slot="label">
                                <div>
                                    {{item.name}}
                                    <ep-icon @click.stop="() => {
                                        saveAutoTestModule.steps.splice(key, 1);
                                        let item1 = variable.clone(saveAutoTestModule.steps);
                                        saveAutoTestModule.steps = [];
                                        window.setTimeout(() => {
                                            saveAutoTestModule.steps = item1;
                                        }, 0);
                                    }" icon="close" wave theme="dark"></ep-icon>
                                </div>
                            </template>

                            {{item}}
                            <auto-test-step :data="item"></auto-test-step>
                        </ep-tab-item>
                    </ep-tabs>
                </card>

                <div style="margin-top: 20px;">
                    <ep-button @click="() => {
                        saveAutoTestModuleApi().then(() => {
                            alterUtil.success('成功');
                            saveAutoTestModulePopup.show = false;
                           init();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }" type="primary" style="width: 100%;" :size="baseConfig.size">保存</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import baseConfig from "../../config/baseConfig.js";
    import autoTestModuleApi from "../../api/autoTestModuleApi.js";
    import card from "../component/card.vue";
    import autoTestStep from "../component/autoTestStep.vue";
    import AutoTestStep from "../../js/AutoTestStep.js";
    import alterUtil from "../../util/alterUtil.js";
    import {variable} from "../../util/zj0724common.js";

    export default {
        name: "autoTest.vue",

        data() {
            let current = this;

            return {
                // tab
                tab: "module",

                // 模块数据
                autoTestModules: [],

                // 保存module
                saveAutoTestModule: {
                    id: null,
                    name: null,
                    steps: []
                },

                // 保存module弹框
                saveAutoTestModulePopup: {
                    show: false,

                    open(data) {
                        this.show = true;
                        variable.clean(current.saveAutoTestModule);

                        if (data !== null) {
                            current.saveAutoTestModule = variable.clone(data);
                            let steps = current.saveAutoTestModule.steps;
                            current.saveAutoTestModule.steps = [];
                            current.window.setTimeout(() => {
                                current.saveAutoTestModule.steps = steps;
                            }, 0);
                        }
                    }
                },

                baseConfig,
                AutoTestStep,
                alterUtil,
                variable,
                window
            };
        },

        methods: {
            async queryAutoTestModulesApi() {
                return await autoTestModuleApi.query().then((data) => {
                    this.autoTestModules = data;
                }).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async saveAutoTestModuleApi() {
                return await autoTestModuleApi.save(this.saveAutoTestModule).catch((m) => {
                    return Promise.reject(m);
                });
            },

            init() {
                this.queryAutoTestModulesApi();
            }
        },

        created() {
            this.init();
        },

        components: {
            card: card,
            autoTestStep: autoTestStep
        }
    }
</script>