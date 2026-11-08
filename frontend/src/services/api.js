import axios from 'axios';

// Sem a URL, as chamadas iriam para o próprio site, e o rewrite do SPA responderia 200 com HTML.
if (import.meta.env.PROD && !import.meta.env.VITE_API_URL) {
  throw new Error('VITE_API_URL não definida: configure a variável e refaça o build do frontend.');
}

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

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