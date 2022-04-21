<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="black" dark>
          {{ isNew ? "Create user" : "Edit user" }}
          <v-alert v-if="showAlert" type="error" value="errors">{{
            errors
          }}</v-alert>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field
            v-model="user.password"
            label="Password"
            type="password"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteU">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  data() {
    return {
      show: false,
      showAlert: false,
      errors: [],
    };
  },
  name: "UserDialog",

  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    deleteU() {
      api.users.deleteUser(this.user.id);
      this.$emit("refresh");
    },
    persist() {
      this.errors = [];
      this.showAlert = false;
      if (this.isNew) {
        api.users
          .createUser({
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      } else {
        api.users
          .editUser(this.user.id, {
            id: this.user.id,
            username: this.user.username,
            email: this.user.email,
            password: this.user.password,
          })
          .catch((err) => {
            this.showAlert = true;
            this.errors = err.response.data.message;
          })
          .then(() => {
            if (this.errors.length === 0) {
              this.$emit("refresh");
              this.showAlert = false;
            }
          });
      }
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
