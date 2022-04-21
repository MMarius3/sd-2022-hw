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
          {{ "Sell item" }}
        </v-toolbar>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Sell" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SellDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.items
        .edit({
          id: this.item.id,
          title: this.item.title,
          author: this.item.author,
          price: this.item.price,
          quantity: this.item.quantity - 1,
          description: this.item.description,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {},
};
</script>

<style scoped></style>
