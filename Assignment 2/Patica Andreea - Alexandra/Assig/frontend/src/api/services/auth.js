import { BASE_URL, HTTP } from "../http";

export default {
  login(data) {
    console.log("loginaaaaaaa");
    return HTTP.post(BASE_URL + "/auth/sign-in", data).then((response) => {
      console.log(response);
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
  },

  logout() {
    localStorage.removeItem("user");
  },

  register(data) {
    console.log(BASE_URL + "/auth/sign-up", data);
    return HTTP.post(BASE_URL + "/auth/sign-up", data).then((response) => {
      console.log(response.data);
    });
  },
};
