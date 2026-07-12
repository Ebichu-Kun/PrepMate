import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";
import { saveSession } from "../utils/auth";
import ErrorMessage from "../components/ErrorMessage";
import "./Login.css";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setLoading(true);
    try {
      const data = await login(email, password);
      saveSession(data.token, data.user);
      navigate("/dashboard");
    } catch (err) {
      setError(
        err.response?.data?.message || "Unable to log in. Check your credentials and try again."
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-page">
      <form className="login-card card" onSubmit={handleSubmit}>
        <h1 className="login-title">PrepMate</h1>
        <p className="login-subtitle">Log in to continue your prep.</p>

        <ErrorMessage message={error} />

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            className="form-control"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            id="password"
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit" className="btn btn-primary login-submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>
    </div>
  );
}
