import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/items/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/items/users" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
