<template>
  <n-space vertical>
  <n-button type='primary' @click="addNewCard">申请校园卡</n-button>
  <p>已申请通过的校园卡</p>
  <n-data-table :columns="columns" :data="tableData" :pagination="false" style="width: 90%" />
  <n-form-item label="绑定校园卡号" >
    <n-input v-model:value="bindcardNo" style="width: 250px;"/>
  </n-form-item>
  <n-button type='primary' @click="bindCard">申请绑定校园卡</n-button>
</n-space>
  <n-modal v-model:show="showModel" style="width:400px" preset="dialog" :title="title">
    <template #default>
      <n-card>
        <n-form :model="rowData">
          <n-form-item v-for="(value, key) in rowData" :key="key" :label="key">
            <n-input v-model:value="rowData[key]"
            v-if="key!=='cardState'"
            :type="key == 'cardPassword' 
            ? 'password' 
            :  'text'"
            show-password-on="mousedown" />
            <n-select v-if="key=='cardState'" v-model:value="rowData[key]" :options="cardOptions"/>
          </n-form-item>
        </n-form>
      </n-card>
    </template>
    <template #action>
      <n-space>
        <n-button type="warning" @click="confirmdislog()">确认</n-button>
        <n-button type='info' @click="closedislog()">取消</n-button>
      </n-space>
    </template>
  </n-modal>
</template>

<script setup>
import { addApplyCard, applyBindCard, showApplyedCard } from '@/api/items/customer';
import router from '@/router';
import { NTag, useMessage } from 'naive-ui';
import { ref } from 'vue';

const title=ref('')
const showModel =ref(false)
const rowData=ref({})
const bindcardNo=ref("")
const message=useMessage()
const tableData=ref([])
const cardOptions=[
  {
    label: '正常',
    value: '0'
  },{
    label: '被冻结',
    value: '1'
  }
]
function addNewCard(){
  rowData.value={ "cardNo": "", "cardPassword": "", "balance": 0, "cardState": "0"}
  title.value="添加新卡"
  showModel.value=true
}
const columns = [
    {
      title: "卡号",
      key: "cardNo"
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
function bindCard(){
  applyBindCard(bindcardNo.value).then(res=>{
    if(res.data.code=="0"){
      message.success("申请成功")
    }else{
      message.error(res.data.msg)
    }
  })
}

function confirmdislog(){
  addApplyCard(rowData.value).then(res=>{
    if(res.data.code==0){
      message.success('申请成功')
      router.go(0)
    }else{
      message.error(res.data.msg)
    }
  })
  .catch(res=>{
    message.error("修改失败")
  })
  showModel.value=false
}
function closedislog(){
  showModel.value=false
}

function getCardList(){
  showApplyedCard().then(res=>{
    tableData.value=res.data.data
  })
}
getCardList()
</script>

<style>

</style>