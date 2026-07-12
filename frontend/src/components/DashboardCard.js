import React from "react";
import { useNavigate } from "react-router-dom";
import "./DashboardCard.css";

export default function DashboardCard({ title, description, to }) {
  const navigate = useNavigate();

  return (
    <button type="button" className="dashboard-card" onClick={() => navigate(to)}>
      <h3>{title}</h3>
      <p>{description}</p>
    </button>
  );
}
