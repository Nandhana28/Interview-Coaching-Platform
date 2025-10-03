import { useState } from 'react';
import { useAuth } from '../../../../shared/hooks/useAuth';
import { authApi } from '../services/authApi';
import { useNavigate } from 'react-router-dom';

export const useLogin = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (email, password) => {
    setIsLoading(true);
    setError('');

    try {
      const token = await authApi.login(email, password);
      
      // For now, create a mock user object - you'll get this from your backend
      const userData = {
        email: email,
        firstName: 'User',
        lastName: 'Demo',
        roles: ['USER']
      };
      
      login(userData, token);
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return {
    handleLogin,
    isLoading,
    error
  };
};