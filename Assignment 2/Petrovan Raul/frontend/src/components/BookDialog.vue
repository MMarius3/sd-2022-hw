<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
      persistent
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create Book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" :class="{ 'hasError':
          $v.book.title.$error}"/>
          <v-text-field v-model="book.author" label="Author"/>
          <v-select
              :items="genres"
              label="Genre"
              solo
              v-model="book.genre"
          ></v-select>
          <v-text-field v-model="book.price" label="Price"/>
          <v-text-field v-model="book.quantity" label="Quantity"/>
          <v-textarea v-model="book.description" label="Description" rows="3"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist" v-if="isAdmin">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="sell" v-if="book.quantity > 0">Sell Book</v-btn>
          <v-btn @click="close">Cancel</v-btn>
          <v-btn @click="deleteBook" v-if="isAdmin && !isNew">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import {auth as store} from "../store/auth.module";
import {required} from "vuelidate/lib/validators";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
    genres: Array,
  },
  validations: {
    book: {
      title: { required },
    },
  },
  methods: {
    persist() {
      this.$v.book.$touch();
      if (this.$v.book.$error) {
        return;
      }
      if (this.isNew) {
        api.books
            .create({
              author: this.book.author,
              description: this.book.description,
              title: this.book.title,
              genre: this.book.genre,
              price: this.book.price,
              quantity: this.book.quantity,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.books
            .edit({
              id: this.book.id,
              author: this.book.author,
              description: this.book.description,
              title: this.book.title,
              genre: this.book.genre,
              price: this.book.price,
              quantity: this.book.quantity,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    sell() {
      api.books
          .sell({
            id: this.book.id,
          })
          .then(() => this.$emit("refresh"));
    },
    close() {
      this.$emit("close");
    },

    deleteBook() {
      api.books
          .deleteBook({
            id: this.book.id,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
    isAdmin: function () {
      return store.getters.isAdmin(store.state);
    },
  },
};
</script>

<style scoped></style>
