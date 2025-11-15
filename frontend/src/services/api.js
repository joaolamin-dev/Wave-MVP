import axios from 'axios';

// Use proxy no desenvolvimento, API direta em produção
const API_BASE_URL = import.meta.env.DEV 
  ? '/api'  // Usa o proxy do Vite em desenvolvimento
  : 'https://wave-backend-v5zn.onrender.com'; // URL direta em produção

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