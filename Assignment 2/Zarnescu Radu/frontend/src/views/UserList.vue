<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="userSearch"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="userHeader"
      :items="users"
      :search="userSearch"
      @click:row="editUser"
    ></v-data-table>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="itemSearch"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="itemHeader"
      :items="items"
      :search="itemSearch"
      @click:row="editItem"
    ></v-data-table>
    <v-btn color="success" @click="csvReport">CSV Report</v-btn>
    <v-btn color="error" @click="pdfReport">PDF Report</v-btn>
    <ItemDialogAdmin
      :opened="itemDialogVisible"
      :item="selectedItem"
      @refresh="refreshItems"
    ></ItemDialogAdmin>
    <UserDialog
      :opened="userDialogVisible"
      :user="selectedUser"
      @refresh="refreshUsers"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialogAdmin from "../components/ItemDialogAdmin";
import UserDialog from "../components/UserDialog";

export default {
  name: "UserList",
  components: { ItemDialogAdmin, UserDialog },
  data() {
    return {
      users: [],
      items: [],
      userSearch: "",
      itemSearch: "",
      userHeader: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      itemHeader: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
      ],
      userDialogVisible: false,
      itemDialogVisible: false,
      selectedItem: {},
      selectedUser: {},
    };
  },
  methods: {
    async refreshItems() {
      this.itemDialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    async refreshUsers() {
      this.userDialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    async addUser() {
      this.userDialogVisible = true;
    },
    async editUser(user) {
      this.selectedUser = user;
      this.userDialogVisible = true;
    },
    async addItem() {
      this.itemDialogVisible = true;
    },
    async editItem(item) {
      this.selectedItem = item;
      this.itemDialogVisible = true;
    },
    async csvReport() {
      api.items.export("csv");
    },
    async pdfReport() {
      api.items.export("pdf");
    },
  },
  async created() {
    await this.refreshItems();
    await this.refreshUsers();
  },
};
</script>

<style scoped></style>
