<template>
  <indexVue style="width: 100vw;">
    <template #header>
      <NLayout has-sider style="height: 70px;">
        <NLayoutSider width="20%">
          <n-menu mode="horizontal" :options="menuOptions" responsive />
        </NLayoutSider>
        <NLayoutSider width="75%">
          <NButton type="info" @click="outLog" style="display: flex; float: right;position: relative; top: 50% ">退出登录
          </NButton>
        </NLayoutSider>
      </NLayout>
    </template>
    <template #default>
      <router-view>
      </router-view>
    </template>
  </indexVue>
</template>

<script setup>
import { h } from "vue";
import { RouterLink } from 'vue-router';
import indexVue from '../index.vue';
import { useJwtCheckStore } from "@/stores/counter";
import router from "@/router";
const menuOptions = [
  {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "home"
        }
      },
      {
        default: () => "主页"
      }
    ),
    key: "home-page"
  },
  {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "about"
        }
      },
      {
        default: () => "关于"
      }
    ),
    key: "about-page"
  }
]
function outLog() {
  const store = useJwtCheckStore()
  store.setJwt("")
  router.push({ path: "/" })
}
</script>

<style scoped></style>