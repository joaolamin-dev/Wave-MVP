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

const rotaPublica = ({ url, method }) =>
  url === '/login' || (url === '/usuarios' && method === 'post');

// Interceptor para adicionar token automaticamente
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && !rotaPublica(config)) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

// Em rota pública, 401 é credencial errada, não sessão expirada: quem chamou é que trata.
api.interceptors.response.use(undefined, (error) => {
  if (error.response?.status === 401 && !rotaPublica(error.config)) {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    if (window.location.pathname.toLowerCase() !== '/login') {
      window.location.assign('/Login');
    }
  }
  return Promise.reject(error);
});

export default api;