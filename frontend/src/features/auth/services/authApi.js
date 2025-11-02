import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api'; // Adjust to your backend URL

export const authApi = {
  login: async (credentials) => {
    const response = await axios.post(`${API_BASE_URL}/auth/login`, credentials);
    return response.data;
  },

  register: async (userData) => {
    const response = await axios.post(`${API_BASE_URL}/auth/register`, userData);
    return response.data;
  },

  forgotPassword: async (email) => {
    const response = await axios.post(`${API_BASE_URL}/auth/forgot-password`, { email });
    return response.data;
  },

  resetPassword: async (token, newPassword) => {
    const response = await axios.post(`${API_BASE_URL}/auth/reset-password`, {
      token,
      newPassword
    });
    return response.data;
  },

  verifyOtp: async (email, otp) => {
    const response = await axios.post(`${API_BASE_URL}/auth/verify-otp`, {
      email,
      otp
    });
    return response.data;
  },

  resendOtp: async (email) => {
    const response = await axios.post(`${API_BASE_URL}/auth/resend-otp`, { email });
    return response.data;
  },

  logout: async () => {
    // Clear local storage
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    
    // Optional: Call backend logout if you have session management
    // const response = await axios.post(`${API_BASE_URL}/auth/logout`);
    // return response.data;
    
    return { success: true, message: 'Logged out successfully' };
  },

  getCurrentUser: async () => {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No token found');
    }
    
    const response = await axios.get(`${API_BASE_URL}/auth/me`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  },

  updateProfile: async (userData) => {
    const token = localStorage.getItem('token');
    const response = await axios.put(`${API_BASE_URL}/auth/profile`, userData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  },

  changePassword: async (currentPassword, newPassword) => {
    const token = localStorage.getItem('token');
    const response = await axios.put(`${API_BASE_URL}/auth/change-password`, {
      currentPassword,
      newPassword
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
};

// Optional: Add axios interceptors for automatic token handling
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default authApi;