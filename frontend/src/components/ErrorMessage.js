import React from "react";
import "./ErrorMessage.css";

export default function ErrorMessage({ message, onRetry }) {
  if (!message) return null;
  return (
    <div className="error-message">
      <span>{message}</span>
      {onRetry && (
        <button type="button" className="btn btn-secondary" onClick={onRetry}>
          Retry
        </button>
      )}
    </div>
  );
}
