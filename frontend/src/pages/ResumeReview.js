import React, { useState } from "react";
import { uploadResume, analyzeResume } from "../services/resumeService";
import Loader from "../components/Loader";
import ErrorMessage from "../components/ErrorMessage";
import "./ResumeReview.css";

export default function ResumeReview() {
  const [file, setFile] = useState(null);
  const [resumeId, setResumeId] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [analyzing, setAnalyzing] = useState(false);
  const [error, setError] = useState("");
  const [analysis, setAnalysis] = useState(null);

  function handleFileChange(e) {
    const selected = e.target.files[0];
    if (!selected) return;
    setFile(selected);
    setAnalysis(null);
    setResumeId(null);
    setError("");
    handleUpload(selected);
  }

  async function handleUpload(selectedFile) {
    setUploading(true);
    setError("");
    try {
      const data = await uploadResume(selectedFile);
      setResumeId(data.resumeId);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to upload resume.");
    } finally {
      setUploading(false);
    }
  }

  async function handleAnalyze() {
    setAnalyzing(true);
    setError("");
    try {
      const data = await analyzeResume(resumeId);
      setAnalysis(data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to analyze resume.");
    } finally {
      setAnalyzing(false);
    }
  }

  return (
    <div>
      <h1 className="page-title">Resume Review</h1>
      <p className="page-subtitle">Upload your resume and get AI-powered feedback.</p>

      <ErrorMessage message={error} />

      <div className="card resume-upload-card">
        <div className="form-group">
          <label htmlFor="resume-file">Upload Resume (PDF)</label>
          <input
            id="resume-file"
            type="file"
            accept="application/pdf"
            className="form-control"
            onChange={handleFileChange}
          />
        </div>

        {file && <p className="resume-filename">Selected file: {file.name}</p>}

        {uploading && <Loader label="Uploading resume..." />}

        {!uploading && resumeId && (
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleAnalyze}
            disabled={analyzing}
          >
            {analyzing ? "Analyzing..." : "Analyze Resume"}
          </button>
        )}
      </div>

      {analyzing && <Loader label="Analyzing resume..." />}

      {!analyzing && analysis && (
        <div className="card resume-result">
          <h2>Analysis</h2>

          <div className="resume-scores">
            <div className="resume-score-box">
              <span className="resume-score-label">Resume Score</span>
              <span className="resume-score-value">{analysis.resumeScore}</span>
            </div>
            <div className="resume-score-box">
              <span className="resume-score-label">ATS Score</span>
              <span className="resume-score-value">{analysis.atsScore}</span>
            </div>
          </div>

          <div className="resume-detail">
            <h4>Strengths</h4>
            <ul>
              {analysis.strengths?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="resume-detail">
            <h4>Weaknesses</h4>
            <ul>
              {analysis.weaknesses?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="resume-detail">
            <h4>Missing Skills</h4>
            <ul>
              {analysis.missingSkills?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="resume-detail">
            <h4>Suggested Improvements</h4>
            <ul>
              {analysis.suggestedImprovements?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="resume-detail">
            <h4>Final Recommendation</h4>
            <p>{analysis.finalRecommendation}</p>
          </div>
        </div>
      )}
    </div>
  );
}
