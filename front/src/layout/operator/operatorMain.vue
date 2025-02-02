<template>
<indexVue style="width: 100vw; height: 100vw;">
<template #header>
  <NLayout has-sider style="height: 70px;">
    <NLayoutSider width="20%">
    <h1>校园卡业务员</h1>
  </NLayoutSider>
  <NLayoutSider width="75%" >
    <NButton type="info" @click="outLog" style="display: flex; float: right;position: relative; top: 50% ">退出登录</NButton>
  </NLayoutSider>
  </NLayout>
</template>
<template #default>
    <n-layout has-sider>
        <n-layout-sider width="25%">
            <n-menu :options="menuOptions"/>
        </n-layout-sider>
        <n-layout-sider width="75%">
            <router-view>
            </router-view>
        </n-layout-sider>
    </n-layout>
</template>
<template #footer>
</template>
</indexVue>
</template>

<script setup>
import { RouterLink } from "vue-router";
import indexVue from "../default/index.vue";
import router from "@/router";
import { useJwtCheckStore } from "@/stores/counter";

const menuOptions=[
{
    label: () => h(
      RouterLink,
      {
        to: {
          name: "operatorPage1"
        }
      },
      {
        default: () => '校园卡信息'
      }
    ),
    key: 'page1'
  }, {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "operatorPage2"
        }
      },
      {
        default: () => '交易记录'
      }
    ),
    key: 'page2'
  }, 
  {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "operatorPage3"
        }
      },
      {
        default: () => '其他功能'
      }
    ),
    key: 'page3'
  },{
    label: () => h(
      RouterLink,
      {
        to: {
          name: "operatorPage4"
        }
      },
      {
        default: () => '审批'
      }
    ),
    key: 'page4'
  },
]

function outLog(){
  const store=useJwtCheckStore()
  store.setJwt("")
  router.push({path: "/"})
}
</script>

<style>

</style>