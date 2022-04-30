import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/book", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  createBook(book) {
    return HTTP.post(BASE_URL + "/book", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  editBook(id, book) {
    return HTTP.put(BASE_URL + "/book/" + id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteBook(id) {
    return HTTP.delete(BASE_URL + "/book/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterAll(filter) {
    return HTTP.get(BASE_URL + "/book/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  exportReport(type) {
    return HTTP.get(BASE_URL + "/book/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
