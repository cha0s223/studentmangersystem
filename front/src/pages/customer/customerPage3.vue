<template>
  <n-form :model="model" style="width: 50%;">
    <n-form-item label="卡密码">
        <n-input v-model:value="model.password" type="password"/>
    </n-form-item>
    <n-form-item label="充值金额">
        <n-input v-model:value="model.amount" type="text"/>
    </n-form-item>
    <n-button type="primary" @click="recharge()">充值</n-button>
  </n-form>
</template>

<script setup>
import { rechargeCard } from '@/api/items/customer';
import { useMessage } from 'naive-ui';
import { ref } from 'vue';

const message=useMessage()

const model=ref({
    password: "sxc258",
    amount: "",
})

function recharge(){
    rechargeCard(model.value).then(res=>{
        if(res.data.code==0){
            message.success("充值成功")
        }else{
            message.error(res.data.msg)
        }
    }).catch(err=>{
        message.error("充值失败")
    })
}
</script>

<style>

</style>