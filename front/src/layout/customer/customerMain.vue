<template>
    <indexVue style="width: 100vw; height: 100vw;">
    <template #header>
      <NLayout has-sider style="height: 70px;">
        <NLayoutSider width="20%">
        <h1>欢迎卡用户{{  }}</h1>
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
              name: "customerPage1"
            }
          },
          {
            default: () => '我的校园卡'
          }
        ),
        key: 'page1'
      }, {
        label: () => h(
          RouterLink,
          {
            to: {
              name: "customerPage2"
            }
          },
          {
            default: () => '消费'
          }
        ),
        key: 'page2'
      },{
        label: () => h(
          RouterLink,
          {
            to: {
              name: "customerPage3"
            }
          },
          {
            default: () => '充值'
          }
        ),
        key: 'page3'
      },
      {
        label: () => h(
          RouterLink,
          {
            to: {
              name: "customerPage4"
            }
          },
          {
            default: () => '我的消费记录'
          }
        ),
        key: 'page4'
      },
      {
        label: () => h(
          RouterLink,
          {
            to: {
              name: "customerPage5"
            }
          },
          {
            default: () => '修改密码'
          }
        ),
        key: 'page5'
      },
      {
        label: () => h(
          RouterLink,
          {
            to: {
              name: "customerPage6"
            }
          },
          {
            default: () => '申请校园卡'
          }
        ),
        key: 'page6'
      }
    ]
    
    function outLog(){
      const store=useJwtCheckStore()
      store.setJwt("")
      router.push({path: "/"})
    }
    </script>
    
    <style>
    
    </style>