import authHeader, { BASE_URL, HTTP } from "../http";
//import { jsPDF } from "jspdf";
//import FileDownload from "js-file-download";

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
  deleteItem(item) {
    console.log("delete");
    return HTTP.delete(BASE_URL + "/items/delete/" + item.id, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      return response.data;
    });
  },
  dataURItoBlob(dataURI) {
    const byteString = Buffer.from(dataURI).toString('base64');
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: 'application/pdf'});
    return blob;
  },
  generateReport(type) {
    console.log(type);
    return HTTP.get(BASE_URL + "/items/export/" + type, {
      headers: authHeader(),
    }).then((response) => {
      console.log(response.data);
      // pdf.output('dataurlnewwindow');
      if (type.localeCompare("PDF") === 0) {
        // var pdf = new jsPDF();
        // pdf.text(20, 20, response.data);
        // pdf.save("report.pdf");
        let FileDownloadPDF = require("js-file-download");
        const blob = this.dataURItoBlob(response.data);
        const url = URL.createObjectURL(blob);
        window.open(url, '_blank');
        FileDownloadPDF(response.data, "report." + type);
      } else {
        console.log("CSV");
        let FileDownloadCSV = require("js-file-download");
        FileDownloadCSV(response.data, "report." + type);
        console.log("success!", response.data);
      }

      return response.data;
    });
  },
};
