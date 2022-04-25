import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  edit(user) {
    // console.log("user", user,user,id,BASE_URL + "/users/"+ user.id);

    return HTTP.put(BASE_URL + "/users/"+ user.id,user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/"+ user.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
