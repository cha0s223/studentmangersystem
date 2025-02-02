import  {useJwtCheckStore}  from "@/stores/counter"
import axios from "axios"
import { ref } from "vue"

const jwt=ref("")
const url="/operator"

function init(){
    const store=useJwtCheckStore()
    jwt.value=store.getJwt()
}

export function showCards(){
    init();
    return axios.get(
        url+"/info",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}
export function addCard(params){
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

export function updateCard(params){
    init()
    return axios.put(
        url+"/update",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}
export function deleteCardById(params){
    init()
    return axios.delete(
        url+`/delete/${params}`,
        {
            headers: {
                "token": jwt.value
            }
        }
    )
}

export function findAllTransactions(){
    init()
    return axios.get(
        url+"/getAllTransactions",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function findTransaction(params){
    init()
    return axios.get(
        url+"/findTransaction",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showApplyCard(){
    init()
    return axios.get(
        url+"/getApplyCard",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function updateApplyCard(params){
    init()
    return axios.post(
        url+"/updateApplyCard",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function deleteApplyCard(id){
    init()
    return axios.delete(
        url+`/deleteApplyCardById/${id}`,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}
