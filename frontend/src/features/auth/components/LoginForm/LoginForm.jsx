import React, { useState } from 'react';
import { useLogin } from '../../../hooks/useLogin';
import './LoginForm.css';

const LoginForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const { handleLogin, isLoading, error } = useLogin();

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    handleLogin(formData.email, formData.password);
  };

  return (
    <form className="login-form" onSubmit={handleSubmit}>
      {error && <div className="error-message">{error}</div>}
      
      <div className="input-group">
        <label htmlFor="email">Email Address</label>
        <input
          type="email"
          id="email"
          name="email"
          placeholder="Enter your email"
          value={formData.email}
          onChange={handleInputChange}
          required
          disabled={isLoading}
        />
      </div>

      <div className="input-group">
        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          name="password"
          placeholder="Enter your password"
          value={formData.password}
          onChange={handleInputChange}
          required
          disabled={isLoading}
        />
      </div>

      <div className="form-options">
        <label className="checkbox-label">
          <input type="checkbox" disabled={isLoading} />
          Remember me
        </label>
        <a href="/forgot-password" className="forgot-link">
          Forgot Password?
        </a>
      </div>

      <button 
        type="submit" 
        className="login-button"
        disabled={isLoading}
      >
        {isLoading ? (
          <div className="loading-spinner"></div>
        ) : (
          'Sign In'
        )}
      </button>
    </form>
  );
};

export default LoginForm;