<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ isNew ? "Create book" : "Edit book" }}
          <v-alert v-if="showAlert" type="error" value="errors">{{
            errors
          }}</v-alert>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field
            v-model="book.quantity"
            label="Quantity"
            type="number"
            min="0"
          />
          <v-text-field
            v-model="book.price"
            label="Price"
            type="number"
            min="0"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteB">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      show: false,
      showAlert: false,
      errors: [],
    };
  },
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    deleteB() {
      api.books.deleteBook(this.book.id);
      this.$emit("refresh");
    },
    persist() {
      this.showAlert = false;
      this.errors = [];
      if (this.isNew) {
        api.books
          .createBook({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      } else {
        api.books
          .editBook(this.book.id, {
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .catch((err2) => {
            this.showAlert = true;
            this.errors = err2.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
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
