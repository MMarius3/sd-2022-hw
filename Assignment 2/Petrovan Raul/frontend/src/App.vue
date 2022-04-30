<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <NavBar v-if="drawer"></NavBar>
        <TopBar v-if="isLoggedIn"></TopBar>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import TopBar from "./components/TopBar";
import NavBar from "@/components/NavBar";
import {auth as store} from "@/store/auth.module";
export default {
  name: "App",
  components: { NavBar, TopBar },
  data: () => ({
    //
  }),
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
    drawer: function () {
      return store.getters.drawer(store.state);
    },
  },
};
</script>
