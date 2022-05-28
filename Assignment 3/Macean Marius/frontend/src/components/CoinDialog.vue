<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create coin" : "Edit coin" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="coin.pair" label="Coin" />
          <v-text-field v-model="coin.top" label="Top" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteCoin"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CoinDialog",
  props: {
    coin: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.coins
          .createCoin({
            pair: this.coin.pair,
            top: this.coin.top,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.coins
          .editCoin(this.coin.id, {
            pair: this.coin.pair,
            top: this.coin.top,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteCoin() {
      api.coins.deleteCoin(this.coin.id).then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.coin.id;
    },
  },
};
</script>

<style scoped></style>
