<template>
    <div>
        <div style="display: flex;background-color: #EEE;">
            <div style="width: 10px;" :style="{backgroundColor: baseConfig.backgroundColor}"></div>
            <div style="width: 100%;padding: 10px;" class="font">
                {{ head }}
            </div>
        </div>

        <div style="margin-top: 20px;">
<!--            <div style="padding: 20px;border: 1px solid rgb(230, 230, 230);">-->
            <slot></slot>
        </div>
    </div>
</template>

<script>
import baseConfig from "../../config/baseConfig.js";

export default {
    name: "card.vue",

    props: ["head"],

    data() {
        return {
            baseConfig
        };
    }
}
</script>