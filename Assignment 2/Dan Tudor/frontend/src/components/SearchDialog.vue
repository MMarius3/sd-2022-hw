<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-text-field v-model="item.title" label="Title" />
          <v-text-field v-model="item.author" label="Author" />
          <v-text-field v-model="item.genre" label="Genre" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Search </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SearchDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.items
        .search({
          title: this.item.title,
          author: this.item.author,
          genre: this.item.genre,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {

  },
};
</script>

<style scoped></style>
