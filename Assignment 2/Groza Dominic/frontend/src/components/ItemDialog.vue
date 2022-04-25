<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Save new book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="item.title" label="Title"/>
          <v-text-field v-model="item.author" label="Author"/>
          <v-text-field v-model="item.genre" label="Genre"/>
          <v-text-field v-model="item.price" label="Price"/>
          <v-text-field v-model="item.quantity" label="Quantity"/>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteBook">
            Delete Book
          </v-btn>
          <v-btn color="green" @click="sellBook">
            Sell Book
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
const user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.items
            .create({
              title: this.item.title,
              author: this.item.author,
              genre: this.item.genre,
              price: this.item.price,
              quantity: this.item.quantity,
            })
            .then(() => this.$emit("refresh"));
      } else {
        if (user.roles[0] === "ADMIN") {
          api.items
              .edit({
                id: this.item.id,
                title: this.item.title,
                author: this.item.author,
                genre: this.item.genre,
                price: this.item.price,
                quantity: this.item.quantity,
              })
              .then(() => this.$emit("refresh"));
        } else {
          alert("You are not authorized to update");
          location.reload();
        }
      }
    },
    deleteBook() {
      if (user.roles[0] === "ADMIN") {
        api.items
            .delete(this.item.id)
            .then(() => this.$emit("refresh"));
      } else {
        alert("You are not authorized to delete a book");
       location.reload();
      }

    }
    ,
    sellBook() {
      api.items
          .sell(this.item)
          .then(() => this.$emit("refresh"));
    }
    ,
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
