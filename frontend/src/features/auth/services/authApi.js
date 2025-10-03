import { apiClient } from '../../../../shared/services/apiClient';

export const authApi = {
  login: async (email, password) => {
    const response = await apiClient.post('/auth/login', null, {
      params: { email, password }
    });
    return response.data;
  },

  register: async (userData) => {
    const response = await apiClient.post('/auth/register', userData);
    return response.data;
  },

  requestOtp: async (email) => {
    const response = await apiClient.post('/auth/request-otp', null, {
      params: { email }
    });
    return response.data;
  },

  verifyOtp: async (email, otp) => {
    const response = await apiClient.post('/auth/verify-otp', null, {
      params: { email, otp }
    });
    return response.data;
  }
};