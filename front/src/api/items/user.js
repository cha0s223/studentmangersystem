import  {useJwtCheckStore}  from "@/stores/counter"
import axios from "axios"
import { ref } from "vue"

const jwt=ref("")
const url="/user"

function init(){
    const store=useJwtCheckStore()
    jwt.value=store.getJwt()
}

export function showUser(params){
    init();
    return axios.post(
        url+"/info",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

export function modifyPassword(params){
    init();
    return axios.put(
        url+"/mfpwd",
        params,
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

