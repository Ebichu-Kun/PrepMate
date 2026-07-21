import React from "react";
import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../utils/auth";
import Layout from "./Layout";

export default function ProtectedRoute({ children }) {
  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }
  return <Layout>{children}</Layout>;
}
