<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteUser"> Delete </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.users
          .create({
            email: this.user.email,
            username: this.user.username,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.users
          .edit(this.user.id, {
            email: this.user.email,
            username: this.user.username,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteUser() {
      api.users.delete(this.user.id).then(() => this.$emit("refresh"));
    },
  },

  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
