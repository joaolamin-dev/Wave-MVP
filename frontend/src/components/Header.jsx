import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import { artistas } from "../data/artistas";
import api from "../services/api";

export default function Header() {
  const [busca, setBusca] = useState("");
  const [resultados, setResultados] = useState([]);
  const [mostrarDropdown, setMostrarDropdown] = useState(false);
  const [mouseSobre, setMouseSobre] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const [usuarioLogado, setUsuarioLogado] = useState(null);
  const [mostrarMenuUsuario, setMostrarMenuUsuario] = useState(false);
  const [carregando, setCarregando] = useState(false);

  const navigate = useNavigate();
  const dropdownRef = useRef(null);
  const menuUsuarioRef = useRef(null);

  // Verificar se usuário está logado e buscar dados da API
  useEffect(() => {
    const token = localStorage.getItem('token');
    const userEmail = localStorage.getItem('userEmail');
    
    if (token && userEmail) {
      buscarDadosUsuario();
    }
  }, []);

  // Buscar dados reais do usuário na API
  const buscarDadosUsuario = async () => {
    setCarregando(true);
    try {
      // Primeiro precisamos encontrar o ID do usuário pelo email
      const response = await api.get('/usuarios');
      const todosUsuarios = response.data;
      
      // Encontrar o usuário pelo email
      const usuarioEncontrado = todosUsuarios.find(usuario => 
        usuario.email === localStorage.getItem('userEmail')
      );
      
      if (usuarioEncontrado) {
        // Agora buscar os dados completos pelo ID
        const userResponse = await api.get(`/usuarios/${usuarioEncontrado.id}`);
        setUsuarioLogado({
          id: userResponse.data.id,
          nome: userResponse.data.nome,
          email: userResponse.data.email
        });
      }
    } catch (error) {
      console.error('Erro ao buscar dados do usuário:', error);
      // Se der erro, faz logout para limpar dados inválidos
      handleLogout();
    } finally {
      setCarregando(false);
    }
  };

  // Detecta scroll - SEMPRE considera como scrolled para manter tamanho reduzido
  useEffect(() => {
    function handleScroll() {
      setScrolled(true); // SEMPRE true para manter tamanho reduzido
    }
    window.addEventListener("scroll", handleScroll);
    // Força o estado scrolled como true imediatamente
    setScrolled(true);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Detectar clique fora dos dropdowns
  useEffect(() => {
    function handleClickOutside(e) {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setMostrarDropdown(false);
      }
      if (menuUsuarioRef.current && !menuUsuarioRef.current.contains(e.target)) {
        setMostrarMenuUsuario(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // Função de logout
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    setUsuarioLogado(null);
    setMostrarMenuUsuario(false);
    navigate('/');
  };

  // Filtrar artistas
  function filtrar(texto) {
    setBusca(texto);

    if (texto.trim().length === 0) {
      setMostrarDropdown(false);
      return;
    }

    const filtro = artistas.filter((a) =>
      a.nome.toLowerCase().includes(texto.toLowerCase())
    );

    setResultados(filtro);
    setMostrarDropdown(filtro.length > 0);
  }

  function pesquisarEnter(e) {
    if (e.key === "Enter" && resultados.length > 0) {
      navigate(resultados[0].path);
      setMostrarDropdown(false);
    }
  }

  function selecionar(artista) {
    navigate(artista.path);
    setMostrarDropdown(false);
    setBusca("");
  }

  return (
    <header
      onMouseEnter={() => setMouseSobre(true)}
      onMouseLeave={() => setMouseSobre(false)}
      className={`
        fixed top-0 left-0 w-full z-50 flex items-center px-8 transition-all duration-500

        ${
          // SEMPRE usa o estilo "scrolled" - py-1 (menor)
          mouseSobre
            ? "bg-[#34034b]/90 backdrop-blur-xl py-2 shadow-xl"  // Mouse sobre → sólido
            : "bg-transparent backdrop-blur-sm py-1"             // Sem mouse → transparente
        }
      `}
    >
      {/* LOGO */}
      <div className="flex-1 flex justify-start -ml-2">
        <Link to="/">
          <img
            src="/assets/img/logo.png"
            alt="Wave Logo"
            className="h-10 drop-shadow-xl transition-all duration-300 hover:scale-110 hover:drop-shadow-[0_0_20px_#ff00cc]"
          />
        </Link>
      </div>

      {/* CAMPO DE PESQUISA */}
      <div className="flex-1 flex justify-center relative" ref={dropdownRef}>
        <input
          type="text"
          placeholder="Pesquise aqui seu artista favorito"
          className={`
            bg-[#5e237d]/80 backdrop-blur-xl text-white px-5 py-1.5 rounded-full
            w-[400px] outline-none transition-all duration-300 text-sm

            ${mouseSobre ? "shadow-[0_0_15px_#ff00cc]" : "shadow-none"}
          `}
          value={busca}
          onChange={(e) => filtrar(e.target.value)}
          onKeyDown={pesquisarEnter}
          onFocus={() => busca.length > 0 && setMostrarDropdown(true)}
        />

        {/* DROPDOWN */}
        {mostrarDropdown && (
          <div className="absolute top-12 w-[400px] bg-[#2b0040]/90 backdrop-blur-xl border border-white/20 rounded-xl shadow-xl text-white p-2 z-50 text-sm">
            {resultados.map((artista, index) => (
              <div
                key={index}
                className="px-3 py-1.5 hover:bg-[#ff00cc]/30 hover:cursor-pointer rounded-lg transition"
                onClick={() => selecionar(artista)}
              >
                {artista.nome}
              </div>
            ))}
          </div>
        )}
      </div>

      {/* MENU - DINÂMICO BASEADO NO LOGIN */}
      <nav className="flex-1 flex justify-end gap-4 text-white font-medium text-sm">
        <Link className="nav-btn" to="/">Home</Link>
        
        {usuarioLogado ? (
          // USUÁRIO LOGADO - NOME REAL DA API
          <div className="relative" ref={menuUsuarioRef}>
            <button
              onClick={() => setMostrarMenuUsuario(!mostrarMenuUsuario)}
              className="nav-btn flex items-center gap-2 bg-[#ff00cc]/20 px-4 py-2 rounded-full hover:bg-[#ff00cc]/30 transition-all"
              disabled={carregando}
            >
              {carregando ? (
                <>
                  <span>⏳</span>
                  Carregando...
                </>
              ) : (
                <>
                  <span>👋</span>
                  Bem-vindo, {usuarioLogado.nome}!
                  <span className={`transform transition-transform ${mostrarMenuUsuario ? 'rotate-180' : ''}`}>
                    ▼
                  </span>
                </>
              )}
            </button>

            {/* MENU DO USUÁRIO */}
            {mostrarMenuUsuario && !carregando && (
              <div className="absolute top-12 right-0 w-48 bg-[#2b0040]/95 backdrop-blur-xl border border-white/20 rounded-xl shadow-xl text-white p-2 z-50">
                <div className="px-3 py-2 border-b border-white/10">
                  <p className="text-sm font-semibold">{usuarioLogado.nome}</p>
                  <p className="text-xs text-white/60">{usuarioLogado.email}</p>
                </div>
                
                <Link 
                  to="/perfil" 
                  className="block px-3 py-2 hover:bg-[#ff00cc]/30 rounded-lg transition text-sm"
                  onClick={() => setMostrarMenuUsuario(false)}
                >
                  Meu Perfil
                </Link>
                
                <Link 
                  to="/configuracoes" 
                  className="block px-3 py-2 hover:bg-[#ff00cc]/30 rounded-lg transition text-sm"
                  onClick={() => setMostrarMenuUsuario(false)}
                >
                  Configurações
                </Link>
                
                <button
                  onClick={handleLogout}
                  className="w-full text-left px-3 py-2 hover:bg-red-500/30 rounded-lg transition text-sm mt-2 border-t border-white/10 pt-2"
                >
                  Sair
                </button>
              </div>
            )}
          </div>
        ) : (
          // USUÁRIO NÃO LOGADO
          <>
            <Link className="nav-btn" to="/Cadastro">Entre na Onda</Link>
            <Link className="nav-btn" to="/Login">Login</Link>
          </>
        )}
        
        <Link className="nav-btn" to="/Premium">Seja Premium</Link>
      </nav>
    </header>
  );
}