import React, { useEffect, useMemo, useState } from "react";
import { getNotes, createNote, updateNote, deleteNote } from "../services/notesService";
import { formatDate } from "../utils/constants";
import Loader from "../components/Loader";
import ErrorMessage from "../components/ErrorMessage";
import "./Notes.css";

const EMPTY_FORM = { title: "", content: "" };

export default function Notes() {
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);
  const [saving, setSaving] = useState(false);
  const [uploadFile, setUploadFile] = useState(null);

  useEffect(() => {
    loadNotes();
  }, []);

  async function loadNotes() {
    setLoading(true);
    setError("");
    try {
      const data = await getNotes();
      setNotes(data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load notes.");
    } finally {
      setLoading(false);
    }
  }

  const filteredNotes = useMemo(() => {
    if (!searchTerm.trim()) return notes;
    const term = searchTerm.toLowerCase();
    return notes.filter(
      (note) =>
        note.title?.toLowerCase().includes(term) || note.content?.toLowerCase().includes(term)
    );
  }, [notes, searchTerm]);

  function openCreateForm() {
    setEditingId(null);
    setForm(EMPTY_FORM);
    setUploadFile(null);
    setShowForm(true);
  }

  function openEditForm(note) {
    setEditingId(note.id);
    setForm({ title: note.title, content: note.content });
    setUploadFile(null);
    setShowForm(true);
  }

  function closeForm() {
    setShowForm(false);
    setEditingId(null);
    setForm(EMPTY_FORM);
    setUploadFile(null);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setSaving(true);
    setError("");
    try {
      const payload = uploadFile
        ? { ...form, file: uploadFile }
        : form;
      if (editingId) {
        await updateNote(editingId, payload);
      } else {
        await createNote(payload);
      }
      closeForm();
      loadNotes();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to save note.");
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id) {
    setError("");
    try {
      await deleteNote(id);
      loadNotes();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to delete note.");
    }
  }

  return (
    <div>
      <h1 className="page-title">Notes</h1>
      <p className="page-subtitle">Upload, write, and organize your study notes.</p>

      <ErrorMessage message={error} />

      <div className="notes-toolbar">
        <input
          type="text"
          className="form-control"
          placeholder="Search notes..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button type="button" className="btn btn-primary" onClick={openCreateForm}>
          + New Note
        </button>
      </div>

      {showForm && (
        <form className="card notes-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="note-title">Title</label>
            <input
              id="note-title"
              className="form-control"
              value={form.title}
              onChange={(e) => setForm({ ...form, title: e.target.value })}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="note-content">Content</label>
            <textarea
              id="note-content"
              className="form-control"
              value={form.content}
              onChange={(e) => setForm({ ...form, content: e.target.value })}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="note-upload">Upload Note (optional)</label>
            <input
              id="note-upload"
              type="file"
              className="form-control"
              onChange={(e) => setUploadFile(e.target.files[0] || null)}
            />
          </div>
          <div className="notes-form-actions">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? "Saving..." : "Save Note"}
            </button>
            <button type="button" className="btn btn-secondary" onClick={closeForm}>
              Cancel
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <Loader label="Loading notes..." />
      ) : filteredNotes.length === 0 ? (
        <p className="page-subtitle">No notes found.</p>
      ) : (
        <div className="notes-grid">
          {filteredNotes.map((note) => (
            <div key={note.id} className="card note-card">
              <h3 className="note-card-title">{note.title}</h3>
              <p className="note-card-content">{note.content}</p>
              <div className="note-card-meta">
                <span>Created: {formatDate(note.createdAt)}</span>
                <span>Updated: {formatDate(note.updatedAt)}</span>
              </div>
              <div className="note-card-actions">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => openEditForm(note)}
                >
                  Edit
                </button>
                <button
                  type="button"
                  className="btn btn-danger"
                  onClick={() => handleDelete(note.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
