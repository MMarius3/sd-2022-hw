<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <v-btn @click="generateReportPDF" color="yellow">Generate PDF</v-btn>
    <v-btn @click="generateReportCSV" color="blue">Generate CSV</v-btn>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";

export default {
  name: "Book list",
  components: { ItemDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {text: "Title", align: "start", sortable: false, value: "title",},
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();

    },
    generateReportPDF(){
      api.items.report("PDF");
    },
    generateReportCSV(){
      api.items.report("CSV");
    }
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
