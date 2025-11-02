import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Homepage.css';

const Homepage = () => {
  const navigate = useNavigate();

  const handleUserLogin = () => {
    navigate('/login');
  };

  const handleAdminLogin = () => {
    navigate('/admin-login');
  };

  const features = [
    {
      icon: '🎯',
      title: 'AI-Powered Mock Interviews',
      description: 'Practice with realistic interviews and get instant AI feedback'
    },
    {
      icon: '💼',
      title: 'Skill Building',
      description: 'Master coding challenges, system design, and behavioral questions'
    },
    {
      icon: '🎮',
      title: 'Gamified Learning',
      description: 'Earn badges, climb leaderboards, and track your progress'
    },
    {
      icon: '🤖',
      title: 'AI Coach',
      description: 'Personalized guidance and performance analysis'
    },
    {
      icon: '💬',
      title: 'Community Banter',
      description: 'Connect with peers, share experiences, and learn together'
    },
    {
      icon: '📊',
      title: 'Progress Analytics',
      description: 'Detailed insights and improvement recommendations'
    }
  ];

  const stats = [
    { number: '10K+', label: 'Users Trained' },
    { number: '95%', label: 'Success Rate' },
    { number: '50+', label: 'Companies' },
    { number: '24/7', label: 'AI Support' }
  ];

  return (
    <div className="homepage">
      {/* Navigation Bar */}
      <nav className="navbar">
        <div className="nav-container">
          <div className="nav-logo">
            <span className="logo-icon">🚀</span>
            <span className="logo-text">InterviewPro</span>
          </div>
          
          <div className="nav-links">
            <a href="#features">Features</a>
            <a href="#how-it-works">How It Works</a>
            <a href="#testimonials">Success Stories</a>
            <a href="#pricing">Pricing</a>
          </div>

          <div className="nav-auth">
            <button className="admin-login-btn" onClick={handleAdminLogin}>
              <span className="btn-icon">👑</span>
              Admin Login
            </button>
            <button className="user-login-btn" onClick={handleUserLogin}>
              <span className="btn-icon">🔑</span>
              User Login
            </button>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-background">
          <div className="floating-shapes">
            <div className="shape shape-1"></div>
            <div className="shape shape-2"></div>
            <div className="shape shape-3"></div>
            <div className="shape shape-4"></div>
          </div>
          <div className="particles-container">
            {[...Array(30)].map((_, i) => (
              <div key={i} className="particle" style={{
                left: `${Math.random() * 100}%`,
                animationDelay: `${Math.random() * 15}s`,
                animationDuration: `${20 + Math.random() * 10}s`
              }}></div>
            ))}
          </div>
        </div>
        
        <div className="hero-content">
          <div className="hero-text">
            <div className="badge">
              <span className="badge-icon">⭐</span>
              Trusted by Top Tech Companies
            </div>
            <h1 className="hero-title">
              Master Your <span className="gradient-text">Dream Job</span> 
              <br />
              Interview
            </h1>
            <p className="hero-subtitle">
              AI-powered interview platform with realistic mock sessions, 
              instant feedback, and personalized coaching. 
              <span className="highlight"> Join elite candidates worldwide.</span>
            </p>
            
            <div className="hero-buttons">
              <button className="cta-button primary" onClick={handleUserLogin}>
                <span className="btn-sparkle">✨</span>
                Start Free Trial
                <span className="btn-arrow">→</span>
              </button>
              <button className="cta-button secondary">
                <span className="btn-play">▶</span>
                Watch Demo
              </button>
            </div>

            <div className="hero-stats">
              {stats.map((stat, index) => (
                <div key={index} className="stat-item">
                  <div className="stat-number">{stat.number}</div>
                  <div className="stat-label">{stat.label}</div>
                </div>
              ))}
            </div>
          </div>
          
          <div className="hero-visual">
            {/* Floating Cards */}
            <div className="floating-card card-1">
              <div className="card-icon">🎯</div>
              <div className="card-content">
                <div className="card-title">AI Feedback</div>
                <div className="card-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            </div>
            <div className="floating-card card-2">
              <div className="card-icon">📈</div>
              <div className="card-content">
                <div className="card-title">Progress</div>
                <div className="card-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            </div>
            <div className="floating-card card-3">
              <div className="card-icon">🏆</div>
              <div className="card-content">
                <div className="card-title">Rank #1</div>
                <div className="card-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            </div>
            
            {/* Main Visual */}
            <div className="main-visual">
              <div className="dashboard-preview">
                <div className="screen-header">
                  <div className="header-dots">
                    <span className="dot red"></span>
                    <span className="dot yellow"></span>
                    <span className="dot green"></span>
                  </div>
                </div>
                <div className="screen-content">
                  <div className="code-window"></div>
                  <div className="feedback-panel"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section id="features" className="features-section">
        <div className="container">
          <div className="section-header">
            <div className="section-badge">Features</div>
            <h2 className="section-title">
              Everything You Need to <span className="gradient-text">Ace Interviews</span>
            </h2>
            <p className="section-subtitle">
              Comprehensive tools and features designed to transform your interview performance
            </p>
          </div>
          
          <div className="features-grid">
            {features.map((feature, index) => (
              <div key={index} className="feature-card">
                <div className="feature-icon-wrapper">
                  <div className="feature-icon">{feature.icon}</div>
                  <div className="feature-glow"></div>
                </div>
                <h3 className="feature-title">{feature.title}</h3>
                <p className="feature-description">{feature.description}</p>
                <div className="feature-hover"></div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta-section">
        <div className="cta-background">
          <div className="cta-shapes">
            <div className="cta-shape cta-shape-1"></div>
            <div className="cta-shape cta-shape-2"></div>
          </div>
        </div>
        <div className="cta-content">
          <h2 className="cta-title">
            Ready to Transform Your Career?
          </h2>
          <p className="cta-subtitle">
            Join thousands of successful candidates who landed their dream jobs at top companies
          </p>
          <button className="cta-button large" onClick={handleUserLogin}>
            <span className="btn-sparkle">⚡</span>
            Start Your Journey Today
            <span className="btn-arrow">→</span>
          </button>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        <div className="container">
          <div className="footer-content">
            <div className="footer-brand">
              <div className="logo">
                <span className="logo-icon">🚀</span>
                <span className="logo-text">InterviewPro</span>
              </div>
              <p>Elite AI-powered interview coaching platform</p>
              <div className="social-links">
                <a href="#" className="social-link">📘</a>
                <a href="#" className="social-link">🐦</a>
                <a href="#" className="social-link">💼</a>
                <a href="#" className="social-link">📸</a>
              </div>
            </div>
            
            <div className="footer-links">
              <div className="footer-column">
                <h4>Platform</h4>
                <a href="#features">Features</a>
                <a href="#how-it-works">How It Works</a>
                <a href="#pricing">Pricing</a>
                <a href="#enterprise">Enterprise</a>
              </div>
              
              <div className="footer-column">
                <h4>Resources</h4>
                <a href="#blog">Blog</a>
                <a href="#guides">Guides</a>
                <a href="#webinars">Webinars</a>
                <a href="#community">Community</a>
              </div>
              
              <div className="footer-column">
                <h4>Company</h4>
                <a href="#about">About</a>
                <a href="#careers">Careers</a>
                <a href="#contact">Contact</a>
                <a href="#press">Press</a>
              </div>
              
              <div className="footer-column">
                <h4>Legal</h4>
                <a href="#privacy">Privacy</a>
                <a href="#terms">Terms</a>
                <a href="#security">Security</a>
                <a href="#cookies">Cookies</a>
              </div>
            </div>
          </div>
          
          <div className="footer-bottom">
            <p>&copy; 2024 InterviewPro. All rights reserved. | Elite Interview Preparation</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Homepage;