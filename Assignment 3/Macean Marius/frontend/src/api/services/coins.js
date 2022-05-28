import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allCoins() {
    return HTTP.get(BASE_URL + "/coins", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createCoin(coin) {
    return HTTP.post(BASE_URL + "/coins", coin, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  editCoin(id, coin) {
    return HTTP.patch(BASE_URL + "/coins/" + id, coin, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteCoin(id) {
    return HTTP.delete(BASE_URL + "/coins/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  exportReports(type) {
    return HTTP.get(BASE_URL + "/coins/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
