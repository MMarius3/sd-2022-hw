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
    return HTTP.put(BASE_URL + "/items/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    return HTTP.delete(BASE_URL + "/items/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(item) {
    return HTTP.put(BASE_URL + "/items/sell/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  search(search) {
    return HTTP.get(BASE_URL + "/items/find/" + search, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportPDF() {
    return HTTP.get(BASE_URL + "/items/export/PDF", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportCSV() {
    return HTTP.get(BASE_URL + "/items/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
