<template>
    <n-data-table :columns="columns" :data="logsData" :pagination="{ pageSize: 10}" style="width: 90%"/>
  </template>
  
  <script setup>
import { findAllTransactions } from '@/api/items/operator';
import { ref } from 'vue';
const logsData=ref([])
const columns =ref([])
function getData(){
    findAllTransactions().then( res=>{
      logsData.value=res.data.data
      for(let data in logsData.value[0]){
        columns.value.push({
          title: data,
          key: data,
          sorter: (row1,row2)=> {
            if(data=='amount'){
              return row1[data]- row2[data]
            }else if(data=='ttime'){
              return Date.parse(row1[data])-Date.parse(row2[data])
            }else{
              return row1[data].length-row2[data].length
            }
          },
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