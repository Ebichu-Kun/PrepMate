import React, { useEffect, useMemo, useState } from "react";
import {
  getDsaQuestions,
  createDsaQuestion,
  updateDsaQuestion,
  deleteDsaQuestion
} from "../services/dsaService";
import { DIFFICULTY_LEVELS, SOLVED_STATUS, formatDate } from "../utils/constants";
import Loader from "../components/Loader";
import ErrorMessage from "../components/ErrorMessage";
import "./DSATracker.css";

const EMPTY_FORM = {
  problemName: "",
  difficulty: "Easy",
  problemLink: "",
  status: "Unsolved",
  hint: "",
  timeComplexity: "",
  spaceComplexity: "",
  notes: "",
  dateSolved: ""
};

export default function DSATracker() {
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [difficultyFilter, setDifficultyFilter] = useState("All");
  const [statusFilter, setStatusFilter] = useState("All");
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    loadQuestions();
  }, []);

  async function loadQuestions() {
    setLoading(true);
    setError("");
    try {
      const data = await getDsaQuestions();
      setQuestions(data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load DSA questions.");
    } finally {
      setLoading(false);
    }
  }

  const filteredQuestions = useMemo(() => {
    return questions.filter((q) => {
      const matchesSearch = q.problemName?.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesDifficulty = difficultyFilter === "All" || q.difficulty === difficultyFilter;
      const matchesStatus = statusFilter === "All" || q.status === statusFilter;
      return matchesSearch && matchesDifficulty && matchesStatus;
    });
  }, [questions, searchTerm, difficultyFilter, statusFilter]);

  function openCreateForm() {
    setEditingId(null);
    setForm(EMPTY_FORM);
    setShowForm(true);
  }

  function openEditForm(q) {
    setEditingId(q.id);
    setForm({
      problemName: q.problemName || "",
      difficulty: q.difficulty || "Easy",
      problemLink: q.problemLink || "",
      status: q.status || "Unsolved",
      hint: q.hint || "",
      timeComplexity: q.timeComplexity || "",
      spaceComplexity: q.spaceComplexity || "",
      notes: q.notes || "",
      dateSolved: q.dateSolved || ""
    });
    setShowForm(true);
  }

  function closeForm() {
    setShowForm(false);
    setEditingId(null);
    setForm(EMPTY_FORM);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setSaving(true);
    setError("");
    try {
      if (editingId) {
        await updateDsaQuestion(editingId, form);
      } else {
        await createDsaQuestion(form);
      }
      closeForm();
      loadQuestions();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to save question.");
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(id) {
    setError("");
    try {
      await deleteDsaQuestion(id);
      loadQuestions();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to delete question.");
    }
  }

  function difficultyBadgeClass(difficulty) {
    if (difficulty === "Easy") return "badge badge-easy";
    if (difficulty === "Medium") return "badge badge-medium";
    return "badge badge-hard";
  }

  return (
    <div>
      <h1 className="page-title">DSA Tracker</h1>
      <p className="page-subtitle">Track your progress on DSA problems.</p>

      <ErrorMessage message={error} />

      <div className="dsa-toolbar">
        <input
          type="text"
          className="form-control"
          placeholder="Search by problem name..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select
          className="form-control"
          value={difficultyFilter}
          onChange={(e) => setDifficultyFilter(e.target.value)}
        >
          <option value="All">All Difficulties</option>
          {DIFFICULTY_LEVELS.map((level) => (
            <option key={level} value={level}>
              {level}
            </option>
          ))}
        </select>
        <select
          className="form-control"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value="All">All Status</option>
          {SOLVED_STATUS.map((status) => (
            <option key={status} value={status}>
              {status}
            </option>
          ))}
        </select>
        <button type="button" className="btn btn-primary" onClick={openCreateForm}>
          + Add Question
        </button>
      </div>

      {showForm && (
        <form className="card dsa-form" onSubmit={handleSubmit}>
          <div className="dsa-form-grid">
            <div className="form-group">
              <label htmlFor="problemName">Problem Name</label>
              <input
                id="problemName"
                className="form-control"
                value={form.problemName}
                onChange={(e) => setForm({ ...form, problemName: e.target.value })}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="difficulty">Difficulty</label>
              <select
                id="difficulty"
                className="form-control"
                value={form.difficulty}
                onChange={(e) => setForm({ ...form, difficulty: e.target.value })}
              >
                {DIFFICULTY_LEVELS.map((level) => (
                  <option key={level} value={level}>
                    {level}
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="problemLink">Problem Link</label>
              <input
                id="problemLink"
                className="form-control"
                value={form.problemLink}
                onChange={(e) => setForm({ ...form, problemLink: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="status">Status</label>
              <select
                id="status"
                className="form-control"
                value={form.status}
                onChange={(e) => setForm({ ...form, status: e.target.value })}
              >
                {SOLVED_STATUS.map((status) => (
                  <option key={status} value={status}>
                    {status}
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="hint">One-line Hint</label>
              <input
                id="hint"
                className="form-control"
                value={form.hint}
                onChange={(e) => setForm({ ...form, hint: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="timeComplexity">Time Complexity</label>
              <input
                id="timeComplexity"
                className="form-control"
                value={form.timeComplexity}
                onChange={(e) => setForm({ ...form, timeComplexity: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="spaceComplexity">Space Complexity</label>
              <input
                id="spaceComplexity"
                className="form-control"
                value={form.spaceComplexity}
                onChange={(e) => setForm({ ...form, spaceComplexity: e.target.value })}
              />
            </div>
            <div className="form-group">
              <label htmlFor="dateSolved">Date Solved</label>
              <input
                id="dateSolved"
                type="date"
                className="form-control"
                value={form.dateSolved}
                onChange={(e) => setForm({ ...form, dateSolved: e.target.value })}
              />
            </div>
          </div>
          <div className="form-group">
            <label htmlFor="notes">Notes</label>
            <textarea
              id="notes"
              className="form-control"
              value={form.notes}
              onChange={(e) => setForm({ ...form, notes: e.target.value })}
            />
          </div>
          <div className="dsa-form-actions">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? "Saving..." : "Save Question"}
            </button>
            <button type="button" className="btn btn-secondary" onClick={closeForm}>
              Cancel
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <Loader label="Loading questions..." />
      ) : filteredQuestions.length === 0 ? (
        <p className="page-subtitle">No questions found.</p>
      ) : (
        <div className="dsa-table-wrapper card">
          <table className="dsa-table">
            <thead>
              <tr>
                <th>Problem</th>
                <th>Difficulty</th>
                <th>Status</th>
                <th>Hint</th>
                <th>Time</th>
                <th>Space</th>
                <th>Date Solved</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredQuestions.map((q) => (
                <tr key={q.id}>
                  <td>
                    {q.problemLink ? (
                      <a href={q.problemLink} target="_blank" rel="noreferrer">
                        {q.problemName}
                      </a>
                    ) : (
                      q.problemName
                    )}
                  </td>
                  <td>
                    <span className={difficultyBadgeClass(q.difficulty)}>{q.difficulty}</span>
                  </td>
                  <td>
                    <span
                      className={
                        q.status === "Solved" ? "badge badge-solved" : "badge badge-unsolved"
                      }
                    >
                      {q.status}
                    </span>
                  </td>
                  <td>{q.hint || "-"}</td>
                  <td>{q.timeComplexity || "-"}</td>
                  <td>{q.spaceComplexity || "-"}</td>
                  <td>{formatDate(q.dateSolved)}</td>
                  <td>
                    <div className="dsa-row-actions">
                      <button
                        type="button"
                        className="btn btn-secondary"
                        onClick={() => openEditForm(q)}
                      >
                        Edit
                      </button>
                      <button
                        type="button"
                        className="btn btn-danger"
                        onClick={() => handleDelete(q.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
