<template>
  <n-space vertical>
    <n-button type='primary' @click="addNewCard">添加卡</n-button>
    <n-data-table :columns="columns" :data="tableData" :pagination="false" :bordered="false"/>
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
              :disabled="key == 'cardNo'&& title=='修改卡信息' ? true : false"  show-password-on="mousedown" />
            <n-select v-if="key=='cardState'" v-model:value="rowData[key]" :options="cardOptions"/>
          </n-form-item>
        </n-form>
      </n-card>
    </template>
    <template #action>
      <n-space>
        <n-button type="warning" @click="title=='修改卡信息' ?confirmdislog() : confirmAdd()">确认</n-button>
        <n-button type='info' @click="closedislog()">取消</n-button>
      </n-space>
    </template>
  </n-modal>
</template>
  
  <script setup>
import { addCard, deleteCardById, showCards, updateCard } from '@/api/items/operator';
import { NButton, NTag, useDialog, useMessage } from 'naive-ui';
import { render } from 'naive-ui/es/_utils';
import { ref, watchEffect } from 'vue';
const message=useMessage();
const dialog=useDialog();
const showModel =ref(false)
const rowData=ref({})
const title=ref('')

function createColumns({play}){ 
  return [
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
    },{
      title: "操作",
      key: "action",
      // colSpan: (rowData,rowIndex)=>(rowIndex!==-1?2:1),
      render(row){
        return h(
          NButton,
          {
            strong: true,
            type: "primary",
            size: "medium",
            onclick: ()=>upcard(row)
          },
          {
            default: ()=>"修改"
          }
        )
      }
    },{
      title: "",
      key: "delete",
      render(row){
        return h(
          NButton,
          {
            strong: true,
            type: 'error',
            size: 'medium',
            onClick: () => delcard(row)
          },
          {
            default: () => '删除'
          }
        )
      }
    }
  ]}
const columns=createColumns({
  upcard(row){},
  delcard(row){},
})
const tableData=ref([])

function upcard(row){
  const { cardNo,cardPassword,balance,cardState } = row
  rowData.value = { cardNo,cardPassword,balance,cardState }
  title.value="修改卡信息"
  showModel.value=true
}

watchEffect(async () => {
  const resp = await showCards()
  tableData.value = await resp.data.data
}
)

const cardOptions=[
  {
    label: '正常',
    value: '0'
  },{
    label: '被冻结',
    value: '1'
  }
]
function confirmdislog(){
  updateCard(rowData.value).then(res=>{
    if(res.data.code==0){
      message.success('修改成功')
    }else{
      message.error(res.data.msg)
    }
  }).then(res=>{
    showCards().then(data=> tableData.value=data.data.data)
  })
  .catch(res=>{
    message.error("修改失败")
  })
  showModel.value=false
}
function closedislog(){
  showModel.value=false
}

function delcard(row){
  dialog.error(
    {
      title: '删除用户！',
      content: `是否要删除${row.cardNo}用户`,
      positiveText: '确定',
      negativeText: '不确定',
      onPositiveClick: ()=>{
        deleteCardById(row.cardNo).then(res=>{
          showCards().then(
              data=> tableData.value=data.data.data
          )
          message.success('删除成功')
        }).catch(err=>{
          message.error("删除失败")
        })
      },
      onNegativeClick: ()=>{
        message.info('取消成功')
      }
    }
  )
}
function addNewCard(){
  rowData.value={ "cardNo": "", "cardPassword": "", "balance": 0, "cardState": "" }
  title.value="添加新卡"
  showModel.value=true
}

function confirmAdd(){
  addCard(rowData.value).then(res=>{
    if(res.data.code==0){
      message.success("添加成功")
    }else{
      message.error(res.data.msg)
    }
  }).then(res=>{
    showCards().then(
              data=> tableData.value=data.data.data
    )
  }).catch(err=>{
    message.error("添加失败")
  })
  showModel.value=false
}
  </script>
  
  <style>
  
  </style>