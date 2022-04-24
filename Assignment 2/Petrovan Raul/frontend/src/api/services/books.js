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
  edit(book) {
    return HTTP.put(BASE_URL + "/books/" + book.id, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(book) {
    return HTTP.put(BASE_URL + "/books/" + book.id + "/sellOne", {}, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  getGenres() {
    return HTTP.get(BASE_URL + "/books/genres", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteBook(book) {
    return HTTP.delete(BASE_URL + "/books/" + book.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  filterBooks(filter) {
    return HTTP.post(BASE_URL + "/books/filter", {filter}, {
      headers: authHeader(),
      params: filter,
    }).then((response) => {
      return response.data;
    });
  },
};
