<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        @input="filterBooks"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <v-btn @click="generatePDF">pdf out of stock</v-btn>
    <v-btn @click="generateCSV">csv out of stock</v-btn>
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
  name: "ItemList",
  components: { ItemDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
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
    generatePDF() {
      api.items.generatePDF();
    },
    generateCSV() {
      api.items.generateCSV();
    },
    editBook(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    deleteBook(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    sellBook(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    async filterBooks() {
      if (this.search !== "") {
        this.items = await api.items.filterBy(this.search);
      } else {
        this.search = "";
        this.items = await api.items.allItems();
      }
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
