
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout:15000,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

//Handle expired or invalid JWTs from protected endpoints.

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status;
    const url = error.config?.url || "";

    // Do not redirect when login itself returns 401.
    const isLoginRequest = url.includes("/auth/login");

    if (status === 401 && !isLoginRequest) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");

      if (
        window.location.pathname !== "/" &&
        window.location.pathname !== "/register"
      ) {
        window.location.assign("/");
      }
    }

    return Promise.reject(error);
  }
);

export default api;
