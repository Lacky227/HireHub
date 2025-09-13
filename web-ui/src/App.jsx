import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import LandingPage from './pages/LandingPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx'
import LoginPage from "./pages/LoginPage.jsx";
import DashboardRouter from "./pages/dashboards/DashboardRouter.jsx";

function App() {

  return (
      <Router>
          <Routes>
              <Route path="/" element={<LandingPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/dashboard" element={<DashboardRouter />} />
          </Routes>
      </Router>
  );
}

export default App
