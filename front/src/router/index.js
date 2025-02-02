import { createRouter, createWebHistory } from "vue-router";
import headerVue from "../layout/default/index/header.vue";
import mainVue from "../layout/default/store/main.vue";
import homeVue from "../views/home.vue";
import aboutVue from "../views/about.vue";
import page1Vue from "@/pages/home/page1.vue";
import page2Vue from "@/pages/home/page2.vue";
import page3Vue from "@/pages/home/page3.vue";
import OperatorMain from "@/layout/operator/operatorMain.vue";
import OpratorPage1 from "@/pages/operator/opratorPage1.vue";
import OpratorPage2 from "@/pages/operator/opratorPage2.vue";
import OpratorPage3 from "@/pages/operator/opratorPage3.vue";
import CustomerMain from "@/layout/customer/customerMain.vue";
import CustomerPage1 from "@/pages/customer/customerPage1.vue";
import CustomerPage2 from "@/pages/customer/customerPage2.vue";
import CustomerPage3 from "@/pages/customer/customerPage3.vue";
import CustomerPage4 from "@/pages/customer/customerPage4.vue";
import { useJwtCheckStore } from "@/stores/counter";
import { authJwt } from "@/api/auth/login";
import Page4Vue from "@/pages/home/page4.vue";
import OpratorPage4 from "@/pages/operator/opratorPage4.vue";
import CustomerPage6 from "@/pages/customer/customerPage6.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "login",
      component: headerVue,
    },
    {
      path: "/login",
      redirect: { name: "login" },
    },
    {
      path: "/main",
      name: "main",
      component: mainVue,
      children: [
        {
          path: "home",
          name: "home",
          component: homeVue,
          children: [
            {
              path: "page1",
              name: "page1",
              component: page1Vue,
            },
            {
              path: "",
              redirect: { name: "page1" },
            },
            {
              path: "page2",
              name: "page2",
              component: page2Vue,
            },
            {
              path: "page3",
              name: "managerPage3",
              component: page3Vue,
            },
            {
              path: "page4",
              name: "managerPage4",
              component: Page4Vue
            }
          ]
        },
        {
          path: "",
          redirect: { name: "home" },
        },
        {
          path: "about",
          name: "about",
          component: aboutVue,
        },
      ],
    },
    {
      path: "/operator",
      name: "operatorMain",
      component: OperatorMain,
      children: [
        {
          path: "page1",
          name: "operatorPage1",
          component: OpratorPage1,
        },
        {
          path: "page2",
          name: "operatorPage2",
          component: OpratorPage2,
        },
        {
          path: "page3",
          name: "operatorPage3",
          component: OpratorPage3,
        },
        {
          path: "page4",
          name: "operatorPage4",
          component: OpratorPage4
        }
      ]
    },
    {
      path: "/customer",
      name: "customerMain",
      component: CustomerMain,
      children: [
        {
          path: "page1",
          name: "customerPage1",
          component: CustomerPage1,
        },
        {
          path: "page2",
          name: "customerPage2",
          component: CustomerPage2,
        },
        {
          path: "page3",
          name: "customerPage3",
          component: CustomerPage3,
        },
        {
          path: "page4",
          name: "customerPage4",
          component: CustomerPage4,
        },
        {
          path: "page5",
          name: "customerPage5",
          component: OpratorPage3,
        },
        {
          path: "page6",
          name: "customerPage6",
          component: CustomerPage6
        }
      ],
    },
  ],
});

router.beforeEach(async (to, from) => {
  if (to.path !== "/") {
    const store = useJwtCheckStore();
    if (store.getJwt() == null) {
      window.alert("未登录")
      return { name: "login" };
    }
    const authResult = await authJwt();
    console.log(authResult.data)
    if (authResult.data.code == "1") {
      window.alert(authResult.data.msg)
      return { name: "login" };
    }
    return true
  }
});
export default router;
