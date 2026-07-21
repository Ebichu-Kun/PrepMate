import React, { useState } from "react";
import { getInterviewQuestions, submitInterview } from "../services/interviewService";
import Loader from "../components/Loader";
import ErrorMessage from "../components/ErrorMessage";
import "./AIInterview.css";

const STAGE = {
  INTRO: "intro",
  QUESTION: "question",
  RESULT: "result"
};

export default function AIInterview() {
  const [stage, setStage] = useState(STAGE.INTRO);
  const [questions, setQuestions] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [answer, setAnswer] = useState("");
  const [answers, setAnswers] = useState([]);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleStart() {
    setLoading(true);
    setError("");
    try {
      const data = await getInterviewQuestions();
      setQuestions(data);
      setAnswers([]);
      setCurrentIndex(0);
      setAnswer("");
      setStage(STAGE.QUESTION);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load interview questions.");
    } finally {
      setLoading(false);
    }
  }

  async function handleNext() {
    const updatedAnswers = [
      ...answers,
      { questionId: questions[currentIndex].id, answer }
    ];
    setAnswers(updatedAnswers);
    setAnswer("");

    if (currentIndex + 1 < questions.length) {
      setCurrentIndex(currentIndex + 1);
      return;
    }

    setLoading(true);
    setError("");
    try {
      const data = await submitInterview(updatedAnswers);
      setResult(data);
      setStage(STAGE.RESULT);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to submit interview.");
    } finally {
      setLoading(false);
    }
  }

  function handleRestart() {
    setStage(STAGE.INTRO);
    setQuestions([]);
    setAnswers([]);
    setResult(null);
    setCurrentIndex(0);
    setAnswer("");
  }

  return (
    <div>
      <h1 className="page-title">AI Interview</h1>
      <p className="page-subtitle">Practice a mock interview and get instant feedback.</p>

      <ErrorMessage message={error} />

      {loading && <Loader label="Please wait..." />}

      {!loading && stage === STAGE.INTRO && (
        <div className="card interview-card">
          <p>Start a mock interview session. Answer each question, then move to the next.</p>
          <button type="button" className="btn btn-primary" onClick={handleStart}>
            Start Interview
          </button>
        </div>
      )}

      {!loading && stage === STAGE.QUESTION && questions.length > 0 && (
        <div className="card interview-card">
          <p className="interview-progress">
            Question {currentIndex + 1} of {questions.length}
          </p>
          <h3 className="interview-question">{questions[currentIndex].question}</h3>
          <textarea
            className="form-control"
            rows={6}
            placeholder="Type your answer..."
            value={answer}
            onChange={(e) => setAnswer(e.target.value)}
          />
          <button
            type="button"
            className="btn btn-primary interview-next-btn"
            onClick={handleNext}
            disabled={!answer.trim()}
          >
            {currentIndex + 1 < questions.length ? "Next" : "Finish Interview"}
          </button>
        </div>
      )}

      {!loading && stage === STAGE.RESULT && result && (
        <div className="card interview-result">
          <h2>Interview Result</h2>
          <div className="interview-scores">
            <div className="interview-score-box">
              <span className="interview-score-label">Overall Rating</span>
              <span className="interview-score-value">{result.overallRating}</span>
            </div>
            <div className="interview-score-box">
              <span className="interview-score-label">Technical Score</span>
              <span className="interview-score-value">{result.technicalScore}</span>
            </div>
            <div className="interview-score-box">
              <span className="interview-score-label">Communication Score</span>
              <span className="interview-score-value">{result.communicationScore}</span>
            </div>
          </div>

          <div className="interview-detail">
            <h4>Strengths</h4>
            <ul>
              {result.strengths?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="interview-detail">
            <h4>Weaknesses</h4>
            <ul>
              {result.weaknesses?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <div className="interview-detail">
            <h4>Suggestions for Improvement</h4>
            <ul>
              {result.suggestions?.map((item, idx) => (
                <li key={idx}>{item}</li>
              ))}
            </ul>
          </div>

          <button type="button" className="btn btn-secondary" onClick={handleRestart}>
            Start New Interview
          </button>
        </div>
      )}
    </div>
  );
}
