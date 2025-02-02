<template>
    <n-space vertical>
        <n-data-table size="medium" :columns="columns" :data="tableData" :pagination="false" :bordered="false" />
    </n-space>

</template>

<script setup>
import { addUser, deleteApplyUser, showApplyUser, updateApplyUser } from '@/api/items/manager';
import router from '@/router';
import { NButton, NTag, useDialog, useMessage } from 'naive-ui';
import { ref, render } from 'vue';

const columns = ref([])
const tableData = ref([])
const message = useMessage()
const dialog = useDialog()
function getData() {
    showApplyUser().then(res => {
        tableData.value = res.data.data
        for (let data in tableData.value[0]) {
            if (data == "apType") {
                columns.value.push({
                    title: data,
                    key: data,
                    render(row) {
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
                                default: () => {
                                    if (row[data] == "user") {
                                        return "校园卡待审批"
                                    } else if(row[data]=="bindCheckuser"){
                                        return "校园卡绑定审批"
                                    }
                                    else {
                                        return "用户待审批"
                                    }
                                }
                            }
                        );
                    }
                })
            } else {
                columns.value.push({
                    title: data,
                    key: data,
                    ellipsis: {
                        tooltip: true
                    }
                })
            }

        }
        columns.value.push({
            title: "操作",
            key: "action",
            render(row) {
                return h(
                    NButton,
                    {
                        strong: true,
                        type: "primary",
                        size: "medium",
                        onclick: () => confirmApply(row)
                    },
                    {
                        default: () => "确认"
                    }
                )
            }
        }, {
            title: "",
            key: "action1",
            render(row) {
                return h(
                    NButton,
                    {
                        strong: true,
                        type: "error",
                        size: "medium",
                        onclick: () => deleteApply(row)
                    },
                    {
                        default: () => "拒绝"
                    }
                )
            }
        }
        )

    }
    )
}
getData()

function confirmApply(row) {
    const { id, apType, apData } = row
    updateApplyUser(
        {
            id: id,
            apType: apType == "user" ? apType + "Checkcard" : apType + "Check",
            apData: apData
        }).then(res => {
            if (res.data.code == 0) {
                message.success("审批成功")
                router.go(0)
            } else {
                message.error("审批失败")
            }
        })
}
function deleteApply(row) {
    dialog.warning({
        title: "拒绝申请",
        content: `拒绝${row.id}的申请?`,
        positiveText: '拒绝',
        negativeText: '取消',
        onPositiveClick: () => {
            const { id, apType, apData } = row
            updateApplyUser({
                id: id,
                apType: apType+"fail",
                apData: apData
            }).then(res => {
                if (res.data.code == '0') {
                    message.success("拒绝成功")
                } else {
                    message.error(res.data.msg)
                }
            }).catch(err => {
                message.error("拒绝错误！")
            })
            router.go(0)
        },
        onNegativeClick: () => {
            message.success('取消成功')
        }
    })
}
</script>

<style></style>