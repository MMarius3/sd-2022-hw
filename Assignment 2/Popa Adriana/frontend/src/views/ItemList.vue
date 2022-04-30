<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        @input="filteredItems"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Item</v-btn>
      <v-btn @click="exportPDF">Export PDF</v-btn>
      <v-btn @click="exportCSV">Export CSV</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
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
      selectedString: "",
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
    deleteItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    sellItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    exportPDF() {
      api.items.exportPDF();
    },
    exportCSV() {
      api.items.exportCSV();
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    async filteredItems() {
      if (this.search !== "") {
        api.items.search(this.search).then(() => this.$emit("refresh"));
      } else {
        this.items = await api.items.allItems();
        this.search = "";
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
