<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> </v-toolbar>
        <v-form>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.genre" label="Genre" />
          <v-text-field v-model="item.price" label="Price" />
          <v-text-field v-model="item.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="sellButton">Sell </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ItemSell",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    sellButton() {
      api.items
        .sellItem({
          id : this.item.id,
          title: this.item.title,
          author: this.item.author,
          genre: this.item.genre,
          price: this.item.price,
          quantity: this.item.quantity,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
