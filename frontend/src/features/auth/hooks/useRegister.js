import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authApi } from '../services/authApi';

export const useRegister = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();

  const register = async (userData) => {
    setIsLoading(true);
    setError(null);
    
    try {
      const response = await authApi.register(userData);
      setIsSuccess(true);
      
      // Redirect to login after successful registration
      setTimeout(() => {
        navigate('/login');
      }, 2000);
      
      return response;
    } catch (err) {
      setError(err.response?.data?.error || 'Registration failed. Please try again.');
      setIsSuccess(false);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return {
    register,
    isLoading,
    error,
    isSuccess
  };
};