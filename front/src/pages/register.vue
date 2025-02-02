<template>
    <n-card class="register" hoverable>
        <n-form ref="formRef" :model="model" :rules="rules" size="large" label-placement="left">
            <n-form-item path="mobile" label="账户">
                <n-input v-model:value="model.mobile" @keydown.enter.prevent />
            </n-form-item>
            <n-form-item label="用户名" path="model.userName">
                <n-input v-model:value="model.userName" @keydown.enter.prevent/>
            </n-form-item>
            <n-form-item label="权限" path="role">
                <n-select v-model:value="model.role" placeholder="权限" :options="roleOptions" />
            </n-form-item>
            <n-form-item path="password" label="密码">
                <n-input v-model:value="model.password" type="password" @input="handlePasswordInput"
                    @keydown.enter.prevent />
            </n-form-item>
            <n-form-item ref="rPasswordFormItemRef" first path="reenteredPassword" label="重复密码">
                <n-input v-model:value="model.reenteredPassword" :disabled="!model.password" type="password"
                    @keydown.enter.prevent />
            </n-form-item>
            
            <n-row :gutter="[0, 24]">
                <n-col :span="24">
                    <div style="display: flex; justify-content: center">
                        <n-button :disabled="model.account === null" type="primary"
                            @click="handleValidateButtonClick">
                            注册
                        </n-button>
                    </div>
                </n-col>
            </n-row>
        </n-form>
    </n-card>
</template>

<script setup>
import { addApplyUser } from "@/api/auth/login";
import router from "@/router";
import { useMessage } from "naive-ui";
import { computed, ref } from "vue";


const formRef = ref(null);
const rPasswordFormItemRef = ref(null);
const message = useMessage();
const modelRef = ref({
    mobile: null,
    password: null,
    reenteredPassword: null,
    userName: null,
    role: 0
});
const roleOptions = [
    {
        label: '用户',
        value: 0
    },
    {
        label: '校园卡业务员',
        value: 1
    },
    {
        label: '管理员',
        value: 2
    }
]
function validatePasswordStartWith(rule, value) {
    return !!modelRef.value.password && modelRef.value.password.startsWith(value) && modelRef.value.password.length >= value.length;
}
function validatePasswordSame(rule, value) {
    return value === modelRef.value.password;
}
const rules = {
    mobile: [
        {
            required: true,
            validator(rule, value) {
                if (!value) {
                    return new Error("需要账户！")
                }
                else if (!/^[0-9A-Za-z]*$/.test(value)) {
                    return new Error("账户字符错误！")
                }
                else if ( String(value).length < 4) {
                    return new Error("账户长度不够")
                }
                return true;
            },
            trigger: ["input", "blur"]
        }
    ],
    password: [
        {
            required: true,
            message: "请输入密码"
        }
    ],
    reenteredPassword: [
        {
            required: true,
            message: "请再次输入密码",
            trigger: ["input", "blur"]
        },
        {
            validator: validatePasswordStartWith,
            message: "两次密码输入不一致",
            trigger: "input"
        },
        {
            validator: validatePasswordSame,
            message: "两次密码输入不一致",
            trigger: ["blur", "password-input"]
        }
    ],
    userName:[
        {
            require: true,
            message: "请输入用户名"
        }
    ]
    
};
const model = ref(modelRef);
function handlePasswordInput() {
    if (modelRef.value.reenteredPassword) {
        rPasswordFormItemRef.value?.validate({ trigger: "password-input" });
    }
}
function handleValidateButtonClick(e) {
    e.preventDefault();
    formRef.value?.validate((errors) => {
        if (!errors) {
            message.success("验证成功");
            const applyData={
                mobile: model.value.mobile,
                userName: model.value.userName,
                password: model.value.password,
                role: model.value.role
            }
            addApplyUser(applyData)
                .then(res=>res.data.code=="0"?message.success("申请成功"):message.error("申请失败"))
                .catch(err=>message.error("请求失败"));
            router.go(0);
        } else {
            console.log(errors);
            message.error("验证失败");
        }
    });
}
</script>

<style scoped>
.register {
    max-width: 600px;
}
</style>