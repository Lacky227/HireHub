import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import LandingPage from './pages/LandingPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx'

function App() {

  return (
      <Router>
          <Routes>
              <Route path="/" element={<LandingPage />} />
              <Route path="/register" element={<RegisterPage />} />
          </Routes>
      </Router>
  );
}

export default App
