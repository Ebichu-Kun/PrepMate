import api from "./api";

export function getInterviewQuestions() {
  return api.get("/api/interview/questions").then((res) => res.data);
}

export function submitInterview(answers) {
  return api.post("/api/interview/submit", { answers }).then((res) => res.data);
}
