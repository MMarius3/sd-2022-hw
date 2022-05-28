<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Coin Added
        </v-toolbar>
        <v-card-actions>
          <v-btn @click="seenNotification"> OK </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
export default {
  name: "Notification",
  props: {
    notification: Object,
    opened: Boolean,
  },
  methods: {
    seenNotification() {
      console.log(this.notification + " " + this.opened);
      this.$emit("refresh");
    },
  },
};
</script>

<style scoped>
.notification-list {
  position: fixed;
  bottom: 0;
  right: 0;
  margin-right: 15px;
  width: 320px;
}
</style>
