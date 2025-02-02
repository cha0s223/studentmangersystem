<template>
  <n-modal v-model:show="showModel" style="width:400px" preset="dialog">
<template #default>
  <n-card>
    <n-form :model="rowData">
        <n-form-item v-for="(value,key) in rowData" :key="key" :label="key">
            <n-input v-model:value="rowData[key]" :type=" key=='password'? 'password' : 'text' "/>
        </n-form-item>
    </n-form>
  </n-card>
  </template>
  <template #action>
    <n-button type="primary" @click="closedislog()">确认</n-button>
  </template>

</n-modal>
</template>

<script setup>
import {  ref } from 'vue';
const showModel=ref(false)
showModel=props.showDialog
const rowData=ref([])
rowData=props.rowData
const props=defineProps(['rowData','showDialog'])

function closedislog(){
    showModel.value=false
}
</script>

<style>

</style>