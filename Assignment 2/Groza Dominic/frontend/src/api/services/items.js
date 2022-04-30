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
    return HTTP.put(BASE_URL + "/items/"+ item.id,item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(item) {
    console.log("delete item", item);
    return HTTP.delete(BASE_URL + "/items/"+ item.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  sell(item) {
    return HTTP.patch(BASE_URL + "/items/"+ item.id,item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  report(type){
    return HTTP.get(BASE_URL + "/items/report/"+ type, {

      responseType:type==="PDF"?'arraybuffer':"",
      headers: authHeader(),
    }).then((response) => {
      var fileDownload = require('js-file-download');
      fileDownload(response.data, "Report_"+type+"."+type.toLowerCase());
      return response.data;
    });
  },
};
