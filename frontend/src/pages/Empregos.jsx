import Header from "../components/Header";
import Footer from "../components/Footer";

export default function Empregos() {
  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc]">
      <Header />

      <main className="flex-1 flex justify-center items-center px-4 py-8 mt-16 mb-8">
        <div className="bg-black/25 p-10 rounded-xl shadow-xl backdrop-blur-sm max-w-[700px] w-full">

          <h1 className="text-center text-4xl font-bold mb-6">Trabalhe Conosco</h1>

          <p className="text-lg mb-8 text-justify leading-8">
            O Wave está crescendo e buscando novos talentos apaixonados por inovação,
            música e tecnologia.
            Se você deseja fazer parte de um time dinâmico, criativo e que respira
            criatividade, envie seu currículo e venha transformar o mundo da música
            conosco!
          </p>

          <div className="border border-white/30 rounded-lg p-6">
            <h2 className="text-xl mb-4 font-semibold">Vagas abertas:</h2>

            <ul className="space-y-3 text-lg">
              <li>🎧 Designer UI/UX</li>
              <li>🎵 Curador Musical</li>
              <li>🖥️ Desenvolvedor Front-End</li>
              <li>🖥️ Desenvolvedor Back-End</li>
            </ul>

            <a
              href="mailto:contato@wave.com"
              className="block mt-6 text-center bg-[#ff00cc] px-6 py-3 rounded-full font-bold hover:bg-pink-700 transition"
            >
              Enviar Currículo
            </a>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  );
}