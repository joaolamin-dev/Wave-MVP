import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../components/Header";
import Footer from "../components/Footer";
import api from "../services/api";

export default function Perfil() {
    const [usuario, setUsuario] = useState({ nome: "", email: "" });
    const [playlists, setPlaylists] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const email = localStorage.getItem('userEmail');
        const token = localStorage.getItem('token');

        // Se não estiver logado, chuta pro login
        if (!email || !token) {
            navigate('/Login');
            return;
        }

        async function carregarDadosDoPerfil() {
            try {
                // 1. Buscar os dados do usuário logado
                const resUsuarios = await api.get('/usuarios?size=100');
                const userEncontrado = resUsuarios.data.content.find(u => u.email === email);
                if (userEncontrado) {
                    setUsuario(userEncontrado);
                }

                // 2. Buscar as playlists exclusivas dele
                const resPlaylists = await api.get('/meu-perfil/playlists');
                setPlaylists(resPlaylists.data);

            } catch (error) {
                console.error("Erro ao carregar perfil:", error);
            } finally {
                setCarregando(false);
            }
        }

        carregarDadosDoPerfil();
    }, [navigate]);

    return (
        <div className="min-h-screen flex flex-col bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc] text-white">
            <Header />

            <main className="flex-1 p-10 pt-[120px] max-w-7xl mx-auto w-full">
                {carregando ? (
                    <div className="flex justify-center items-center h-full">
                        <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-[#ff00cc]"></div>
                    </div>
                ) : (
                    <>
                        {/* CABEÇALHO DO PERFIL */}
                        <section className="bg-[#2b0040]/60 backdrop-blur-xl border border-white/10 rounded-3xl p-10 flex items-center gap-8 shadow-2xl relative overflow-hidden">
                            {/* Efeito de brilho de fundo */}
                            <div className="absolute top-0 right-0 w-64 h-64 bg-[#ff00cc]/20 rounded-full blur-3xl"></div>

                            <div className="relative w-40 h-40 rounded-full overflow-hidden border-4 border-[#ff00cc] shadow-[0_0_20px_#ff00cc]">
                                {/* Placeholder de avatar gerado pela inicial do nome */}
                                <div className="w-full h-full bg-gradient-to-br from-[#7c3aed] to-[#ff00cc] flex items-center justify-center text-6xl font-bold">
                                    {usuario.nome ? usuario.nome.charAt(0).toUpperCase() : "U"}
                                </div>
                            </div>

                            <div className="relative z-10">
                                <p className="text-[#ff66cc] font-semibold tracking-wider text-sm mb-1 uppercase">Perfil Verificado</p>
                                <h1 className="text-5xl font-bold mb-2 animate-neonPulse">{usuario.nome}</h1>
                                <p className="text-white/60 text-lg">{usuario.email}</p>

                                <div className="flex gap-4 mt-6">
                  <span className="bg-white/10 px-4 py-2 rounded-full text-sm backdrop-blur-sm border border-white/5">
                    🎵 {playlists.length} Playlists salvas
                  </span>
                                    <span className="bg-[#ff00cc]/20 text-[#ff66cc] px-4 py-2 rounded-full text-sm backdrop-blur-sm border border-[#ff00cc]/30">
                    👑 Assinante Premium
                  </span>
                                </div>
                            </div>
                        </section>

                        {/* SEÇÃO DE PLAYLISTS */}
                        <section className="mt-16">
                            <h2 className="text-3xl font-bold mb-8 flex items-center gap-3">
                                Minhas Playlists
                                <span className="text-sm font-normal bg-white/10 px-3 py-1 rounded-full">{playlists.length}</span>
                            </h2>

                            {playlists.length === 0 ? (
                                <div className="text-center py-20 bg-white/5 rounded-3xl border border-white/10 border-dashed">
                                    <div className="text-6xl mb-4 opacity-50">🎧</div>
                                    <h3 className="text-xl font-semibold mb-2">Sua biblioteca está vazia</h3>
                                    <p className="text-white/50">Você ainda não criou nenhuma playlist com suas músicas favoritas.</p>
                                </div>
                            ) : (
                                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                    {playlists.map((playlist) => (
                                        <div
                                            key={playlist.id}
                                            className="bg-white/10 backdrop-blur-md rounded-2xl p-6 border border-white/10 hover:bg-white/20 hover:scale-[1.02] transition-all duration-300 group cursor-pointer"
                                        >
                                            <div className="w-full h-40 bg-gradient-to-br from-purple-900 to-[#2c003e] rounded-xl mb-4 flex items-center justify-center relative overflow-hidden shadow-lg group-hover:shadow-[0_0_15px_#ff00cc]/50 transition-all">
                                                <div className="text-5xl group-hover:scale-125 transition-transform duration-500">💿</div>
                                                {/* Botão de play que aparece no hover */}
                                                <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 flex items-center justify-center transition-opacity duration-300">
                                                    <div className="w-12 h-12 bg-[#ff00cc] rounded-full flex items-center justify-center shadow-[0_0_15px_#ff00cc]">
                                                        <svg className="w-6 h-6 text-white ml-1" viewBox="0 0 24 24" fill="currentColor"><path d="M8 5v14l11-7z"/></svg>
                                                    </div>
                                                </div>
                                            </div>

                                            <h3 className="text-xl font-bold mb-1 truncate">{playlist.nome}</h3>
                                            <p className="text-sm text-white/60 mb-4">{playlist.musicas.length} músicas</p>

                                            {/* Lista prévia das músicas */}
                                            <div className="space-y-2">
                                                {playlist.musicas.slice(0, 3).map((musica, index) => (
                                                    <div key={musica.id} className="text-sm text-white/80 truncate flex items-center gap-2">
                                                        <span className="text-xs text-white/40">{index + 1}</span>
                                                        {musica.titulo} - <span className="text-white/50">{musica.artistaNome}</span>
                                                    </div>
                                                ))}
                                                {playlist.musicas.length > 3 && (
                                                    <div className="text-xs text-[#ff66cc] mt-2">+ {playlist.musicas.length - 3} mais...</div>
                                                )}
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </section>
                    </>
                )}
            </main>

            <Footer />
        </div>
    );
}