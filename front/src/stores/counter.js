import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})

export const useJwtCheckStore = defineStore('JwtCheck',()=>{
  const jwtString=ref("");
  function getJwt(){
    if(localStorage.getItem("jwt_check")!==null){
      jwtString.value=localStorage.getItem("jwt_check");
    }
    return jwtString.value
  }
  function setJwt(jwt){
    localStorage.setItem("jwt_check",jwt)
    jwtString.value=jwt
  }
  return {jwtString,getJwt,setJwt}
})
