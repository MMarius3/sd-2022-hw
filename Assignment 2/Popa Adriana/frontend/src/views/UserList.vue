<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      user="userSelected"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Password", value: "password" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      userSelected: {},
    };
  },
  methods: {
    addUser() {
      this.dialogVisible = true;
    },
    editUser(user) {
      this.userSelected = user;
      this.dialogVisible = true;
    },
    deleteUser(user) {
      this.selectedItem = user;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.userSelected = {};
      this.users = await api.users.allUsers();
    },
  },
  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
