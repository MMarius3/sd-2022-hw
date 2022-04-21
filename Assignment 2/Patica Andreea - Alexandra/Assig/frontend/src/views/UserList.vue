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
      :item="selectedUser"
      @refresh="refreshList"
      @close="dialogVisible = !dialogVisible"
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
          //TODO add roles
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },
    async refreshList() {
      console.log("aaaaaaaaaaaaa");
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
      console.log("bbbbbbbbbbbbbbb");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
