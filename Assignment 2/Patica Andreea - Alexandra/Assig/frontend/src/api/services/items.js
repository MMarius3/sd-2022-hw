import authHeader, { BASE_URL, HTTP } from "../http";
import { jsPDF } from "jspdf";

export default {
  getAllItemz() {
    console.log(BASE_URL + "/items");
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data);
        return response.data;
      }
    );
  },
  create(item) {
    console.log(BASE_URL + "/items", item);
    return HTTP.post(BASE_URL + "/items", item, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items/" + item.id, item, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteItem(item){
    console.log("delete");
    return HTTP.delete(BASE_URL + "/items/delete/" + item.id,{
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  generateReport(type) {
    console.log(type);
    return HTTP.get(BASE_URL + "/items/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      // pdf.output('dataurlnewwindow');
      var pdf = new jsPDF();
      pdf.text(20, 20, response.data);
      pdf.save("report.pdf");
      return response.data;
    });
  },
};
