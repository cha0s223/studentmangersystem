import { useJwtCheckStore } from '@/stores/counter'
import axios from 'axios'
const url="/login"
const getLogin=async(params)=>{
    return axios.post(url, params)
}
const jwt=ref("")
function init(){
    const store=useJwtCheckStore()
    jwt.value=store.getJwt()
}

function authJwt(){
    init()
    return axios.get(
        url+"/auth",
        {
            headers:{
                "token": jwt.value
            }
        }
    )
}

function addApplyUser(params){
    init()
    return axios.post(url+"/addApplyUser",params)
}
export { getLogin,authJwt,addApplyUser }