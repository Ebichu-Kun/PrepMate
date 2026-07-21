import React from "react";
import { getUser } from "../utils/auth";
import "./Header.css";

export default function Header() {
  const user = getUser();

  return (
    <header className="app-header">
      <div />
      <div className="app-header-user">
        <span className="app-header-name">{user?.name || "User"}</span>
      </div>
    </header>
  );
}
