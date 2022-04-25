<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
    @click:outside="$emit('close')"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form @submit="checkForm">
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.price" label="Price" />
          <v-text-field v-model="item.quantity" label="Quantity" />
          <v-text-field v-model="item.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
        <v-card-actions>
          <v-btn @click="deleteItem">
            {{ isNew ? "" : "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    checkForm: function () {

          return true //TODO
      //   this.title &&
      //   this.author &&
      //   this.price &&
      //   this.quantity &&
      //   this.description
      // )
      //   return true;
      // this.errors = [];
      // if (!this.title) this.errors.push("Name required.");
      // if (!this.author) this.errors.push("Age required.");
      // if (!this.price) this.errors.push("Age required.");
      // if (!this.quantity) this.errors.push("Age required.");
      // if (!this.description) this.errors.push("Age required.");
      // e.preventDefault();
    },
    persist() {
      if (this.isNew) {
        api.items
          .create({
            title: this.item.title,
            author: this.item.author,
            price: this.item.price,
            quantity: this.item.quantity,
            description: this.item.description,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.items
          .edit({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            price: this.item.price,
            quantity: this.item.quantity,
            description: this.item.description,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteItem() {
      if (!this.isNew) {
        console.log("delete");
        api.items
          .deleteItem({
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            price: this.item.price,
            quantity: this.item.quantity,
            description: this.item.description,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
