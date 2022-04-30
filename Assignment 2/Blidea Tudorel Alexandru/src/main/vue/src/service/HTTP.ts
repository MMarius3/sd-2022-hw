import axios from "axios";

const HTTP = axios.create({
    baseURL: "http://localhost:8080/bookstore_api"
});

export default HTTP;
