<template>
  <div class="notification-list">
    <notification
      v-for="notification in notifications"
      :key="notification.id"
      :notification="notification"
    />
  </div>
</template>

<script>
import Notification from "@/components/Notification";
import { mapState } from "vuex";

export default {
  components: {
    Notification,
  },
  name: "NotificationList",
  computed: mapState(["notifications"]),
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
