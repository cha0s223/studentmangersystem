<template>
    <n-form-item label="输入密码已查看当前用户消费记录">
        <n-input v-model:value="password" type="password" style="width: 300px;" />
        <NButton type='primary' @click="checkUser()">查看</NButton>
    </n-form-item>
    <n-data-table :columns="columns" :data="tableData" :pagination="{ pageSize: 5 }" style="width: 90%" />
</template>

<script setup>
import { showAllTransactions } from '@/api/items/customer';
import { showUser } from '@/api/items/user';
import { useMessage } from 'naive-ui';
import { ref } from 'vue';
const password = ref("sxc258")
const tableData = ref([])
const columns = ref([])
const message = useMessage()
function getData(password) {
    showAllTransactions(password).then(res => {
        if (res.data.code == 0) {
            tableData.value = res.data.data
            for (let data in tableData.value[0]) {
                columns.value.push({
                    title: data,
                    key: data,
                    sorter: (row1, row2) => {
                        if (data == 'amount') {
                            return row1[data] - row2[data]
                        } else if (data == 'ttime') {
                            return Date.parse(row1[data]) - Date.parse(row2[data])
                        } else {
                            return row1[data].length - row2[data].length
                        }
                    },
                    ellipsis: {
                        tooltip: true
                    }
                })
            }
            message.success("查询成功")
        } else {
            message.error(res.data.msg)
        }
    }).catch(err => message.error("查询错误"))
}

function checkUser() {
    getData({ password: password.value })
}
</script>

<style></style>