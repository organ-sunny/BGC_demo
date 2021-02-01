<template>
    <div>
        <ep-card layout="contents">
            <div slot="contents">
                <div style="display: flex;justify-content: center;padding: 0 35%;">
                    <ep-input></ep-input>
                    <ep-button style="margin-left: 10px;" type="primary">搜索</ep-button>
                </div>
            </div>
        </ep-card>

        <div style="margin-top: 50px;display: flex;flex-wrap: wrap;">
            <div class="systemPanel card center title addSystemPanel">
                新增
            </div>

            <div v-for="(item, key) in system.data" :key="key" class="systemPanel card">
                <div class="title" style="height: 75%;padding: 20px;box-sizing: border-box;">
                    {{item.objSystem}}
                </div>

                <div style="height: 25%;border-top: 1px solid #ddd;display: flex;">
                    <div style="width: 100%;" class="center">
                        <ep-button type="text">详情</ep-button>
                    </div>
                    <div style="width: 100%;border-left: 1px solid #ddd;" class="center">
                        <ep-button type="text" style="color: #FF4D4F;">删除</ep-button>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin-top: 50px;display: flex;flex-direction: row-reverse;">
            <ep-pager :total-num="65" :page-size="10" layout="pager"></ep-pager>
        </div>
    </div>
</template>

<script>
    export default {
        name: "system",

        data() {
            return {
                system: {
                    data: []
                }
            };
        },

        methods: {
            getSystem() {
                this.system.data = [
                    {
                        objSystem: "项目1"
                    },
                    {
                        objSystem: "项目2"
                    },
                    {
                        objSystem: "项目2"
                    },
                    {
                        objSystem: "项目2"
                    },
                    {
                        objSystem: "项目2"
                    },
                    {
                        objSystem: "项目2"
                    }
                ];
            }
        },

        created() {
            this.getSystem();
        }
    }
</script>

<style scoped>
    .systemPanel {
        width: 302px;
        height: 150px;
        margin: 10px;
    }

    .addSystemPanel {
        border: 1px dashed #ccc;
        color: #999;
        cursor: pointer;
    }

    .addSystemPanel:hover {
        border: 1px dashed #2296F3;
    }
</style>