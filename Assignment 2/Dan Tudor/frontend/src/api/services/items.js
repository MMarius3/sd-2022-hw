import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      //return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    // return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
    return HTTP.post(BASE_URL + "/books", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    // return HTTP.patch(BASE_URL + "/items", item, {
    return HTTP.patch(BASE_URL + "/books", item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    // return HTTP.delete(BASE_URL + "/items/" + item.id, {
    return HTTP.delete(BASE_URL + "/books/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  search(item) {
    return HTTP.get(BASE_URL + "/books/search/" + item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
