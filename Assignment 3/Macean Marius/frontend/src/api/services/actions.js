import { PUSH_NOTIFICATION } from "@/api/services/mutations";

export const addNotification = ({ commit }, notification) => {
  commit(PUSH_NOTIFICATION, notification);
};