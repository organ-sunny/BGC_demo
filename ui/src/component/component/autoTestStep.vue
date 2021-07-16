<template>
    <div style="padding: 20px;">
        <div style="display: flex;">
            <div style="width: 40px;display: flex;flex-direction: row-reverse;align-items: center;padding-right: 10px;">
                名称
            </div>
            <div style="width: 100%;">
                <ep-input v-model="autoTestStep.name" :size="baseConfig.size"></ep-input>
            </div>
        </div>

        <ep-tabs v-model="tab" style="margin-top: 20px;" @change="() => {
            let name = autoTestStep.name;
            variable.clean(autoTestStep);
            autoTestStep.name = name;
        }">
            <ep-tab-item name="browser" label="浏览器操作">
                <div style="overflow: hidden;margin-top: 10px;">
                    <ep-select placeholder="请选择" :size="baseConfig.size" v-model="autoTestStep.action">
                        <ep-select-item index="OPEN" label="打开网址"></ep-select-item>
                        <ep-select-item index="AWAIT" label="线程等待"></ep-select-item>
                        <ep-select-item index="EXECUTE_SCRIPT" label="执行js脚本"></ep-select-item>
                    </ep-select>
                </div>

                <!-- 选择不同的浏览器操作 -->
                <div style="margin-top: 20px;">
                    <!-- 打开网址 || 线程等待 -->
                    <div v-if="autoTestStep.action === 'OPEN' || autoTestStep.action === 'AWAIT'">
                        <ep-input :size="baseConfig.size" v-model="autoTestStep.value"></ep-input>
                    </div>

                    <!-- 执行js脚本 -->
                    <div v-if="autoTestStep.action === 'EXECUTE_SCRIPT'">
                        <ep-input type="textarea" v-model="autoTestStep.value"></ep-input>
                    </div>
                </div>
            </ep-tab-item>

            <ep-tab-item name="element" label="元素操作">
                <div>
                    选择元素
                </div>

                <div style="margin-top: 10px;">
                    <ep-tag type="success">findBy.{{findElement.type}}({{findElement.root}})</ep-tag>
                    <span v-for="(item, key) in findElement.elements" :key="key">
                        .
                        <ep-tag @close="deleteElement(key)" v-if="item.type === 'CHILD'" type="primary" closable>{{item.type}}({{item.index}})</ep-tag>
                        <ep-tag @close="deleteElement(key)" v-else type="primary" closable>{{item.type}}</ep-tag>
                    </span>
                </div>

                <div style="margin-top: 10px;display: flex;">
                    <div style="width: 30%;">
                        <ep-select placeholder="请选择" v-model="findElement.type">
                            <ep-select-item index="SELECTOR" label="SELECTOR"></ep-select-item>
                            <ep-select-item index="XPATH" label="XPATH"></ep-select-item>
                            <ep-select-item index="TEXT" label="TEXT"></ep-select-item>
                            <ep-select-item index="ID" label="ID"></ep-select-item>
                        </ep-select>
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="findElement.root"></ep-input>
                    </div>
                </div>

                <div v-if="findElement.type !== '' && findElement.root !== ''" style="margin-top: 10px;overflow: hidden;">
                    <div>
                        <ep-button @click="findElement.elements.push({type: 'PARENT'})" style="width: 100%;" :plain="true" type="info">父元素</ep-button>
                    </div>
                    <div style="margin-top: 5px;display: flex;">
                        <ep-input v-model="findElement.childIndexTemp" placeholder="索引" style="width: 20%;"></ep-input>
                        <ep-button @click="() => {
                            if (findElement.childIndexTemp === '') {
                                alterUtil.info('索引不能为空');
                                return;
                            }
                            findElement.elements.push({
                                type: 'CHILD',
                                index: findElement.childIndexTemp
                            });
                        }" style="width: 100%;" :plain="true" type="info">子元素</ep-button>
                    </div>
                    <div style="margin-top: 5px;">
                        <ep-button @click="findElement.elements.push({type: 'PREV'})" style="width: 100%;" :plain="true" type="info">上一个元素</ep-button>
                    </div>
                    <div style="margin-top: 5px;">
                        <ep-button @click="findElement.elements.push({type: 'NEXT'})" style="width: 100%;" :plain="true" type="info">下一个元素</ep-button>
                    </div>
                </div>

                <ep-divider></ep-divider>

                <div>
                    操作类型
                </div>

                <div style="overflow: hidden;margin-top: 10px;">
                    <ep-select placeholder="请选择" :size="baseConfig.size" v-model="autoTestStep.action">
                        <ep-select-item index="CLICK" label="点击"></ep-select-item>
                        <ep-select-item index="SEND_KEY" label="输入"></ep-select-item>
                    </ep-select>
                </div>

                <!-- 输入的框 -->
                <div v-if="autoTestStep.action === 'SEND_KEY'" style="margin-top: 10px;">
                    <ep-input v-model="findElement.sendKey" placeholder="输入的值" :size="baseConfig.size"></ep-input>
                </div>
            </ep-tab-item>
        </ep-tabs>
    </div>
</template>

<script>
    import baseConfig from "../../config/baseConfig.js";
    import alterUtil from "../../util/alterUtil.js";
    import {variable} from "../../util/zj0724common.js";

    export default {
        name: "autoTestStep.vue",

        props: ["data"],

        data() {
            return {
                autoTestStep: this.data,

                // tab
                tab: "browser",

                // 元素选择
                findElement: {
                    type: "",
                    root: "",
                    elements: [
                        // {
                        //     type: "",
                        //     index: ""
                        // }
                    ],
                    childIndexTemp: "",

                    // send_key
                    sendKey: ""
                },

                baseConfig,

                alterUtil,

                variable
            }
        },

        methods: {
            init() {
                console.log(this.data);
            },

            loadValue() {
                let value = "";
                let split = "<|||>";
                if (this.findElement.type !== "") {
                    value = value + this.findElement.type + split;
                }
                if (this.findElement.root !== "") {
                    value = value + this.findElement.root + split;
                }
                for (let item of this.findElement.elements) {
                    if (item.type === "CHILD") {
                        value = value + `${item.type}(${item.index})${split}`;
                    } else {
                        value = value + `${item.type}${split}`;
                    }
                }
                if (this.findElement.sendKey !== "") {
                    value = value + `SEND_KEY(${this.findElement.sendKey})${split}`;
                }

                if (value !== "") {
                    value = value.substring(0, value.length - split.length);
                }
                this.autoTestStep.value = value;
            },

            deleteElement(key) {
                this.findElement.elements.splice(key, 1);
            }
        },

        watch: {
            "autoTestStep.name": function() {
                let parent1 = this.$parent;
                if (parent1 !== undefined) {
                    let parent2 = parent1.$parent;
                    if (parent2 !== undefined) {
                        parent2.$forceUpdate();
                    }
                }
            },

            "findElement.type": function () {
                this.loadValue();
            },
            "findElement.root": function () {
                this.loadValue();
            },
            "findElement.elements.length": function () {
                this.loadValue();
            },
            "findElement.sendKey": function () {
                this.loadValue();
            }
        },

        created() {
            this.init();
        }
    }
</script>