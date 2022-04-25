import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  generatePdfReport() {
    return HTTP.get(BASE_URL + "/items/export/" + "PDFBox", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  generateCsvReport() {
    return HTTP.get(BASE_URL + "/items/export/" + "CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
