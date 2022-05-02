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
      <v-btn @click="addBook">Add Book</v-btn>
      <v-btn @click="filter">Filter Books</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    >
    </v-data-table>
    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookDialog>
    <FilterDialog
      :opened="filterDialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></FilterDialog>
    <v-btn @click="generateReport">Generate Report</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import FilterDialog from "../components/FilterDialog";

export default {
  name: "BookList",
  components: { BookDialog, FilterDialog },
  data() {
    return {
      books: [],
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
      selectedBook: {},
      filterDialogVisible: false,
      selectedFilters: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    generateReport() {
      console.log(api.books.exportReport().then(() => this.$emit("refresh")));
    },
    addBook() {
      this.dialogVisible = true;
    },
    filter() {
      this.filterDialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
