import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items/create", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(item) {
    const id = item.id;
    return HTTP.patch(BASE_URL + "/items/" + id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(item) {
    const id = item.id;
    return HTTP.patch(BASE_URL + "/items/sell/" + id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    const id = item.id;
    return HTTP.delete(BASE_URL + "/items/delete/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  export(type) {
    console.log(BASE_URL + "/items/" + type);
    return HTTP.post(BASE_URL + "/items/" + type, {
      headers: authHeader(),
    });
  },
};
