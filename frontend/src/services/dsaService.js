import api from "./api";

export function getDsaQuestions() {
  return api.get("/api/dsa").then((res) => res.data);
}

export function createDsaQuestion(question) {
  return api.post("/api/dsa", question).then((res) => res.data);
}

export function updateDsaQuestion(id, question) {
  return api.put(`/api/dsa/${id}`, question).then((res) => res.data);
}

export function deleteDsaQuestion(id) {
  return api.delete(`/api/dsa/${id}`).then((res) => res.data);
}
