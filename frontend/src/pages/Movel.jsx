import Header from "../components/Header";
import Footer from "../components/Footer";

export default function Movel() {
  return (
    <div className="min-h-screen flex flex-col text-white bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc]">

      <Header />

      <main className="flex-1 flex items-center justify-center px-4">
        <div className="bg-[#38003d] p-12 rounded-xl text-center max-w-[500px] w-full shadow-xl">
          <h1 className="text-4xl font-bold mb-4">Aplicativo Móvel</h1>
          <p className="text-2xl font-bold">
            📱 Em breve disponível!
          </p>
        </div>
      </main>

      <Footer />
    </div>
  );
}
