<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.price" label="Price" />
          <v-text-field v-model="book.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteBook"> Delete </v-btn>
          <v-btn @click="sellBook"> Sell </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.books
          .create({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: this.book.price,
            quantity: this.book.quantity,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books
          .edit(this.book.id, {
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            price: this.book.price,
            quantity: this.book.quantity,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteBook() {
      api.books.delete(this.book.id).then(() => this.$emit("refresh"));
    },
    sellBook() {
      api.books.sell(this.book.id).then(() => this.$emit("refresh"));
    },
  },

  computed: {
    isNew: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
