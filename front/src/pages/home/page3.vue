<template>
  <n-layout>
    <n-layout-content>
      <n-form-item label="输入密码已查看当前用户信息">
        <n-input v-model:value="password" style="width: 300px;" />
        <NButton type='primary' @click="checkUser">查看</NButton>
      </n-form-item>
    </n-layout-content>

    <n-layout-content>
      <h1>修改密码</h1>
      <n-form :model="mdPassword">
        <n-form-item label="请输入原密码">
          <n-input v-model:value="mdPassword.oldPWD" style="width: 300px;" />
        </n-form-item>
        <n-form-item label="请输入新密码">
          <n-input v-model:value="mdPassword.password" style="width: 300px;" />
        </n-form-item>
        <n-form-item label="请输入在输入一次新密码">
          <n-input v-model:value="mdPassword.password2" style="width: 300px;" />
        </n-form-item>
        <NButton type="primary" @click="mdpwd()">确认</NButton>
      </n-form>
    </n-layout-content>
  </n-layout>
</template>

<script setup>
import { modifyPassword, showUser } from '@/api/items/user';
import router from '@/router';
import { useDialog, useMessage } from 'naive-ui';
import { ref } from 'vue';
const message = useMessage()
const dialog = useDialog()
const password = ref('')
const mdPassword = ref({
  oldPWD: '',
  password: '',
  password2: ''
})
function checkUser() {
  showUser({password: password.value}).then(res => {
    if (res.data.code == 0) {
      dialog.info({
        title: '用户信息',
        content: `userName: ${res.data.data.userName}
                  password: ${res.data.data.password}
                  role: ${res.data.data.role}`,
        positiveText: '确认',
        onPositiveClick: () => {
          message.success("成功")
        }
      }
      )
    } else {
      password.value=null
      message.error("密码错误")
    }
  }).catch(err => {
    message.error("查询失败")
  })
}

function mdpwd() {
  const { oldPWD,password,password2 } = mdPassword.value
  modifyPassword({ oldPWD,password,password2 }).then(res => {
    if (res.data.code == 0) {
      message.success("修改成功")
      router.push({path: "/"})
    } else (
      message.error("密码错误")
    )
  })
  .catch(err => {
    message.error("修改失败")
  })
}
</script>

<style></style>