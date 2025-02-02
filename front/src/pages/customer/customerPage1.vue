<template>
  <n-form-item label="输入密码已查看当前我的卡">
        <n-input v-model:value="password" type="password" style="width: 300px;" />
        <NButton type='primary' @click="checkCard()">查看</NButton>
    </n-form-item>
    <n-data-table :columns="columns" :data="tableData" :pagination="false" style="width: 90%" />

</template>


<script setup>
import { showMyCard } from '@/api/items/customer';
import { NTag, useMessage } from 'naive-ui';
import { render } from 'naive-ui/es/_utils';
import { ref } from 'vue';
const password = ref("sxc258")
const tableData = ref([])
const message = useMessage()
const columns = [
    {
      title: "卡号",
      key: "cardNo"
    },
    {
      title: "卡密码",
      key: "cardPassword"
    },
    {
      title: "余额",
      key: "balance"
    },
    {
      title: "卡状态",
      key: "cardState",
      render(row){
        return h(
            NTag,
            {
              style: {
                marginRight: "6px"
              },
              type: row.cardState==0? "info" : "error",
              bordered: false
            },
            {
              default: () => row.cardState==0?"正常": "被冻结"
            }
          );
      }
    }
]

function checkCard(){
    showMyCard({password: password.value})
    .then(res=>{
        if(res.data.code==0){
            message.success("查询成功")
            tableData.value=[res.data.data]
        }else{
            message.error(res.data.msg)
        }
    })
    .catch(err=>message.error("查询错误"))
}


</script>

<style>

</style>