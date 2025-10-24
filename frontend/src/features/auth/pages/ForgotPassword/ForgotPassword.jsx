import React from 'react';
import { Link } from 'react-router-dom';
import './ForgotPassword.css';

const ForgotPassword = () => {
  return (
    <div className="forgot-password-container">
      <div className="forgot-password-background">
        <div className="forgot-password-gradient"></div>
      </div>
      
      <div className="forgot-password-card">
        <div className="forgot-password-header">
          <div className="logo">
            <div className="logo-icon">💼</div>
            <h1>InterviewCoach</h1>
          </div>
          <h2>Reset Password</h2>
          <p>Enter your email to reset your password</p>
        </div>

        <div className="forgot-password-form">
          <p className="coming-soon">Password reset coming soon!</p>
          <Link to="/login" className="back-to-login">
            Back to Login
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;