import Header from "../components/Header";
import Footer from "../components/Footer";

export default function Sobre() {
  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc]">
      <Header />

      <main className="flex-1 flex justify-center mt-24 mb-10 px-4">
        <div className="bg-black/25 backdrop-blur-md shadow-xl rounded-xl p-10 max-w-[1200px] w-full">

          {/* TÍTULO */}
          <div className="text-center mb-10">
            <h1 className="text-4xl font-semibold drop-shadow-[0_0_10px_#ff66cc]">
              Sobre o Wave
            </h1>
          </div>

          {/* TEXTO */}
          <p className="text-lg leading-8 text-justify max-w-[900px] mx-auto mb-12">
            Somos uma plataforma de streaming inovadora, criada para transformar a forma como
            você descobre artistas, escuta músicas e vive experiências sonoras.  
            Nosso objetivo é trazer tecnologia, acessibilidade e estilo — tudo em uma única onda.
            Aqui você navega por diferentes gêneros, encontra artistas que ama e acompanha novidades
            de todos os cantos do mundo.  
            O Wave nasceu para quem vive música intensamente.
          </p>

          {/* ===== FOTOS ===== */}
          <div className="flex flex-col items-center gap-10 mt-10">

            {/* LINHA 1 - 3 CARDS */}
            <div className="flex justify-center gap-10 flex-wrap">
              {/* CARD 1 */}
              <div className="bg-white/10 p-5 rounded-xl shadow-lg backdrop-blur-md hover:scale-105 transition transform duration-300 border border-pink-500/30 hover:border-pink-400/80 hover:shadow-[0_0_20px_#ff2ad1]">
                <img
                  src="/assets/img/joaolamin.jpg"
                  alt="Fundador"
                  className="w-[140px] h-[140px] rounded-full object-cover shadow-xl
                             border-4 border-pink-400 hover:border-purple-500 
                             transition duration-300 hover:shadow-[0_0_15px_#ff29d1]"
                />
                <p className="mt-3 font-semibold text-center">João Lamin</p>
              </div>

              {/* CARD 2 */}
              <div className="bg-white/10 p-5 rounded-xl shadow-lg backdrop-blur-md hover:scale-105 transition transform duration-300 border border-pink-500/30 hover:border-pink-400/80 hover:shadow-[0_0_20px_#ff2ad1]">
                <img
                  src="/assets/img/myrelle.jpg"
                  alt="Equipe Wave"
                  className="w-[140px] h-[140px] rounded-full object-cover shadow-xl
                             border-4 border-pink-400 hover:border-purple-500 
                             transition duration-300 hover:shadow-[0_0_15px_#ff29d1]"
                />
                <p className="mt-3 font-semibold text-center">Myrelle Bazílio</p>
              </div>

              {/* CARD 3 */}
              <div className="bg-white/10 p-5 rounded-xl shadow-lg backdrop-blur-md hover:scale-105 transition transform duration-300 border border-pink-500/30 hover:border-pink-400/80 hover:shadow-[0_0_20px_#ff2ad1]">
                <img
                  src="/assets/img/Priscila.jpg"
                  alt="Desenvolvedores"
                  className="w-[140px] h-[140px] rounded-full object-cover shadow-xl
                             border-4 border-pink-400 hover:border-purple-500 
                             transition duration-300 hover:shadow-[0_0_15px_#ff29d1]"
                />
                <p className="mt-3 font-semibold text-center">Priscila Oliveira</p>
              </div>
            </div>

            {/* LINHA 2 - 2 CARDS */}
            <div className="flex justify-center gap-10 flex-wrap">
              {/* CARD 4 */}
              <div className="bg-white/10 p-5 rounded-xl shadow-lg backdrop-blur-md hover:scale-105 transition transform duration-300 border border-pink-500/30 hover:border-pink-400/80 hover:shadow-[0_0_20px_#ff2ad1]">
                <img
                  src="/assets/img/giuliacosta.jpg"
                  alt="Equipe"
                  className="w-[140px] h-[140px] rounded-full object-cover shadow-xl
                             border-4 border-pink-400 hover:border-purple-500 
                             transition duration-300 hover:shadow-[0_0_15px_#ff29d1]"
                />
                <p className="mt-3 font-semibold text-center">Giulia Costa</p>
              </div>

              {/* CARD 5 */}
              <div className="bg-white/10 p-5 rounded-xl shadow-lg backdrop-blur-md hover:scale-105 transition transform duration-300 border border-pink-500/30 hover:border-pink-400/80 hover:shadow-[0_0_20px_#ff2ad1]">
                <img
                  src="/assets/img/brunopassos.jpg"
                  alt="Time"
                  className="w-[140px] h-[140px] rounded-full object-cover shadow-xl
                             border-4 border-pink-400 hover:border-purple-500 
                             transition duration-300 hover:shadow-[0_0_15px_#ff29d1]"
                />
                <p className="mt-3 font-semibold text-center">Bruno Passos</p>
              </div>
            </div>

          </div>

        </div>
      </main>

      <Footer />
    </div>
  );
}
