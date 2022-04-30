import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    getPDF() {
        return HTTP.get(BASE_URL + "/reports/pdf", {
            headers: authHeader(),
            responseType: "blob",
        }).then((response) => {
                const blob = new Blob([response.data], {type: "application/pdf"});
                const link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = "report.pdf";
                link.click();
                URL.revokeObjectURL(link.href);
            }
        ).catch(console.error);
    },
    getCSV() {
        return HTTP.get(BASE_URL + "/reports/csv", {
            headers: authHeader(),
            responseType: "blob",
        }).then((response) => {
                const blob = new Blob([response.data], {type: "text/csv"});
                const link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = "report.csv";
                link.click();
                URL.revokeObjectURL(link.href);
            }
        ).catch(console.error);
    }
};
