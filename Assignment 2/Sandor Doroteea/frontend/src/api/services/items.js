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
  generatePDF() {
    return HTTP.get(BASE_URL + "/items/export/PDF", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  generateCSV() {
    return HTTP.get(BASE_URL + "/items/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterBy(filter) {
    return HTTP.get(BASE_URL + "/items/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
