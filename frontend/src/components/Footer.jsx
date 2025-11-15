export default function Footer() {
  return (
    <footer className="bg-[#2c0140] text-white flex flex-col items-center p-4 mt-8">
      <div className="max-w-[1200px] w-full flex justify-between flex-wrap gap-8">

        <div>
          <h3 className="font-semibold text-base mb-1">Empresa</h3>
          <ul className="space-y-0.5 text-sm">
            <li><a href="/Sobre" className="hover:text-pink-400">Sobre</a></li>
            <li><a href="/Empregos" className="hover:text-pink-400">Empregos</a></li>
          </ul>
        </div>

        <div>
          <h4 className="font-semibold text-base mb-1">Planos Wave</h4>
          <ul className="space-y-0.5 text-sm">
            <li><a href="/Premium" className="hover:text-pink-400">Wave Premium</a></li>
            <li><a href="/Familia" className="hover:text-pink-400">Wave Família</a></li>
            <li><a href="/Universitario" className="hover:text-pink-400">Wave Universitário</a></li>
            <li><a href="/Free" className="hover:text-pink-400">Wave Free</a></li>
          </ul>
        </div>

        <div>
          <h3 className="font-semibold text-base mb-1">Links úteis</h3>
          <ul className="space-y-0.5 text-sm">
            <li><a href="/Suporte" className="hover:text-pink-400">Suporte</a></li>
            <li><a href="/Movel" className="hover:text-pink-400">Aplicativo Móvel</a></li>
          </ul>
        </div>

        <div className="flex gap-4 items-center">
          <a href="https://instagram.com/wavemusic.stream">
            <img src="/assets/img/instagram_logo.png" alt="instagram" className="w-6" />
          </a>
          <a href="https://x.com/wavemusicstream">
            <img src="/assets/img/twitter_logo.png" alt="twitter" className="w-6" />
          </a>
        </div>
      </div>

      <div className="text-xs mt-3 border-t border-white/20 pt-2 w-full text-center">
        © 2025 Wave. Todos os direitos reservados.
      </div>
    </footer>
  );
}