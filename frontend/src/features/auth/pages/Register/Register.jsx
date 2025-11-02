import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRegister } from '../../hooks/useRegister';
import './Register.css';

const Register = () => {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState('terms');

  const [formData, setFormData] = useState({
    fullName: '', username: '', email: '', phone: '', dob: '', referral: '',
    password: '', confirmPassword: ''
  });

  const [errors, setErrors] = useState({});
  const { register, isLoading, error, isSuccess } = useRegister();

  const strength = formData.password.length < 6 ? 1 : formData.password.length < 10 ? 2 : 3;
  const strengthColor = strength === 1 ? '#ef4444' : strength === 2 ? '#f59e0b' : '#10b981';

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: '' }));
  };

  const validate = () => {
    const err = {};
    if (!formData.fullName.trim()) err.fullName = 'Required';
    if (!formData.username.trim()) err.username = 'Required';
    if (!formData.email.includes('@')) err.email = 'Invalid email';
    if (!formData.phone.match(/^\+?[0-9]{10,15}$/)) err.phone = 'Invalid phone';
    if (formData.password.length < 6) err.password = 'Min 6 characters';
    if (formData.password !== formData.confirmPassword) err.confirmPassword = "Passwords don't match";
    setErrors(err);
    return Object.keys(err).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    await register({
      fullName: formData.fullName,
      username: formData.username,
      email: formData.email,
      password: formData.password,
      phone: formData.phone,
      dob: formData.dob,
      referralCode: formData.referral || undefined
    });
  };

  return (
    <>
      <div className="register-page">
        <div className="bg-image"></div>

        <button className="back-btn" onClick={() => navigate('/login')}>
          ← Back to Login
        </button>

        <div className="card-wrapper">
          <div className="register-card">
            {/* NEW: SIGN UP TEXT */}
            <div className="signup-text">
              Sign up to unlock <span>elite interview mastery</span>
            </div>

            <form onSubmit={handleSubmit} className="form-grid">
              {/* LEFT */}
              <div className="col">
                <div className="field">
                  <label>Full Name</label>
                  <input name="fullName" value={formData.fullName} onChange={handleChange} placeholder="John Doe" />
                  {errors.fullName && <span className="err">{errors.fullName}</span>}
                </div>

                <div className="field">
                  <label>Username</label>
                  <input name="username" value={formData.username} onChange={handleChange} placeholder="@johndoe" />
                  {errors.username && <span className="err">{errors.username}</span>}
                </div>

                <div className="field">
                  <label>Password</label>
                  <input type="password" name="password" value={formData.password} onChange={handleChange} placeholder="••••••••" />
                  {formData.password && (
                    <>
                      <div className="pw-bar"><div className="fill" style={{ width: `${strength * 33}%`, background: strengthColor }}></div></div>
                      <div className="pw-text" style={{ color: strengthColor }}>
                        {strength === 1 ? 'Weak' : strength === 2 ? 'Medium' : 'Strong'}
                      </div>
                    </>
                  )}
                  {errors.password && <span className="err">{errors.password}</span>}
                </div>

                <div className="field">
                  <label>Confirm Password</label>
                  <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} placeholder="••••••••" />
                  {errors.confirmPassword && <span className="err">{errors.confirmPassword}</span>}
                </div>
              </div>

              {/* DIVIDER */}
              <div className="divider"></div>

              {/* RIGHT */}
              <div className="col">
                <div className="field">
                  <label>Phone Number</label>
                  <input name="phone" value={formData.phone} onChange={handleChange} placeholder="+91 98765 43210" />
                  {errors.phone && <span className="err">{errors.phone}</span>}
                </div>

                <div className="field">
                  <label>Email ID</label>
                  <input name="email" value={formData.email} onChange={handleChange} placeholder="john@example.com" />
                  {errors.email && <span className="err">{errors.email}</span>}
                </div>

                <div className="field">
                  <label>Date of Birth</label>
                  <input type="date" name="dob" value={formData.dob} onChange={handleChange} />
                </div>

                <div className="field">
                  <label>Referral Code (Optional)</label>
                  <input name="referral" value={formData.referral} onChange={handleChange} placeholder="REF2025" />
                </div>
              </div>
            </form>

            <div className="terms">
              <p>
                By signing up, you agree to our{' '}
                <span className="link" onClick={() => { setModalType('terms'); setShowModal(true); }}>
                  Terms of Service
                </span>{' '}
                and{' '}
                <span className="link" onClick={() => { setModalType('privacy'); setShowModal(true); }}>
                  Privacy Policy
                </span>
              </p>
            </div>

            {error && <div className="alert error">{error}</div>}
            {isSuccess && <div className="alert success">Account created! Redirecting...</div>}

            <button className="submit-btn" disabled={isLoading} onClick={handleSubmit}>
              {isLoading ? 'Creating Account...' : 'Create Account'}
            </button>
          </div>
        </div>
      </div>

      {/* MODAL */}
      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <button className="close" onClick={() => setShowModal(false)}>✕</button>
            <iframe
              src={modalType === 'terms' ? '/TermsOfService.html' : '/PrivacyPolicy.html'}
              className="iframe"
            />
          </div>
        </div>
      )}
    </>
  );
};

export default Register;