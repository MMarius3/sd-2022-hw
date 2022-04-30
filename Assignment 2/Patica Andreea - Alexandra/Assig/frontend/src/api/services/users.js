import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    console.log(BASE_URL + "/users");
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(user) {
    console.log(BASE_URL + "/users", user);
    return HTTP.post(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/users" + "/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
