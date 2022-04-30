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
        @input="searchAllBy"
      ></v-text-field>
      <v-btn @click="sellBook">Sell Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      @click:row="selectBookToSell"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "SellBookList",
  data() {
    return {
      books: [],
      search: "",
      items: ["Title", "Author", "Genre", "All"],
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      selectedBook: {},
    };
  },
  methods: {
    selectBookToSell(book) {
      this.selectedBook = book;
    },
    sellBook: function () {
      api.books.editBook(this.selectedBook.id, {
        id: this.selectedBook.id,
        title: this.selectedBook.title,
        author: this.selectedBook.author,
        genre: this.selectedBook.genre,
        quantity: this.selectedBook.quantity - 1,
        price: this.selectedBook.price,
      });
      this.splice(this.books, this.selectedBook);
      this.selectedBook = {};
      this.refreshList();
    },
    async refreshList() {
      this.books = await api.books.allBooks();
    },
    async searchAllBy() {
      if (this.search !== "") {
        this.books = await api.books.filterAll(this.search);
      } else {
        this.search = "";
        this.books = await api.books.allBooks();
      }
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
