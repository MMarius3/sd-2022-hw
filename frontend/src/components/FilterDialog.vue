<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Add Filters </v-toolbar>
        <v-form>
          <v-text-field v-model="title" label="Title" />
          <v-text-field v-model="author" label="Author" />
          <v-text-field v-model="genre" label="Genre" />
          <v-text-field v-model="quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="filterBooks"> Filter </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "FilterDialog",
  props: {
    title: String,
    author: String,
    genre: String,
    quantity: Number,
    opened: Boolean,
  },
  methods: {
    filterBooks() {
      api.books
        .allFilteredBooks(
          this.title,
          this.author,
          this.genre,
          this.quantity
        )
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
