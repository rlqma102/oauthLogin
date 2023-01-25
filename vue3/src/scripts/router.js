import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import Join  from "@/pages/Join.vue";
import OAuthLogin from "@/pages/OAuthLogin.vue";
import NotFound from "@/pages/NotFound.vue";
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
    {path: '/join', component: Join},
    {path: '/redirect/oauth', component: OAuthLogin},
    {path: "/404", name: "notFound",component: NotFound},
    {path: "/:pathMatch(.*)*",redirect: "/404"},
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router;