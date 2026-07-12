import axios from "axios";
import { getToken, clearSession } from "../utils/auth";

const BASE_URL = process.env.REACT_APP_API_BASE_URL || "http://localhost:5000";

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

api.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      clearSession();
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;
