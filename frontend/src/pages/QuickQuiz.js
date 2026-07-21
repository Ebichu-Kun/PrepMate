import React, { useState } from "react";
import { getQuizQuestions, submitQuiz } from "../services/quizService";
import { QUIZ_SUBJECTS } from "../utils/constants";
import Loader from "../components/Loader";
import ErrorMessage from "../components/ErrorMessage";
import "./QuickQuiz.css";

const STAGE = {
  SELECT: "select",
  QUESTION: "question",
  RESULT: "result"
};

export default function QuickQuiz() {
  const [stage, setStage] = useState(STAGE.SELECT);
  const [subject, setSubject] = useState("");
  const [questions, setQuestions] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [selectedOption, setSelectedOption] = useState(null);
  const [answers, setAnswers] = useState([]);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleStart() {
    if (!subject) return;
    setLoading(true);
    setError("");
    try {
      const data = await getQuizQuestions(subject);
      setQuestions(data);
      setAnswers([]);
      setCurrentIndex(0);
      setSelectedOption(null);
      setStage(STAGE.QUESTION);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load quiz questions.");
    } finally {
      setLoading(false);
    }
  }

  function handleNext() {
    const updatedAnswers = [
      ...answers,
      { questionId: questions[currentIndex].id, selectedOption }
    ];
    setAnswers(updatedAnswers);
    setSelectedOption(null);

    if (currentIndex + 1 < questions.length) {
      setCurrentIndex(currentIndex + 1);
    } else {
      handleSubmit(updatedAnswers);
    }
  }

  async function handleSubmit(finalAnswers) {
    setLoading(true);
    setError("");
    try {
      const data = await submitQuiz(subject, finalAnswers);
      setResult(data);
      setStage(STAGE.RESULT);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to submit quiz.");
    } finally {
      setLoading(false);
    }
  }

  function handleRestart() {
    setStage(STAGE.SELECT);
    setSubject("");
    setQuestions([]);
    setAnswers([]);
    setResult(null);
    setCurrentIndex(0);
    setSelectedOption(null);
  }

  return (
    <div>
      <h1 className="page-title">Quick Quiz</h1>
      <p className="page-subtitle">Test your knowledge across core CS subjects.</p>

      <ErrorMessage message={error} />

      {loading && <Loader label="Please wait..." />}

      {!loading && stage === STAGE.SELECT && (
        <div className="card quiz-card">
          <div className="form-group">
            <label htmlFor="subject">Select Subject</label>
            <select
              id="subject"
              className="form-control"
              value={subject}
              onChange={(e) => setSubject(e.target.value)}
            >
              <option value="">Choose a subject...</option>
              {QUIZ_SUBJECTS.map((s) => (
                <option key={s} value={s}>
                  {s}
                </option>
              ))}
            </select>
          </div>
          <button
            type="button"
            className="btn btn-primary"
            onClick={handleStart}
            disabled={!subject}
          >
            Start Quiz
          </button>
        </div>
      )}

      {!loading && stage === STAGE.QUESTION && questions.length > 0 && (
        <div className="card quiz-card">
          <p className="quiz-progress">
            Question {currentIndex + 1} of {questions.length}
          </p>
          <h3 className="quiz-question">{questions[currentIndex].question}</h3>
          <div className="quiz-options">
            {questions[currentIndex].options?.map((option, idx) => (
              <button
                type="button"
                key={idx}
                className={
                  selectedOption === option ? "quiz-option quiz-option-selected" : "quiz-option"
                }
                onClick={() => setSelectedOption(option)}
              >
                {option}
              </button>
            ))}
          </div>
          <button
            type="button"
            className="btn btn-primary quiz-next-btn"
            onClick={handleNext}
            disabled={selectedOption === null}
          >
            {currentIndex + 1 < questions.length ? "Next Question" : "Submit Quiz"}
          </button>
        </div>
      )}

      {!loading && stage === STAGE.RESULT && result && (
        <div className="card quiz-result">
          <h2>Quiz Result</h2>
          <div className="quiz-scores">
            <div className="quiz-score-box">
              <span className="quiz-score-label">Score</span>
              <span className="quiz-score-value">{result.score}</span>
            </div>
            <div className="quiz-score-box">
              <span className="quiz-score-label">Correct Answers</span>
              <span className="quiz-score-value">{result.correctAnswers}</span>
            </div>
            <div className="quiz-score-box">
              <span className="quiz-score-label">Incorrect Answers</span>
              <span className="quiz-score-value">{result.incorrectAnswers}</span>
            </div>
          </div>

          <div className="quiz-detail">
            <h4>Suggested Topics to Revise</h4>
            <ul>
              {result.suggestedTopics?.map((topic, idx) => (
                <li key={idx}>{topic}</li>
              ))}
            </ul>
          </div>

          <button type="button" className="btn btn-secondary" onClick={handleRestart}>
            Take Another Quiz
          </button>
        </div>
      )}
    </div>
  );
}
