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
          <v-text-field v-model="user.password" label="Password" />
          <v-text-field v-model="user.email" label="Email" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create user" : "Save user" }}
          </v-btn>
          <v-btn @click="deleteUser">Delete user</v-btn>
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
      this.errors = [];
      if (this.isNew) {
        api.users
          .create({
            name: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .catch((err) => {
            alert(err.response.data.message);
          })
          .then(() => {
            this.$emit("refresh");
          });
      } else {
        api.users
          .edit({
            id: this.user.id,
            name: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteUser() {
      api.users
        .delete({
          id: this.user.id,
        })
        .then(() => this.$emit("refresh"));
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
