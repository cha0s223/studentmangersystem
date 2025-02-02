<template>
  <n-form :model="model" style="width: 50%;">
    <n-form-item label="卡密码">
        <n-input v-model:value="model.password" type="password"/>
    </n-form-item>
    <n-form-item label="消费金额">
        <n-input v-model:value="model.camount" type="text"/>
    </n-form-item>
    <n-form-item label="消费物品">
        <n-input v-model:value="model.cobject" type="text"/>
    </n-form-item>
    <n-button type="primary" @click="consume()">消费</n-button>
  </n-form>
</template>

<script setup>
import { consumeCard } from '@/api/items/customer';
import { useMessage } from 'naive-ui';
import { ref } from 'vue';

const message=useMessage()
const model=ref({
    password: "sxc258",
    camount: "",
    cobject: ""
})

function consume(){
    consumeCard(model.value).then(res=>{
        if(res.data.code==0){
            message.success("消费成功")
        }else{
            message.error(res.data.msg)
        }
    }).catch(err=> message.error("消费失败"))
}

</script>

<style>

</style>