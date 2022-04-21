import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  getAllItemz() {
    console.log(BASE_URL + "/items");
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(item) {
    console.log(BASE_URL + "/items/add", item);
    return HTTP.post(BASE_URL + "/items/add", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
