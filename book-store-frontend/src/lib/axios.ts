import axios from "axios";

const baseURL = "http://localhost:8081/api/v1";

export default axios.create({
  baseURL,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});
