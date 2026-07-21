import api from "./api";

export function uploadResume(file) {
  const formData = new FormData();
  formData.append("resume", file);
  return api
    .post("/api/resume/upload", formData, {
      headers: { "Content-Type": "multipart/form-data" }
    })
    .then((res) => res.data);
}

export function analyzeResume(resumeId) {
  return api.post("/api/resume/analyze", { resumeId }).then((res) => res.data);
}
