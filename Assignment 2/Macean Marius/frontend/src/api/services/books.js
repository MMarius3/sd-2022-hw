import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/books", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/books", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(id, book) {
    return HTTP.patch(BASE_URL + "/books/" + id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/books/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(id) {
    return HTTP.put(BASE_URL + "/books/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportReport() {
    return HTTP.post(BASE_URL + "/books/export/" + 1, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allFilteredBooks(title, author, genre, quantity) {
    return HTTP.get(
      BASE_URL +
        "/books/filtered/" +
        title +
        "/" +
        author +
        "/" +
        genre +
        "/" +
        quantity,
      {
        headers: authHeader(),
      }
    ).then((response) => {
      return response.data;
    });
  },
};
