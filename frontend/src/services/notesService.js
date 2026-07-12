import api from "./api";

export function getNotes() {
  return api.get("/api/notes").then((res) => res.data);
}

export function createNote(note) {
  return api.post("/api/notes", note).then((res) => res.data);
}

export function updateNote(id, note) {
  return api.put(`/api/notes/${id}`, note).then((res) => res.data);
}

export function deleteNote(id) {
  return api.delete(`/api/notes/${id}`).then((res) => res.data);
}
