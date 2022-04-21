import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  createUser(user) {
    return HTTP.post(BASE_URL + "/user", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  editUser(id, user) {
    return HTTP.put(BASE_URL + "/user/" + id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteUser(id) {
    return HTTP.delete(BASE_URL + "/user/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterAll(filter) {
    return HTTP.get(BASE_URL + "/user/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
