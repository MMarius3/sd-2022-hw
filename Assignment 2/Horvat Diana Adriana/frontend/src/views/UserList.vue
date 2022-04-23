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
      <v-btn @click="generatePDF">Generate PDF report</v-btn>
      <v-btn @click="generateCSV">Generate CSV report</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser">
        <template v-slot:item="row">
          <tr>
            <td>{{ row.item.name }}</td>
            <td>{{ row.item.email }}</td>
            <td>{{ row.item.roles[0] }}</td>
            <td>
              <v-btn color="red" :disabled="isUnauthorized()" @click="deleteUser(row.item)">
                Delete
              </v-btn>
            </td>
          </tr>
        </template>
    </v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user ="selectedUser"
      @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";

const auth_user = JSON.parse(localStorage.getItem("user"));

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
          value: "name",
        },
        { text: "Email", align: "start", sortable: false, value: "email" },
        { text: "Roles", align: "start", sortable: false, value: "roles" },
        { text: "", align: "start", sortable: false, value: "" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user) {
      console.log("akbscjsdcnksdhcsd");
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },
    deleteUser(user) {
      api.users
        .delete({
          id: user.id,
        })
        .then(() => this.refreshList());
    },
    isUnauthorized(){
      if(auth_user.roles[0] === "ADMIN"){
        return false;
      }else{
        return true;
      }
    },
    generatePDF(){
      api.users
        .generateReport({
          type: "pdf",
        })
        .then(() => this.refreshList());
    },
    generateCSV(){
      api.users
          .generateReport({
            type: "csv",
          })
          .then(() => this.refreshList());
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
