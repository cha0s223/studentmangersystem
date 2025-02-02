<template>
    <indexVue style="width: 100vw; height: 100vw;">
        <template #header>
            <h1>校园卡管理系统</h1>
        </template>
        <template #default>
            <n-layout style="display: flex  ;justify-content: center; align-items: center; width: 100%;">
            <n-card>
                <n-tabs class="card-tabs" default-value="signin" size="large" animated
                    pane-wrapper-style="margin: 0 -4px"
                    pane-style="padding-left: 4px; padding-right: 4px; box-sizing: border-box;">
                    <n-tab-pane name="signin" tab="登录">
                        <loginVue>

                        </loginVue>
                    </n-tab-pane>
                    <n-tab-pane name="signup" tab="注册">
                       <registerVue>
                        
                       </registerVue>
                    </n-tab-pane>
                </n-tabs>
            </n-card>
        </n-layout>
        </template>
        <template #footer>
            <h5>底部</h5>
        </template>
    </indexVue>
</template>

<script setup>
import loginVue from "../../../pages/login.vue";
import { ref } from "vue";
import indexVue from "../index.vue";
import registerVue from "../../../pages/register.vue"


</script>

<style></style>