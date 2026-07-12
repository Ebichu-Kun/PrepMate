import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { clearSession } from "../utils/auth";
import "./Sidebar.css";

const NAV_ITEMS = [
  { to: "/dashboard", label: "Dashboard" },
  { to: "/notes", label: "Notes" },
  { to: "/ai-interview", label: "AI Interview" },
  { to: "/dsa-tracker", label: "DSA Tracker" },
  { to: "/resume-review", label: "Resume Review" },
  { to: "/quick-quiz", label: "Quick Quiz" }
];

export default function Sidebar() {
  const navigate = useNavigate();

  function handleLogout() {
    clearSession();
    navigate("/login");
  }

  return (
    <aside className="sidebar">
      <div className="sidebar-logo">PrepMate</div>
      <nav className="sidebar-nav">
        {NAV_ITEMS.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) =>
              isActive ? "sidebar-link sidebar-link-active" : "sidebar-link"
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
      <button type="button" className="sidebar-logout" onClick={handleLogout}>
        Logout
      </button>
    </aside>
  );
}
