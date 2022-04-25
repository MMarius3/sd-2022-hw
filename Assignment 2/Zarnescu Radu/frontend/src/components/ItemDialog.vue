<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>Sell Book</v-toolbar>
        <v-form>
          <v-text-field v-model="item.quantity" label="Quantity" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">Sell</v-btn>
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
    persist() {
      api.items
        .sell(
          {
            id: this.item.id,
            title: this.item.title,
            author: this.item.author,
            genre: this.item.genre,
            quantity: this.item.quantity,
          },
          "items"
        )
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
