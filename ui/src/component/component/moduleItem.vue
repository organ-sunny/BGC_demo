<template>
    <div>
        <!-- 模块 -->
        <div :class="actionId === module.id ? 'action' : ''" class="select"
             style="display: flex;padding: 10px 20px;" @click="moduleClick()">
            <div>
                <i class="ion-navicon-round" style="margin-right: 10px;"></i>
            </div>
            <div style="white-space: nowrap;margin-left: 10px;">
                {{ module.moduleName }}
            </div>
            <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                <i @click.stop="isOpen = true;getApi()" v-if="!isOpen" class="ion-chevron-right icon"></i>
                <i @click.stop="isOpen = false" v-else class="ion-chevron-down icon"></i>
            </div>
        </div>

        <!-- api集合 -->
        <div v-if="isOpen" style="margin-left: 20px;">
            <div v-for="(item, key) in api.data"
                 :key="key" :class="!variableUtil.isEmpty(apiActionId) && apiActionId === item.id ? 'action' : ''" class="select"
                 style="padding: 10px 20px;" @click="selectApiKey = key;apiClick(item)">
                <i class="ion-information" style="margin-right: 10px;"></i>
                {{ item.apiName }}
            </div>
        </div>
    </div>
</template>

<script>
import objectApiApi from "../../api/objectApiApi.js";
import variableUtil from "../../util/variableUtil.js";

export default {
    name: "moduleItem.vue",

    props: ["module", "actionId", "apiActionId"],

    data() {
        return {
            // 是否展开
            isOpen: false,

            selectApiKey: -1,

            // api
            api: {
                // 数据
                data: []
            },

            variableUtil
        };
    },

    methods: {
        getApi() {
            let moduleId = this.module.id;
            objectApiApi.query({
                moduleId
            }).then((data) => {
                this.api.data = data;
            });
        },

        moduleClick() {
            this.$emit("click", this);
        },

        apiClick(api) {
            this.$emit("apiClick", this.module, api);
        }
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

.icon {
    transition: 0.1s;
}

.icon:hover {
    color: #409EFF;
}

.action {
    background-color: #e6e6e6;
}
</style>