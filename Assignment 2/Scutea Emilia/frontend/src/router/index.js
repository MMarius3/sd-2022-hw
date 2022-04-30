import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import ItemList from "../views/ItemList.vue";
import Admin from "../views/Admin.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import Bookstore from "../views/Bookstore";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Items" });
      }
    },
  },
  {
    path: "/bookstore",
    name: "Bookstore",
    component: Bookstore,
  },
  {
    path: "/items",
    name: "Items",
    component: ItemList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Admin.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
