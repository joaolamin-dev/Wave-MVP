import Header from "../components/Header";
import Footer from "../components/Footer";

export default function RecuperarSenha() {
  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc] text-white animate-fadeIn">
      <Header />

      <main className="flex-1 flex justify-center items-center">
        <div className="max-w-[400px] w-full p-10 text-center">
          <img
            src="/assets/img/logo.png"
            className="w-[250px] h-[250px] mx-auto rounded-full mb-6 shadow-xl"
            alt="logo"
          />

          <h2 className="text-2xl mb-4">Recuperar Senha</h2>

          <p className="mb-6 text-blue-100 text-sm">
            Digite seu email para receber um link de redefinição.
          </p>

          <form>
            <input
              type="email"
              placeholder="Seu email"
              className="w-full text-black px-4 py-3 rounded-full outline-none mb-4"
            />

            <button className="w-full bg-[#2d0e4c] py-3 rounded-full hover:bg-[#4a1d7c] transition font-bold">
              Enviar Link
            </button>
          </form>
        </div>
      </main>

      <Footer />
    </div>
  );
}
