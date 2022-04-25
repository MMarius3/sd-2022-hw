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
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.put(BASE_URL + "/items/edit/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/items/delete/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  searchItems() {
    return HTTP.get(BASE_URL + "/bookstore/search/{str", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sellItem(item) {
    return HTTP.put(BASE_URL + "/bookstore/sell/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
