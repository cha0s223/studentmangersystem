<template>
  <NLayout has-sider>
    <NLayoutSider width="25%">
      <n-menu :options="menuOptions" />
    </NLayoutSider>
    <NLayoutSider width="75%">
      <router-view></router-view>
    </NLayoutSider>
  </NLayout>
</template>

<script setup>
import { h } from 'vue';
import { RouterLink } from 'vue-router';

const menuOptions = [
  {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "page1"
        }
      },
      {
        default: () => '用户信息'
      }
    ),
    key: 'page1'
  }, {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "page2"
        }
      },
      {
        default: () => '日志查看'
      }
    ),
    key: 'page2'
  }, {
    label: () => h(
      RouterLink,
      {
        to: {
          name: "managerPage3"
        }
      },
      {
        default: () => '修改密码'
      }
    ),
    key: 'page3'
  },{
    label: ()=> h(
      RouterLink,
      {
        to: {
          name: "managerPage4"
        }
      },
      {
        default: ()=> '审批'
      }
    )
  }
]
</script>

<style></style>