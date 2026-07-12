import api from "./api";

export function getQuizQuestions(subject) {
  return api.get(`/api/quiz/${encodeURIComponent(subject)}`).then((res) => res.data);
}

export function submitQuiz(subject, answers) {
  return api.post("/api/quiz/submit", { subject, answers }).then((res) => res.data);
}
