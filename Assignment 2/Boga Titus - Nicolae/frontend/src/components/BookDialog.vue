<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>

        <v-card-actions>
          <v-btn @click="sellBook">
            {{ " Sell " }}
          </v-btn>
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
    item: Object,
    opened: Boolean,
  },
  methods: {
    sellBook() {
      api.items
        .edit({
          id: this.item.id,
          title: this.item.title,
          author: this.item.author,
          genre: this.item.genre,
          quantity: this.item.quantity - 1,
          price: this.item.price,
        })
        .then(() => this.$emit("refresh"));
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
