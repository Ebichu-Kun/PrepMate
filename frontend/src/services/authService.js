import api from "./api";

export function login(email, password) {
  return api.post("/api/auth/login", { email, password }).then((res) => res.data);
}
