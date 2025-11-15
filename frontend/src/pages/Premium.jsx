import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState } from "react";

export default function Premium() {
  const [confirm, setConfirm] = useState(false);

  function handleSubmit(e) {
    e.preventDefault();
    setConfirm(true);
    setTimeout(() => setConfirm(false), 2500);
  }

  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc]">
      <Header />

      <main className="flex-1 flex flex-col items-center justify-center py-8 px-4 mt-16 mb-8">
        <section className="text-center w-full max-w-4xl">
          <h1 className="text-4xl md:text-5xl font-bold mb-2">Plano Wave Premium</h1>
          <p className="text-lg text-white/80 mb-8">Eleve sua experiência musical</p>

          {/* Benefícios */}
          <div className="bg-white/10 p-8 rounded-2xl mt-6 shadow-2xl border border-white/10 backdrop-blur-sm max-w-2xl mx-auto">
            <h2 className="text-2xl md:text-3xl font-semibold mb-6">Benefícios do Premium</h2>
            <ul className="space-y-4 text-lg">
              <li className="flex items-center justify-center gap-3">
                <span className="text-2xl">🎵</span>
                Acesso ilimitado a músicas SEM anúncios
              </li>
              <li className="flex items-center justify-center gap-3">
                <span className="text-2xl">⬇️</span>
                Downloads liberados para ouvir offline
              </li>
              <li className="flex items-center justify-center gap-3">
                <span className="text-2xl">🟢</span>
                Acesso total às transmissões e comentários
              </li>
              <li className="flex items-center justify-center gap-3">
                <span className="text-2xl">🔁</span>
                Trocas de faixa ilimitadas
              </li>
              <li className="flex items-center justify-center gap-3">
                <span className="text-2xl">🎤</span>
                Recursos exclusivos para artistas
              </li>
            </ul>
          </div>

          {/* Preço */}
          <div className="bg-white/10 p-8 rounded-2xl mt-8 shadow-2xl border border-white/10 backdrop-blur-sm max-w-md mx-auto">
            <span className="text-sm opacity-75 block mb-2">Apenas</span>
            <span className="text-4xl font-bold block mb-4">R$ 29,90/mês</span>
            
            <button className="w-full bg-gradient-to-r from-[#7c3aed] to-[#a855f7] py-4 rounded-full hover:from-[#a855f7] hover:to-[#c084fc] transition-all duration-300 font-bold text-lg shadow-lg hover:shadow-purple-500/25 hover:scale-105">
              Assinar Agora
            </button>
            
            {confirm && (
              <div className="mt-4 p-3 bg-green-500/20 border border-green-500 rounded-lg text-green-300">
                🎉 Parabéns! Você agora é Premium!
              </div>
            )}
          </div>

          {/* Garantia */}
          <div className="mt-8 text-white/60 text-sm max-w-md mx-auto">
            <p>✅ Cancele quando quiser • ✅ 7 dias de garantia</p>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
}