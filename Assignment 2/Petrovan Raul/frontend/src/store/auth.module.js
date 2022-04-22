import api from "../api";

const user = JSON.parse(localStorage.getItem("user"));
const initialState = user
  ? { status: { loggedIn: true }, user, drawer: false }
  : { status: { loggedIn: false }, user: null, drawer: false };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    async login({ commit }, user) {
      return api.auth.login(user).then(
        (user) => {
          commit("loginSuccess", user);
          return Promise.resolve(user);
        },
        (error) => {
          commit("loginFailure");
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }) {
      api.auth.logout();
      commit("logout");
    },
    register({ commit }, user) {
      return api.auth.register(user).then(
        (response) => {
          commit("registerSuccess");
          return Promise.resolve(response.data);
        },
        (error) => {
          commit("registerFailure");
          return Promise.reject(error);
        }
      );
    },
    toggleDrawer({ commit }) {
      commit("toggleDrawer");
    },
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
      if(state.drawer) {
        state.drawer = false;
      }
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    },
    toggleDrawer(state) {
      state.drawer = !state.drawer;
    },
  },
  getters: {
    isAdmin: (state) => {
      return state.user.roles.includes("ADMIN");
    },
    drawer: (state) => {
      console.log('ye');
      return state.drawer;
    },
  },
};
