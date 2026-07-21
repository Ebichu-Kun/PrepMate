import React from "react";
import Sidebar from "./Sidebar";
import Header from "./Header";
import "./Layout.css";

export default function Layout({ children }) {
  return (
    <div className="app-layout">
      <Sidebar />
      <div className="app-layout-main">
        <Header />
        <main className="app-layout-content">{children}</main>
      </div>
    </div>
  );
}
