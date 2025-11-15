import Header from "../components/Header";
import Footer from "../components/Footer";
import { Link } from "react-router-dom";

export default function Homepage() {
  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc] text-white overflow-hidden">

      <Header />

      {/* Espaço para não ser tampado pelo header fixo */}
      <main className="flex justify-between p-10 flex-1 pt-[120px]">

        <div className="max-w-[50%]">
          <h1
            className="text-3xl mb-8 animate-neonPulse font-semibold"
            style={{ textShadow: "0 0 15px #ff66cc" }}
          >
            Descubra seus artistas favoritos
          </h1>

          <div className="flex flex-wrap gap-6">

            <Link to="/Billie_Eilish" className="text-center hover:text-[#ff66cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/Billie.png"
                    alt="Billie Eilish"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">Billie Eilish</div>
            </Link>

            <Link to="/Linkin_park" className="text-center hover:text-[#ff66cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/linkin.png"
                    alt="Linkin Park"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">Linkin Park</div>
            </Link>

            <Link to="/Jorge_Mateus" className="text-center hover:text-[#ff66cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/jorge_e_mateus.png"
                    alt="Jorge & Mateus"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">Jorge & Mateus</div>
            </Link>

            <Link to="/Livinho" className="text-center hover:text-[#ff66cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/mc_livinho.png"
                    alt="Livinho"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">Livinho</div>
            </Link>

            <Link to="/System" className="text-center hover:text-[#ff66cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/Soad.png"
                    alt="System of a Down"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">System of a Down</div>
            </Link>

            <Link to="/Iza" className="text-center hover:text-[#ff00cc] transition">
              <div className="relative w-[230px] h-[230px] mx-auto">
                <div className="absolute -inset-2 rounded-full blur-[22px] opacity-80 pointer-events-none abstract-ring-1"></div>
                <div className="absolute -inset-4 rounded-full blur-[44px] opacity-50 pointer-events-none abstract-ring-2"></div>

                <div className="relative w-full h-full rounded-full overflow-hidden ring-0">
                  <img
                    src="/assets/img/Iza.png"
                    alt="Iza"
                    className="w-full h-full object-cover rounded-full transform transition-transform duration-500 hover:scale-105"
                  />
                </div>

                <div className="absolute inset-0 rounded-full pointer-events-none overlay-frost"></div>
              </div>
              <div className="mt-2">Iza</div>
            </Link>

          </div>
        </div>

        <div className="relative w-[700px] h-auto flex items-center justify-center">
          {/* Novo fundo elegante */}
          <div className="absolute -inset-8 rounded-3xl pointer-events-none stage-bg-elegant"></div>
          
          {/* Ondas sonoras */}
          <div className="absolute inset-0 rounded-3xl pointer-events-none">
            <div className="sound-wave"></div>
            <div className="sound-wave sound-wave-2"></div>
            <div className="sound-wave sound-wave-3"></div>
          </div>
          
          {/* Partículas flutuantes */}
          <div className="absolute inset-0 pointer-events-none">
            <span className="floating-particle fp1"></span>
            <span className="floating-particle fp2"></span>
            <span className="floating-particle fp3"></span>
            <span className="floating-particle fp4"></span>
            <span className="floating-particle fp5"></span>
            <span className="floating-particle fp6"></span>
          </div>

          {/* Container da imagem com borda abstrata */}
          <div className="relative">
            {/* Borda abstrata rotativa */}
            <div className="abstract-border"></div>
            
            {/* Imagem principal com brilho pulsante E glitch no hover */}
            <div className="relative z-10 rounded-3xl overflow-hidden transform transition-transform duration-700 hover:scale-[1.02] glow-pulse glitch-effect">
              <img
                src="/assets/img/dancingwoman.jpg"
                alt="Pessoa ouvindo música"
                className="w-[700px] rounded-3xl object-cover"
              />
            </div>
          </div>

          {/* Overlay de reflexo */}
          <div className="absolute z-20 inset-0 rounded-3xl pointer-events-none glass-reflection"></div>
        </div>

      </main>

      <Footer />
    </div>
  );
}