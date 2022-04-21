<template>
  <v-card>
    <v-btn @click="viewItems">View Items</v-btn>
    <v-btn @click="viewUsers">View Users</v-btn>
    <v-btn @click="generateReportPDF">Generate Report PDF</v-btn>
    <v-btn @click="generateReportCSV">Generate Report CSV</v-btn>
  </v-card>
</template>

<script>
import router from "../router";
import api from "../api";

export default {
  name: "AdminOptions",
  methods: {
    viewItems() {
      router.push("/admin-items");
    },
    viewUsers() {
      router.push("/users");
    },
    generateReportPDF() {
      console.log("PDF");
      api.items.generateReport("PDF");
    },
    generateReportCSV() {
      console.log("CSV");
      api.items.generateReport("CSV");
    },
  },
};
</script>
