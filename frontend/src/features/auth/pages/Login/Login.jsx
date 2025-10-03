import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [rememberMe, setRememberMe] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [activeTab, setActiveTab] = useState('username');

  const navigate = useNavigate();

  // Clear form data when component mounts or when coming from other pages
  useEffect(() => {
    // Only load remembered credentials if remember me was checked
    const savedUsername = localStorage.getItem('rememberedUsername');
    if (savedUsername && rememberMe) {
      setFormData(prev => ({ ...prev, username: savedUsername }));
    } else {
      // Clear everything to ensure fresh state
      setFormData({ username: '', password: '' });
      setError('');
    }
  }, [rememberMe]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    // Clear error when user starts typing
    if (error) setError('');
  };

  const handleRememberMeChange = (e) => {
    const isChecked = e.target.checked;
    setRememberMe(isChecked);
    
    if (!isChecked) {
      // Clear remembered username if unchecked
      localStorage.removeItem('rememberedUsername');
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    // Simulate API call with validation
    setTimeout(() => {
      // Simple validation - in real app, this would be API call
      if (formData.username && formData.password) {
        // Save username if remember me is checked
        if (rememberMe) {
          localStorage.setItem('rememberedUsername', formData.username);
        } else {
          localStorage.removeItem('rememberedUsername');
        }
        
        localStorage.setItem('isLoggedIn', 'true');
        navigate('/dashboard');
      } else {
        setError('Invalid username or password. Please try again.');
      }
      setIsLoading(false);
    }, 1000);
  };

  const handleSocialLogin = (provider) => {
    alert(`Logging in with ${provider} - Integration coming soon!`);
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-card">
          {/* Header */}
          <div className="login-header">
            <h1 className="login-title">Welcome Back</h1>
            <p className="login-subtitle">Sign in to access your interview preparation dashboard and continue your learning journey</p>
          </div>

          {/* Tab Navigation */}
          <div className="tab-navigation">
            <button 
              className={`tab-button ${activeTab === 'username' ? 'active' : ''}`}
              onClick={() => setActiveTab('username')}
            >
              Username
            </button>
            <button 
              className={`tab-button ${activeTab === 'phone' ? 'active' : ''}`}
              onClick={() => setActiveTab('phone')}
            >
              Phone
            </button>
            <button 
              className={`tab-button ${activeTab === 'social' ? 'active' : ''}`}
              onClick={() => setActiveTab('social')}
            >
              Social
            </button>
          </div>

          {/* Username Login */}
          {activeTab === 'username' && (
            <form className="login-form" onSubmit={handleLogin}>
              <div className="input-group">
                <label htmlFor="username" className="input-label">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  placeholder="Enter username or email ID"
                  value={formData.username}
                  onChange={handleInputChange}
                  required
                  autoComplete="username"
                />
              </div>

              <div className="input-group">
                <label htmlFor="password" className="input-label">Password</label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  placeholder="Enter the password"
                  value={formData.password}
                  onChange={handleInputChange}
                  required
                  autoComplete="current-password"
                />
              </div>

              <div className="form-options">
                <label className="checkbox-label">
                  <input 
                    type="checkbox" 
                    checked={rememberMe}
                    onChange={handleRememberMeChange}
                  />
                  Remember me
                </label>
                <a href="#" className="forgot-link">
                  Forgot Password?
                </a>
              </div>

              {/* Error Message */}
              {error && (
                <div className="error-message">
                  <span className="error-icon">⚠️</span>
                  {error}
                </div>
              )}

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
          )}

          {/* Phone Login */}
          {activeTab === 'phone' && (
            <div className="phone-login">
              <div className="input-group">
                <label htmlFor="phone" className="input-label">Phone Number</label>
                <input
                  type="tel"
                  id="phone"
                  name="phone"
                  placeholder="Enter your phone number"
                />
              </div>
              <button className="otp-button">
                Send OTP
              </button>
            </div>
          )}

          {/* Social Login */}
          {activeTab === 'social' && (
            <div className="social-login">
              <div className="social-buttons">
                <button 
                  className="social-button google"
                  onClick={() => handleSocialLogin('Google')}
                >
                  Continue with Google
                </button>
                <button 
                  className="social-button facebook"
                  onClick={() => handleSocialLogin('Facebook')}
                >
                  Continue with Facebook
                </button>
                <button 
                  className="social-button github"
                  onClick={() => handleSocialLogin('GitHub')}
                >
                  Continue with GitHub
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Login;