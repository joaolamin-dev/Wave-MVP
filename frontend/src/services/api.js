import axios from 'axios';

// Aplicação em Desenvolvimento
const API_BASE_URL = import.meta.env.DEV
    ? 'http://localhost:8080'  // backend local
    : 'https://wave-backend-v5zn.onrender.com'; // URL de produção

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para adicionar token automaticamente
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  
  return config;
});

export default api;