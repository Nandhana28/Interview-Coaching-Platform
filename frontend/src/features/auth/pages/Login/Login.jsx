// frontend/src/features/auth/pages/Login/Login.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useLogin } from '../../hooks/useLogin';
import { motion } from 'framer-motion';
import './Login.css';

// === ICONS (compact) ===
const UserIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/></svg>);
const LockIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zM12 17c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zM15.1 8H8.9V6c0-1.71 1.39-3.1 3.1-3.1 1.71 0 3.1 1.39 3.1 3.1v2z"/></svg>);
const KeyIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M21 10h-8.35C11.83 7.67 9.61 6 7 6c-3.31 0-6 2.69-6 6s2.69 6 6 6c2.61 0 4.83-1.67 5.65-4H13l2 2 2-2 2 2 4-4.04L21 10zM7 15c-1.65 0-3-1.35-3-3s1.35-3 3-3 3 1.35 3 3-1.35 3-3 3z"/></svg>);
const ArrowIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z"/></svg>);
const WarningIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z"/></svg>);
const RocketIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 2.5s4.5 2.04 4.5 10.5c0 2.49-1.04 5.57-1.6 7H9.1c-.56-1.43-1.6-4.51-1.6-7C7.5 4.54 12 2.5 12 2.5zm2 8.5c0-1.1-.9-2-2-2s-2 .9-2 2 .9 2 2 2 2-.9 2-2zm-6.31 9.52c-.48-1.23-1.52-4.17-1.67-6.87l-1.13.75c-.56.38-.89 1-.89 1.67V22l3.69-1.48zM20 22v-5.93c0-.67-.33-1.29-.89-1.66l-1.13-.75c-.15 2.69-1.2 5.64-1.67 6.87L20 22z"/></svg>);
const GoogleIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/><path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/><path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/><path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/></svg>);
const LinkedInIcon = () => (<svg className="icon" viewBox="0 0 24 24" fill="currentColor"><path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/></svg>);

const Login = () => {
  const [formData, setFormData] = useState({ usernameOrEmail: '', password: '', rememberMe: false });
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const { login, error, isSuccess } = useLogin();
  const navigate = useNavigate();

  useEffect(() => {
    const saved = localStorage.getItem('rememberedUsername');
    if (saved) setFormData(prev => ({ ...prev, usernameOrEmail: saved, rememberMe: true }));
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: '' }));
  };

  const validate = () => {
    const err = {};
    if (!formData.usernameOrEmail.trim()) err.usernameOrEmail = 'Required';
    if (!formData.password) err.password = 'Required';
    else if (formData.password.length < 6) err.password = 'Min 6 characters';
    setErrors(err);
    return Object.keys(err).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    setIsLoading(true);
    if (formData.rememberMe) localStorage.setItem('rememberedUsername', formData.usernameOrEmail);
    else localStorage.removeItem('rememberedUsername');
    await login(formData);
    setIsLoading(false);
  };

  return (
    <div className="login-page">
      <div className="bg-image"></div>

      <div className="cards-wrapper">

        {/* LEFT: IMAGE CARD — NOW HOLDS THE BUTTON */}
        <motion.div
          initial={{ opacity: 0, x: -60 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.7 }}
          className="quote-card"
        >
          <img src="/assets/images/login_img2.png" alt="InterviewPro AI" className="quote-image" />

          {/* BACK BUTTON — TOP-RIGHT OF IMAGE CARD */}
          <motion.button
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            className="back-btn-image"
            onClick={() => navigate('/')}
          >
            ← Back to Home
          </motion.button>
        </motion.div>

        {/* RIGHT: LOGIN CARD */}
        <motion.div
          initial={{ opacity: 0, x: 60 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.7 }}
          className="login-card"
        >
          <div className="login-header">
            <div className="logo"><RocketIcon /><span>InterviewPro</span></div>
            <h1>Welcome Back</h1>
            <p>Sign in to continue your journey</p>
          </div>

          {error && (
            <motion.div initial={{ opacity: 0, y: -10 }} animate={{ opacity: 1, y: 0 }} className="alert error">
              <WarningIcon /><span>{error}</span>
            </motion.div>
          )}

          <form onSubmit={handleSubmit} className="login-form">
            <div className="field">
              <label><UserIcon /> Username or Email</label>
              <input type="text" name="usernameOrEmail" value={formData.usernameOrEmail} onChange={handleChange} placeholder="john@example.com" className={errors.usernameOrEmail ? 'err' : ''} />
              {errors.usernameOrEmail && <span className="err">{errors.usernameOrEmail}</span>}
            </div>

            <div className="field">
              <label><LockIcon /> Password</label>
              <input type="password" name="password" value={formData.password} onChange={handleChange} placeholder="••••••••" className={errors.password ? 'err' : ''} />
              {errors.password && <span className="err">{errors.password}</span>}
            </div>

            <div className="form-options">
              <label className="checkbox">
                <input type="checkbox" name="rememberMe" checked={formData.rememberMe} onChange={handleChange} />
                <span className="checkmark"></span> Remember me
              </label>
              <Link to="/forgot-password" className="link">Forgot password?</Link>
            </div>

            <motion.button whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }} type="submit" disabled={isLoading} className="submit-btn">
              {isLoading ? <>Signing In...</> : <><KeyIcon /> Sign In <ArrowIcon /></>}
            </motion.button>
          </form>

          <p className="or-text">or continue with</p>

          <div className="social-login">
            <motion.button whileHover={{ y: -3 }} className="social-btn google"><GoogleIcon /> Google</motion.button>
            <motion.button whileHover={{ y: -3 }} className="social-btn linkedin"><LinkedInIcon /> LinkedIn</motion.button>
          </div>

          <div className="create-account">
            New here? <Link to="/register" className="link">Create account →</Link>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Login;