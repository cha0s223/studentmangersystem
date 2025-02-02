import  {useJwtCheckStore}  from "@/stores/counter"
import axios from "axios"
import { ref } from "vue"

const jwt=ref("")
const url="/customer"

function init(){
    const store=useJwtCheckStore()
    jwt.value=store.getJwt()
}

export function showAllTransactions(params){
    init()
    return axios.post(
        url+"/transactionInfo",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showMyCard(params){
    init()
    return axios.post(
        url+"/cardInfo",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function rechargeCard(params){
    init()
    return axios.put(
        url+"/recharge",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function consumeCard(params){
    init()
    return axios.put(
        url+"/consume",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function addApplyCard(params){
    init()
    return axios.post(
        url+"/addApplyCard",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function applyBindCard(params){
    init()
    return axios.post(
        url+"/applyBindCard",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function showApplyedCard(){
    init()
    return axios.get(
        url+"/showApplyedCard",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}