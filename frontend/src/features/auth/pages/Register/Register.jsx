import React from 'react';
import { Link } from 'react-router-dom';
import RegisterForm from '../../components/RegisterForm/RegisterForm';
import './Register.css';

const Register = () => {
  return (
    <div className="register-container">
      <div className="register-background">
        <div className="register-gradient"></div>
      </div>
      
      <div className="register-card">
        <div className="register-header">
          <div className="logo">
            <div className="logo-icon">💼</div>
            <h1>InterviewCoach</h1>
          </div>
          <h2>Create Account</h2>
          <p>Start your interview preparation journey</p>
        </div>

        <RegisterForm />

        <div className="terms">
          <p>
            By creating an account, you agree to our{' '}
            <Link to="/terms" className="terms-link">Terms of Service</Link>{' '}
            and{' '}
            <Link to="/privacy" className="terms-link">Privacy Policy</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Register;