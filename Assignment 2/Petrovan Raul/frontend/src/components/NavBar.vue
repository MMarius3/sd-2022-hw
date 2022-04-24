<template>
  <nav>
    <v-navigation-drawer v-model="drawer" app class="indigo">
      <v-list>
        <v-list-item v-for="link in accessibleLinks" :key="link" router :to="link.to">
            <v-list-item-action>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>

        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import store from '../store'

export default {
  name: 'NavBar',
  data() {
    return {
      drawer: true,
      links: [
        {icon: '', text: 'Users', to: '/users', requiresPrivilege: true},
        {icon: 'library_books', text: 'Books', to: '/books', requiresPrivilege: false},
        {icon: '', text: 'Report Generator', to: '/reports', requiresPrivilege: true}
      ]
    };
  },
  computed: {
    accessibleLinks: function () {
      return this.links.filter(link => !link.requiresPrivilege || store.getters["auth/isAdmin"]);
    }
  }
}
</script>