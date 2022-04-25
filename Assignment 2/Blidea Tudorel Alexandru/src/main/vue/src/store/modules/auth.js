import HTTP from "@/service/HTTP";

const state = {
    logInDetails: {
        id: 0,
        token: "",
        username: "",
        email: "",
        roles: [],
    },
    isAuthenticated: false,
};

const getters = {
    username: () => state.logInDetails.username,
    email: () => state.logInDetails.email,
    roles: () => state.logInDetails.roles,
    isAuthenticated: () => state.isAuthenticated,
};

const actions = {
    signIn: async ({ commit }, logInModel) => {
        const logInDetailsResponse = (await HTTP.post('/auth/sign-in', logInModel)).data;
        commit('saveLogInDetails', logInDetailsResponse);
    },

    logOut: ({ commit }) => {
        commit('logOut')
    }
};

const mutations = {
    saveLogInDetails: (state, logInDetails) => {
        state.logInDetails = logInDetails;
        state.isAuthenticated = true;
    },

    logOut: (state) => {
        state.logInDetails = {
            id: 0,
            token: "",
            username: "",
            email: "",
            roles: [],
        }
        state.isAuthenticated = false;
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}
