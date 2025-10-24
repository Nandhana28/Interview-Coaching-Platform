import React from 'react';
import { Navigate } from 'react-router-dom';
import './Layout.css';

const Layout = () => {
  // Check if user is authenticated
  const token = localStorage.getItem('token');
  
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  const user = JSON.parse(localStorage.getItem('user') || '{}');

  return (
    <div className="layout">
      <header className="header">
        <h1>InterviewCoach Dashboard</h1>
        <div className="user-info">
          <span>Welcome, {user.fullName || user.username}!</span>
          <button 
            onClick={() => {
              localStorage.removeItem('token');
              localStorage.removeItem('user');
              window.location.href = '/login';
            }}
            className="logout-btn"
          >
            Logout
          </button>
        </div>
      </header>
      <main className="main-content">
        <div className="welcome-message">
          <h2>Welcome to InterviewCoach! 🎉</h2>
          <p>Your interview preparation platform is ready and working perfectly!</p>
          <div className="features">
            <h3>What you can do:</h3>
            <ul>
              <li>✅ User Registration & Login</li>
              <li>✅ JWT Authentication</li>
              <li>✅ PostgreSQL Database</li>
              <li>✅ Beautiful Login Page</li>
              <li>🚀 More features coming soon!</li>
            </ul>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Layout;