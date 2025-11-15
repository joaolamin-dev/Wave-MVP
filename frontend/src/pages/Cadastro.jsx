import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Cadastro() {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);

  const formData = new FormData(e.target);
  
  const userData = {
    nome: formData.get('nome'),
    email: formData.get('email'),
    senha: formData.get('senha')
  };

  console.log('📤 Dados sendo enviados:', userData);

  try {
    const response = await api.post('/usuarios', userData);
    console.log('✅ Resposta da API:', response);
    
    if (response.status === 201) {
      alert('Cadastro realizado com sucesso! Faça login para continuar.');
      navigate('/Login');
    }
  } catch (error) {
    console.error('❌ Erro detalhado:', error);
    console.log('📊 Response error:', error.response);
    
    // Mostra o erro específico da API
    if (error.response?.data?.message) {
      alert(`Erro: ${error.response.data.message}`);
    } else if (error.response?.data) {
      alert(`Erro: ${JSON.stringify(error.response.data)}`);
    } else if (error.message) {
      alert(`Erro de conexão: ${error.message}`);
    } else {
      alert('Erro ao realizar cadastro. Tente novamente.');
    }
  } finally {
    setLoading(false);
  }
};

  return (
    <div 
      className="min-h-screen flex flex-col text-white relative"
      style={{
        background: 'linear-gradient(to bottom right, #4b0082, #da70d6, #f8b6ff)',
        opacity: 0,
        animation: 'fadeIn 0.6s ease forwards'
      }}
    >
      {/* SEUS ESTILOS ORIGINAIS - MANTIDOS INTACTOS */}
      <style>
        {`
          @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
          }
          
          .cadastro-input {
            width: 100%;
            padding: 0.8rem 0.8rem 0.8rem 40px;
            border-radius: 30px;
            border: none;
            font-size: 1rem;
            color: #333;
            background-color: #fff;
          }
          
          .cadastro-select {
            width: 100%;
            padding: 0.8rem;
            border-radius: 30px;
            border: none;
            font-size: 1rem;
            color: #333;
            background-color: #fff;
          }
          
          .data-input {
            text-align: center;
            padding-left: 0.8rem !important;
          }
          
          .cadastro-btn {
            width: 100%;
            padding: 1rem;
            border: none;
            border-radius: 30px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s ease;
            background: linear-gradient(to right, #ff00cc, #3f0343);
            color: white;
            margin-top: 1rem;
          }
          
          .cadastro-btn:hover {
            transform: scale(1.05);
            filter: brightness(1.1);
          }

          .cadastro-btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
          }
        `}
      </style>

      <Header />

      <main className="flex-1 flex items-center justify-center py-8 px-4 mt-16 mb-8">
        <div className="w-full max-w-md">
          {/* Logo */}
          <div className="flex justify-center mb-6">
            <img 
              src="/assets/img/iconewaveP&B.png" 
              alt="Ícone Wave" 
              className="w-16 h-16"
            />
          </div>

          <h2 className="text-center text-2xl md:text-3xl font-bold mb-6">Se Inscreva e Entre na Onda</h2>
          
          <form className="space-y-4" onSubmit={handleSubmit}>
            {/* Nome completo - AGORA É OBRIGATÓRIO PARA A API */}
            <div className="input-group">
              <input
                type="text"
                name="nome"
                placeholder="Nome completo"
                className="cadastro-input pl-10"
                required
              />
              <i className="fas fa-user absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500"></i>
              <small className="text-white/80 text-xs mt-1 block pl-4">
                Este nome aparecerá no seu perfil
              </small>
            </div>

            {/* Email - OBRIGATÓRIO PARA A API */}
            <div className="input-group">
              <input
                type="email"
                name="email"
                placeholder="Digite seu e-mail"
                className="cadastro-input pl-10"
                required
              />
              <i className="fas fa-envelope absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500"></i>
            </div>

            {/* Senha - OBRIGATÓRIO PARA A API */}
            <div className="input-group relative">
              <input
                type="password"
                name="senha"
                placeholder="Senha"
                className="cadastro-input"
                required
                minLength="6"
              />
              <i className="fas fa-lock absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500"></i>
            </div>

            {/* Campos que NÃO vão para a API (mantidos do seu código original) */}
            <div className="input-group">
              <input
                type="text"
                name="usuario"
                placeholder="Nome de usuário (apelido)"
                className="cadastro-input"
              />
              <small className="text-white/80 text-xs mt-1 block pl-4">
                Nome de perfil único (ex: seuapelido)
              </small>
            </div>

            {/* Data de nascimento */}
            <div className="input-group">
              <label className="block text-white mb-2 font-medium text-sm pl-4">Data de nascimento</label>
              <div className="flex gap-2">
                <input
                  type="text"
                  name="dia"
                  placeholder="dd"
                  maxLength="2"
                  className="cadastro-input data-input flex-1"
                />
                <input
                  type="text"
                  name="mes"
                  placeholder="mm"
                  maxLength="2"
                  className="cadastro-input data-input flex-1"
                />
                <input
                  type="text"
                  name="ano"
                  placeholder="aaaa"
                  maxLength="4"
                  className="cadastro-input data-input flex-1"
                />
              </div>
            </div>

            {/* Gênero */}
            <div className="input-group">
              <label className="block text-white mb-2 font-medium text-sm pl-4">Gênero</label>
              <select
                name="genero"
                className="cadastro-select"
              >
                <option value="">Selecione</option>
                <option value="Homem">Homem</option>
                <option value="Mulher">Mulher</option>
                <option value="Outro">Outro</option>
                <option value="Prefiro não informar">Prefiro não informar</option>
              </select>
            </div>

            {/* Plano */}
            <div className="input-group">
              <label className="block text-white mb-2 font-medium text-sm pl-4">Plano</label>
              <select
                name="plano"
                className="cadastro-select"
              >
                <option value="">Selecione seu plano</option>
                <option value="Free">Free - R$ 0,00/mês</option>
                <option value="Universitario">Universitário - R$ 14,99/mês</option>
                <option value="Premium">Premium - R$ 29,90/mês</option>
                <option value="Familia">Família - R$ 49,90/mês</option>
              </select>
              <small className="text-white/80 text-xs mt-1 block pl-4">
                Você pode alterar seu plano a qualquer momento
              </small>
            </div>

            {/* Botão de cadastro */}
            <button 
              type="submit" 
              className="cadastro-btn"
              disabled={loading}
            >
              {loading ? 'Cadastrando...' : 'Concluir cadastro'}
            </button>

            {/* Link para login */}
            <div className="text-center pt-4">
              <a
                href="/Login"
                className="text-white/80 hover:text-white transition-colors duration-300 text-sm"
              >
                Já possui uma conta? Faça login.
              </a>
            </div>
          </form>
        </div>
      </main>

      <Footer />
    </div>
  );
}