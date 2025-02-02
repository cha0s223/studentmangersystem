<template>
    <n-space vertical>
        <n-data-table size="medium" :columns="columns" :data="tableData" :pagination="false" :bordered="false" />
    </n-space>
</template>

<script setup>
import { addCard, deleteApplyCard, showApplyCard, updateApplyCard } from '@/api/items/operator';
import router from '@/router';
import { NButton, NTag, useDialog, useMessage } from 'naive-ui';
import { ref, render } from 'vue';

const columns = ref([])
const tableData = ref([])
const message = useMessage()
const dialog = useDialog()
function getData() {
    showApplyCard().then(res => {
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
                                    if (row[data] == "card") {
                                        return "用户待审批"
                                    }else if(row[data]== "bind"){
                                        return "绑定校园卡"
                                    } 
                                    else {
                                        return "校园卡待审批"
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
    message.success(id)
    updateApplyCard(
        {
            id: id,
            apType: apType == "userCheckcard" ? apType + "Check" : apType + "Checkuser",
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
            updateApplyCard({
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