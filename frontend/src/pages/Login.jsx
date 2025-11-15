import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData(e.target);
    
    const loginData = {
      email: formData.get('email'),
      senha: formData.get('senha')
    };

    try {
      const response = await api.post('/login', loginData);
      
      if (response.data.token) {
        // Salva o token no localStorage
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('userEmail', loginData.email);
        
        alert('Login realizado com sucesso!');
        navigate('/'); // Redireciona para a página inicial
      }
    } catch (error) {
      console.error('Erro no login:', error);
      alert('Email ou senha incorretos!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc] animate-fadeIn">
      <Header />

      <main className="flex-1 flex items-center justify-center py-8 px-4 mt-16 mb-8">
        <div className="w-full max-w-[400px] p-8 text-center">
          <img
            src="/assets/img/logo2.png"
            className="w-[180px] h-[180px] md:w-[250px] md:h-[250px] rounded-full mx-auto mb-6 shadow-xl object-cover"
            alt="logo"
          />

          <h2 className="text-2xl md:text-3xl mb-6 font-semibold">Entre na Onda!</h2>

          <form onSubmit={handleLogin}>
            {/* Email (mudei de "Usuário" para "Email" para bater com a API) */}
            <div className="relative mb-4">
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-500">📧</span>
              <input
                type="email"
                name="email"
                placeholder="Email"
                className="w-full bg-white/95 text-black px-10 py-3 rounded-full outline-none focus:ring-2 focus:ring-purple-500 border border-transparent focus:border-purple-500 transition-all"
                required
              />
            </div>

            {/* Senha */}
            <div className="relative mb-4">
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-500">🔒</span>

              <input
                type={showPassword ? "text" : "password"}
                name="senha"
                placeholder="Senha"
                className="w-full bg-white/95 text-black px-10 py-3 rounded-full outline-none focus:ring-2 focus:ring-purple-500 border border-transparent focus:border-purple-500 transition-all"
                required
              />

              <span
                className="absolute right-4 top-1/2 -translate-y-1/2 cursor-pointer text-gray-500 hover:text-gray-700 transition-colors"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? "🙈" : "👁️"}
              </span>
            </div>

            <a href="/RecuperarSenha" className="text-blue-200 text-sm block mb-6 hover:text-blue-300 transition-colors">
              Esqueceu a senha?
            </a>

            <button 
              type="submit" 
              className="w-full bg-gradient-to-r from-[#7c3aed] to-[#a855f7] text-white py-3 rounded-full hover:from-[#a855f7] hover:to-[#c084fc] transition-all duration-300 font-bold shadow-lg hover:shadow-purple-500/25 mb-4 btn-login"
              disabled={loading}
            >
              {loading ? 'Entrando...' : 'Entrar'}
            </button>

            <a
              href="/Cadastro"
              className="block w-full py-3 rounded-full bg-gradient-to-r from-[#E0B3FF] to-[#9A60F3] text-white font-bold hover:from-[#9A60F3] hover:to-[#E0B3FF] transition-all duration-300 shadow-lg hover:shadow-purple-500/25 btn-login"
            >
              Criar Conta
            </a>
          </form>
        </div>
      </main>

      <Footer />
    </div>
  );
}