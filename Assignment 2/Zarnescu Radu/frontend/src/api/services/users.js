import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/user/create", user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(user) {
    const id = user.id;
    return HTTP.patch(BASE_URL + "/user/" + id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    const id = user.id;
    return HTTP.delete(BASE_URL + "/user/delete/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
