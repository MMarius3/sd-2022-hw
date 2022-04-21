<template>
  <v-card>
    <v-card-title>
      Reports with out-of-stock books
      <v-spacer></v-spacer>
      <v-btn @click="exportPDF">PDF Report</v-btn>
      <v-btn @click="exportCSV">CSV Report</v-btn>
    </v-card-title>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "ReportList",
  methods: {
    exportPDF() {
      api.books.exportReport("PDF");
    },
    exportCSV() {
      api.books.exportReport("CSV");
    },
  },
};
</script>

<style scoped></style>
