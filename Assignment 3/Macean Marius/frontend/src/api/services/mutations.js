export const PUSH_NOTIFICATION = (state, notification) => {
  alert(state.notifications);
  state.notifications.push({
    ...notification,
    id: (Math.random().toString(36) + Date.now().toString(36)).substring(2),
  });
};
