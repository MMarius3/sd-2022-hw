import {createRouter, createWebHistory, RouteMeta, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import {mapGetters} from "vuex";
import store from "@/store";

interface Route {
    path: string,
    name: string,
    component: any,
    meta: any,
}

const createRoute = (path: string, name: string, component: any, meta: RouteMeta = { requiresAuth: false, roles: [] }): Route => ({
    path,
    name,
    component,
    meta
})

const routes: Array<RouteRecordRaw> = [
    createRoute('/', 'home', HomeView),
    createRoute('/signUp', 'signUp', () => import( '../views/SignUp.vue')),
    createRoute('/signIn', 'signIn', () => import( '../views/SignIn.vue')),
    createRoute('/about', 'about', () => import('../views/AboutView.vue')),
    createRoute('/customer', 'customer', () => import('../views/CustomerView.vue'), {
        requiresAuth: true,
        roles: ['CUSTOMER']
    }),
    createRoute('/admin', 'admin', () => import('../views/AdminView.vue'), {
        requiresAuth: true,
        roles: ['ADMIN']
    })
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    const roles = store.getters.roles;
    const isAuthenticated = store.getters.isAuthenticated;
    if (to.meta.requiresAuth === false) {
        next();
    } else {
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        if (to.meta.requiresAuth && isAuthenticated && (to.meta.roles.length === 0 || roles.filter(role => to.meta.roles.includes(role)).length > 0)) {
            next();
        } else {
            next('/')
        }
    }
})

export default router
