<template>
    <n-data-table :columns="columns" :data="logsData" :pagination="{ pageSize: 10}" :scroll-x="1800"/>
</template>
  
  <script setup>
import { showLogs } from '@/api/items/manager';
import { ref } from 'vue';
  const logsData=ref([])
  const columns =ref([])
  function getData(){
    showLogs().then( res=>{
      logsData.value=res.data.data
      for(let data in logsData.value[0]){
        columns.value.push({
          title: data,
          key: data,
          ellipsis: {
            tooltip: true
          }
        })
      }
    }
    )
  }
  getData()
  

  </script>
  
  <style>
  
  </style>