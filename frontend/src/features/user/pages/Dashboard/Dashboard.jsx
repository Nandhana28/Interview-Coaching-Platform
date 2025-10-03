import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('isLoggedIn');
    navigate('/login');
  };

  const stats = [
    { label: 'Mock Tests Taken', value: '12', color: 'primary' },
    { label: 'Interviews Completed', value: '8', color: 'secondary' },
    { label: 'Skills Improved', value: '15', color: 'accent' },
    { label: 'Current Streak', value: '5 days', color: 'success' }
  ];

  const quickActions = [
    { title: 'Start Mock Test', description: 'Practice with AI-powered tests', color: 'primary' },
    { title: 'Live Interview', description: 'Real-time interview practice', color: 'secondary' },
    { title: 'Skill Builder', description: 'Improve specific skills', color: 'accent' },
    { title: 'Community Chat', description: 'Connect with peers', color: 'success' }
  ];

  return (
    <div className="dashboard">
      {/* Header */}
      <div className="dashboard-header">
        <div className="welcome-section">
          <h1 className="welcome-title">Welcome to InterviewPrep</h1>
          <p className="welcome-subtitle">Ready to ace your next interview? Lets practice and improve your skills</p>
        </div>
        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>

      {/* Stats Grid */}
      <div className="stats-grid">
        {stats.map((stat, index) => (
          <div key={index} className={`stat-card stat-${stat.color}`}>
            <div className="stat-content">
              <h3 className="stat-value">{stat.value}</h3>
              <p className="stat-label">{stat.label}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Quick Actions */}
      <section className="quick-actions-section">
        <h2 className="section-title">Quick Actions</h2>
        <div className="actions-grid">
          {quickActions.map((action, index) => (
            <div key={index} className={`action-card action-${action.color}`}>
              <div className="action-content">
                <h3 className="action-title">{action.title}</h3>
                <p className="action-description">{action.description}</p>
              </div>
              <button className="action-button">Start</button>
            </div>
          ))}
        </div>
      </section>

      {/* Recent Activity */}
      <section className="recent-activity">
        <h2 className="section-title">Recent Activity</h2>
        <div className="activity-list">
          <div className="activity-item">
            <div className="activity-content">
              <p>Completed Mock Technical Interview</p>
              <span className="activity-time">2 hours ago</span>
            </div>
          </div>
          <div className="activity-item">
            <div className="activity-content">
              <p>Improved behavioral answers score by 15%</p>
              <span className="activity-time">1 day ago</span>
            </div>
          </div>
          <div className="activity-item">
            <div className="activity-content">
              <p>Earned Code Master badge</p>
              <span className="activity-time">2 days ago</span>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Dashboard;