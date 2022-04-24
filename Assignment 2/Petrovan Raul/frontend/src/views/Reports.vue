<template>
  <v-card>
    <v-card-title>
      Reports
    </v-card-title>
    <v-btn @click="getPDF">
      <v-icon>mdi-file-document-outline</v-icon>
      <span>PDF Report</span>
    </v-btn>
    <v-btn @click="getCSV">
      <v-icon>mdi-file-document-outline</v-icon>
      <span>CSV Report</span>
    </v-btn>
  </v-card>
</template>

<script>
import api from "@/api";

export default {
  name: "Reports",
  methods: {
    getPDF() {
      api.reports.getPDF();
    },
    getCSV() {
      api.reports.getCSV();
    },
  },
};
</script>