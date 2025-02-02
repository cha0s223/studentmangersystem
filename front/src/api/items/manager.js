import  {useJwtCheckStore}  from "@/stores/counter"
import axios from "axios"
import { ref } from "vue"

const jwt=ref("")
const url="/manager"

function init(){
    const store=useJwtCheckStore()
    jwt.value=store.getJwt()
}


export function showUsersInfo(){
    init()
    console.log(jwt.value)
    return axios.get(
        url+"/info",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function updateUser(params){
    init()
    return axios.put(
        url+"/update",
        params
        ,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function deleteUserById(params){
    init()
    return axios.delete(
        url+"/delete"+`/${params}`,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showLogs(){
    init()
    return axios.get(
        url+"/logs",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function addUser(params){
    init()
    return axios.post(
        url+"/add",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showCardsNo(){
    init()
    return axios.get(
        url+"/cardsNo",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showApplyUser(){
    init()
    return axios.get(
        url+"/getApplyUser",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function deleteApplyUser(id){
    init()
    return axios.delete(
        url+`/deleteApplyUserById/${id}`,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function updateApplyUser(params){
    init()
    return axios.post(
        url+"/updateApplyUser",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}