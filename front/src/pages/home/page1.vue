<template>

  <n-space vertical>
    <n-button @click="insert()" type='primary'>添加用户</n-button>
    <n-data-table size="medium" :columns="columns" :data="tableData" :pagination="false" :bordered="false" />
  </n-space>
  <n-modal v-model:show="showModel" style="width:400px" preset="dialog" title="修改信息">
    <template #default>
      <n-card>
        <n-form :model="rowData">
          <n-form-item v-for="(value, key) in rowData" :key="key" :label="key">
            <n-input v-model:value="rowData[key]" :type="key == 'password' ? 'password' : 'text'"
              :disabled="key == 'mobile' ? true : false" show-password-on="mousedown" v-if="key!=='cno'&&key!=='role'"/>
              <n-select v-model:value="rowData[key]" :options="roleOptions" v-else-if="key=='role'"/>
            <n-select v-model:value="rowData[key]" :options="cardOptions" v-else-if="key=='cno'"/>
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
  <n-modal v-model:show="showInsert" style="width:400px" preset="dialog" title="添加用户">
    <template #default>
      <n-card>
        <n-form :model="insertData">
          <n-form-item label="Mobile" path="insertData.mobile">
            <n-input v-model:value="insertData.mobile" />
          </n-form-item>
          <n-form-item label="Username" path="insertData.userName">
            <n-input v-model:value="insertData.userName" />
          </n-form-item>
          <n-form-item label="Password" path="insertData.password">
            <n-input v-model:value="insertData.password" />
          </n-form-item>
          <n-form-item label="role" path="insertData.role">
                <n-select v-model:value="insertData.role" placeholder="权限" :options="roleOptions" />
            </n-form-item>
          <n-form-item label="Cno" path="insertData.cno">
                <n-select v-model:value="insertData.cno" placeholder="卡号" :options="cardOptions" />
            </n-form-item>
        </n-form>
      </n-card>
    </template>
    <template #action>
      <n-space>
        <n-button type="warning" @click="confirmInsert()">确认</n-button>
        <n-button type='info' @click="closeInsert()">取消</n-button>
      </n-space>
    </template>
  </n-modal>

</template>

<script setup>
import { addUser, deleteUserById, showCardsNo, showUsersInfo, updateUser } from '@/api/items/manager';
import { NButton, NTag, NText, useDialog, useMessage } from 'naive-ui';
import { render } from 'naive-ui/es/_utils';
import { h, ref, watchEffect } from 'vue';
const showModel = ref(false)
const showInsert = ref(false)
const cardOptions=ref([
  {
    label: '空',
    value: null
  }
])
// 操作数据后更新视图
const dialog = useDialog()
function createColumns({
  play
}) {
  return [
    {
      title: "Mobile",
      key: "mobile"
    },
    {
      title: "Username",
      key: "userName"
    },
    {
      title: "Password",
      key: "password",
      render(row){
        return h(
          NText,
          {
            style:{
              size: "30"
            },
            type: "error"
          },{
            default: ()=>"******"
          }
        )
      }
    },
    {
      title: "Role",
      key: "role",
      render(row){
        return h(
          NTag,
          {
            style: {
                marginRight: "6px"
            },
            type: "info",
            bordered: false
          },
          {
            default: ()=>{
              if(row.role=='0'){
                return "卡用户"
              }else if(row.role=='1'){
                return "卡业务员"
              }else if(row.role=='2'){
                return "系统管理员"
              }
            }
          }
        )
        
      }
    },
    {
      title: "Cno",
      key: "cno"
    },
    {
      title: "操作",
      key: "shows",
      render(row) {
        return h(
          NButton,
          {
            strong: true,
            // tertiary: true,
            type: 'primary',
            size: 'medium',
            onClick: () => update(row)
          },
          {
            default: () => '修改'
          }
        )
        // h(
        //   NButton,
        //   {
        //     strong: true,
        //     type: 'error',
        //     size: 'medium',
        //     onClick: () => del(row)
        //   },
        //   {
        //     default: () => '删除'
        //   }
        // )
      }
    },
    {
      title: "",
      key: "delete",
      render(row) {
        return h(
          NButton,
          {
            strong: true,
            type: 'error',
            size: 'medium',
            onClick: () => deleterow(row)
          },
          {
            default: () => '删除'
          }
        )

      }
    }
  ]
}
const rowData = ref([])

function update(row) {
  const { mobile, userName, password, role, cno } = row
  console.log(mobile, userName, password, role, cno)
  rowData.value = { mobile, userName, password, role, cno }
  showModel.value = true
  cardOptions.value=[{
    label: '空',
    value: null
  }]
  showCardsNo().then(res=>{
    console.log(res.data.data)
    for( const data in res.data.data){
      cardOptions.value.push({
        label: res.data.data[data],
        value: res.data.data[data]
      })
    }
  })
  // dialog.info(
  //   {
  //     title: '修改信息',
  //     content:"",
  //     positiveText: '确定',
  //     negativeText: '不确定',
  //     onPositiveClick: () => {
  //           message.success('确定')
  //         },
  //         onNegativeClick: () => {
  //           message.error('不确定')
  //         }
  //   }
  // )
}
function deleterow(row) {
  dialog.error(
    {
      title: '删除用户！',
      content: `是否要删除${row.mobile}用户`,
      positiveText: '确定',
      negativeText: '不确定',
      onPositiveClick: () => {
        deleteUserById(row.mobile).then(res => {
          showUsersInfo().then(
            data => tableData.value = data.data.data
          )
          message.success('删除成功')
        }).catch(err => {
          message.error("删除失败")
        })
      },
      onNegativeClick: () => {
        message.info('取消成功')
      }
    }
  )
}

function confirmdislog() {
  updateUser(rowData.value).then(data => {
    if (data.data.code == 0) { 
      message.success("修改成功") 
    }else{
      message.error("修改失败")
    }
  }).then(
    res => showUsersInfo().then(
      data => tableData.value = data.data.data
    )
  )
    .catch(err => {
      message.error(`修改失败`)
    })
  showModel.value = false
}

function closedislog() {
  showModel.value = false
}


const tableData = ref([])
const message = useMessage()
const columns = createColumns(
  {
    update(row) { }
  },
  {
    deleterow(row) { }
  }
)

watchEffect(async () => {
  const resp = await showUsersInfo()
  tableData.value = await resp.data.data
}
)

const insertData = ref({
  mobile: null,
  userName: null,
  password: null,
  role: null,
  cno: null
})
const roleOptions = [
    {
        label: '用户',
        value: '0'
    },
    {
        label: '校园卡业务员',
        value: '1'
    },
    {
        label: '管理员',
        value: '2'
    }
]
function insert() {
  cardOptions.value=[{
    label: '空',
    value: null
  }]
  showInsert.value = true
  showCardsNo().then(res=>{
    console.log(res.data.data)
    for( const data in res.data.data){
      cardOptions.value.push({
        label: res.data.data[data],
        value: res.data.data[data]
      })
    }
  })
}

function confirmInsert() {
  const { mobile, userName, password, role, cno } = insertData.value
  addUser({ mobile, userName, password, role, cno }).then(res => {
    if (res.data.code == '0') {
      dialog.success(
        '添加成功'
      )

    } else {
      dialog.error('添加失败')
    }
  }).then(
    res => showUsersInfo().then(
      data => tableData.value = data.data.data
    )
  )
    .catch(err => dialog.error("添加异常")
    )
  showInsert.value = false
}
function closeInsert() {
  showInsert.value = false
}
// { mobile: 12345678911,username: "LTC丁真",role: 1,cno: "ruike5dai" },
//     { mobile: 12345678912,username: "孙笑川258",role: 0,cno: "aminuosi" },
//     { mobile: 12345678913,username: "advent",role: 0,cno: "advent" },
//     { mobile: 12345678914,username: "kobe",role: 0,cno: "mambaout" }
// data=computed(()=>{
//   getData()
//   data=tabledata
// })

</script>

<style></style>