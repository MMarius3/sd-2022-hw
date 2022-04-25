<template>
  <v-card>
    <v-card-title>
      Administrator Page
      <v-btn @click="crudItems">CRUD on Items</v-btn>
      <v-btn @click="crudUsers">CRUD on Users</v-btn>
      <v-btn @click="pdfBox">Generate PDF report</v-btn>
      <v-btn @click="csv">Generate CSV report</v-btn>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
export default {
  name: "Admin",
  data() {
    return {
      users: [],
      search: "",
      headers: [],
    };
  },
  methods: {
    crudUsers() {
      router.push("/users");
    },
    crudItems() {
      // api.admin.crudItems();
      router.push("/items");
    },
    pdfBox() {
      api.admin.generatePdfReport();
    },
    csv() {
      api.admin.generateCsvReport();
    },
  },
};
</script>

<style scoped></style>
