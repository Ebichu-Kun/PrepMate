import React from "react";
import DashboardCard from "../components/DashboardCard";
import "./Dashboard.css";

const CARDS = [
  { title: "Notes", description: "Create and manage your study notes.", to: "/notes" },
  { title: "AI Interview", description: "Practice with AI-generated interview questions.", to: "/ai-interview" },
  { title: "DSA Tracker", description: "Track solved and unsolved DSA problems.", to: "/dsa-tracker" },
  { title: "Resume Review", description: "Get AI feedback on your resume.", to: "/resume-review" },
  { title: "Quick Quiz", description: "Test yourself on core CS subjects.", to: "/quick-quiz" }
];

export default function Dashboard() {
  return (
    <div>
      <h1 className="page-title">Dashboard</h1>
      <p className="page-subtitle">Pick where you'd like to continue.</p>
      <div className="dashboard-grid">
        {CARDS.map((card) => (
          <DashboardCard key={card.to} {...card} />
        ))}
      </div>
    </div>
  );
}
