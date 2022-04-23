import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
        headers: authHeader(),
    }).then((response) => {
        return response.data;
    });
  },
    edit(user) {
        return HTTP.put(BASE_URL + "/users/" + user.id, user, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },

    generateReport(report) {

        return HTTP.get(BASE_URL + "/users/export/" + report.type, { responseType: 'arraybuffer', headers: authHeader() }).then(
            (response) => {
                var fileDownload = require('js-file-download');
                fileDownload(response.data, "Report_"+report.type+".pdf");
                return response.data;
            }
        );
    },
};
