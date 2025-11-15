import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState } from "react";

export default function Suporte() {
  const [enviado, setEnviado] = useState(false);

  function handleSubmit(e) {
    e.preventDefault();
    setEnviado(true);
    setTimeout(() => setEnviado(false), 3000);
  }

  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc]">
      <Header />

      <main className="flex-1 flex justify-center items-center px-4 py-8 mt-16 mb-8">
        <div className="bg-black/25 p-10 rounded-xl shadow-xl backdrop-blur-sm max-w-[500px] w-full">

          <h1 className="text-center text-3xl mb-6 font-bold">
            Suporte
          </h1>

          <form onSubmit={handleSubmit}>
            <label>Nome:</label>
            <input
              type="text"
              className="w-full px-4 py-2 rounded-md text-black mb-4"
              required
            />

            <label>Email:</label>
            <input
              type="email"
              className="w-full px-4 py-2 rounded-md text-black mb-4"
              required
            />

            <label>Descrição do problema:</label>
            <textarea
              className="w-full px-4 py-2 rounded-md text-black mb-4 h-28"
              required
            />

            <button className="w-full bg-[#44034d] py-3 rounded-full hover:bg-[#b40aca] transition font-bold">
              Enviar
            </button>
          </form>

          {enviado && (
            <div className="bg-[#650372] p-4 text-center mt-4 rounded-md animate-fadeIn">
              Mensagem enviada com sucesso!
            </div>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
}