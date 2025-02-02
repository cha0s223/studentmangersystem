<template>
    <n-card class="login" hoverable>
        <n-form ref="formRef" :model="model" :rules="rules" label-placement="left" label-width="auto"
            require-mark-placement="right-hanging" :size="medium">
            <n-form-item label="账户：" path="mobile">
                <n-input v-model:value="model.mobile" placeholder="账户" />
            </n-form-item>
            <n-form-item label="密码：" path="password">
                <n-input type="password" v-model:value="model.password" placeholder="密码" />
            </n-form-item>
            <n-form-item label="权限" path="role">
                <n-select v-model:value="model.role" placeholder="权限" :options="roleOptions" />
            </n-form-item>
            <div style="display: flex; justify-content: center;">
                <n-button type="primary" @click="submit" :disabled="String(model.account).length == 0">
                    登录
                </n-button>
            </div>
        </n-form>
    </n-card>
</template>

<script setup>
import { getLogin } from "@/api/auth/login";
import router from "@/router";
import { useJwtCheckStore } from "@/stores/counter";
import { useMessage } from "naive-ui";
import { ref } from "vue";


const formRef = ref(null);
const message = useMessage();
const model = ref({
    mobile: "admin",
    password: "123456",
    role: 2
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

const rules = {
    mobile: [{
        required: true,
        trigger: ["blur", "input"],
        validator(rule, value) {
            if (!value) {
                return new Error("需要账户！")
            }
            else if (!/^[0-9A-Za-z]*$/.test(value)) {
                return new Error("账户字符错误！")
            }
            else if (String(value).length < 4) {
                return new Error("账户长度不够")
            }
            return true;
        }
    }],
    password: [{
        required: true,
        trigger: ["blur", "input"],
        validator(rule, value) {
            if (String(value).length < 4) {
                return new Error("密码长度不够")
            }
            return true;

        }
    }]
}

const store=useJwtCheckStore()
function submit(e) {
    e.preventDefault();
    formRef.value?.validate((errors) => {
        if (!errors) {
            message.success("验证成功");
            const params = {
                mobile: model.value.mobile,
                password: model.value.password,
                role: model.value.role
            }
            getLogin(params).then(data => {
                const resp=data.data
                console.log(resp)
                if (resp.code == 0) {
                    console.log(resp.data)
                    store.setJwt(resp.data)
                    if(model.value.role==2){
                        router.push({ name: "home" });
                    }else if(model.value.role==1){
                        router.push({ name: "operatorMain"})
                    }else if(model.value.role==0){
                        router.push({name:"customerMain"})
                    }else{
                        router.push({path: "/"})
                    }
                } else {
                    message.error(resp.msg);
                    router.push({path: "/"})
                }
            })

        } else {
            console.log(errors);
            message.error("验证失败");
        }
    });

}
</script>

<style scoped>

</style>