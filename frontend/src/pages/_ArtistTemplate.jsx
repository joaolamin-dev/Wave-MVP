
import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState, useRef, useEffect } from "react";

// Componentes de ícones SVG customizados
const PlayIcon = ({ className = "w-6 h-6" }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    <path d="M8 5v14l11-7z"/>
  </svg>
);

const PauseIcon = ({ className = "w-6 h-6" }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    <path d="M6 4h4v16H6zm8 0h4v16h-4z"/>
  </svg>
);

const NextIcon = ({ className = "w-5 h-5" }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    <path d="M6 18l8.5-6L6 6v12zM16 6v12h2V6h-2z"/>
  </svg>
);

const PrevIcon = ({ className = "w-5 h-5" }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    <path d="M6 6h2v12H6zm3.5 6l8.5 6V6z"/>
  </svg>
);

const VolumeIcon = ({ level = 1, className = "w-5 h-5" }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    {level === 0 ? (
      <path d="M3.63 3.63a.996.996 0 000 1.41L7.29 9H6c-.55 0-1 .45-1 1v4c0 .55.45 1 1 1h1.29l4.71 4.71c.17.18.42.29.7.29.55 0 1-.45 1-1v-8.59c0-.27-.11-.52-.29-.71L3.63 3.63zM16.5 12c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02z"/>
    ) : level < 0.5 ? (
      <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02z"/>
    ) : (
      <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/>
    )}
  </svg>
);

const MusicWaveIcon = ({ className = "w-4 h-4", isPlaying = false }) => (
  <svg className={className} viewBox="0 0 24 24" fill="currentColor">
    {isPlaying ? (
      <>
        <path d="M6 7h2v10H6zm5 0h2v10h-2zm5 0h2v10h-2z"/>
      </>
    ) : (
      <>
        <path d="M6 14h2v-4H6zm5 2h2V8h-2zm5-3h2v-2h-2z"/>
      </>
    )}
  </svg>
);

export default function ArtistTemplate({ images = [], name, description, albums = [], musics = [] }) {
  const audioRef = useRef(null);
  const [currentIndex, setCurrentIndex] = useState(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [progress, setProgress] = useState(0);
  const [volume, setVolume] = useState(1);
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);

  // Backgrounds disponíveis para o player
  const playerBackgrounds = [
    "/assets/img/player-bg1.jpg",
    "/assets/img/player-bg2.gif", 
    "/assets/img/player-bg3.jpg",
    "/assets/img/neon-wave.gif"
  ];
  
  const [currentBg, setCurrentBg] = useState(0);

  function playMusic(index) {
    if (!musics || musics.length === 0) return;
    const idx = index % musics.length;
    setCurrentIndex(idx);
    
    if (audioRef.current) {
      audioRef.current.src = musics[idx].src;
      audioRef.current.play().then(() => {
        setIsPlaying(true);
      }).catch(error => {
        console.error("Error playing audio:", error);
      });
    }
  }

  function togglePlayPause() {
    if (!audioRef.current) return;
    if (isPlaying) {
      audioRef.current.pause();
      setIsPlaying(false);
    } else {
      audioRef.current.play().then(() => {
        setIsPlaying(true);
      });
    }
  }

  function handleProgressChange(e) {
    if (!audioRef.current) return;
    const newTime = (e.target.value / 100) * duration;
    audioRef.current.currentTime = newTime;
    setProgress(e.target.value);
    setCurrentTime(newTime);
  }

  function handleVolumeChange(e) {
    const newVolume = parseFloat(e.target.value);
    setVolume(newVolume);
    if (audioRef.current) {
      audioRef.current.volume = newVolume;
    }
  }

  function formatTime(seconds) {
    if (!seconds || isNaN(seconds)) return "0:00";
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  }

  function nextBackground() {
    setCurrentBg((prev) => (prev + 1) % playerBackgrounds.length);
  }

  useEffect(() => {
    if (!audioRef.current) return;

    const updateProgress = () => {
      if (audioRef.current) {
        const current = audioRef.current.currentTime;
        const total = audioRef.current.duration || duration;
        setCurrentTime(current);
        setDuration(total);
        
        if (total > 0) {
          setProgress((current / total) * 100);
        }
      }
    };

    const handleEnded = () => {
      setIsPlaying(false);
      if (currentIndex !== null && musics.length > 0) {
        playMusic((currentIndex + 1) % musics.length);
      }
    };

    const handleLoadedMetadata = () => {
      if (audioRef.current) {
        setDuration(audioRef.current.duration);
      }
    };

    audioRef.current.addEventListener('timeupdate', updateProgress);
    audioRef.current.addEventListener('ended', handleEnded);
    audioRef.current.addEventListener('loadedmetadata', handleLoadedMetadata);

    return () => {
      if (audioRef.current) {
        audioRef.current.removeEventListener('timeupdate', updateProgress);
        audioRef.current.removeEventListener('ended', handleEnded);
        audioRef.current.removeEventListener('loadedmetadata', handleLoadedMetadata);
      }
    };
  }, [currentIndex, duration, musics.length]);

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-r from-[#2c003e] via-[#7a1e78] to-[#ff00cc] text-white">
      <Header />

      <main className="p-10 flex-1 pt-24">
        <section className="flex flex-wrap gap-10 items-center">
          <div className="flex gap-6">
            {images.map((src, i) => (
              <img key={i} src={src} alt={`${name}-${i}`} className="w-[280px] h-[280px] rounded-xl shadow-2xl object-cover hover:animate-pulseImg" />
            ))}
          </div>

          <div className="max-w-[600px]">
            <h1 className="text-5xl font-bold mb-4 animate-neonPulse">{name}</h1>
            <p className="text-lg leading-7 text-justify">{description}</p>
          </div>
        </section>

        <section className="mt-16">
          <h2 className="text-3xl mb-6">Discografia</h2>

          <div className="overflow-hidden">
            <div className="flex animate-scroll-albums">
              {albums.concat(albums).map((a, i) => (
                <div key={i} className="min-w-[200px] mr-6 text-center">
                  <img src={a.img} alt={a.name} className="w-[200px] h-[200px] rounded-xl object-cover shadow-lg hover:scale-105 transition-transform duration-300" />
                  <span className="block mt-2">{a.name}</span>
                </div>
              ))}
            </div>
          </div>
        </section>

        <section className="mt-16">
          <h2 className="text-3xl mb-6">Mais tocadas</h2>

          {/* Lista de músicas estilizada - DURAÇÃO REMOVIDA */}
          <div className="grid grid-cols-1 gap-3 mb-8">
            {musics.map((m, i) => (
              <div
                key={i}
                onClick={() => playMusic(i)}
                className={`
                  relative p-4 rounded-xl cursor-pointer transition-all duration-300 group
                  ${currentIndex === i 
                    ? 'bg-gradient-to-r from-[#7c3aed] to-[#a855f7] shadow-lg shadow-purple-500/30 transform scale-105' 
                    : 'bg-white/10 hover:bg-white/20 hover:transform hover:scale-102'
                  }
                `}
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-4">
                    <div className={`
                      w-8 h-8 rounded-full flex items-center justify-center
                      ${currentIndex === i 
                        ? 'bg-white text-purple-600' 
                        : 'bg-white/20 group-hover:bg-white/30'
                      }
                    `}>
                      {currentIndex === i ? (
                        <MusicWaveIcon isPlaying={isPlaying} />
                      ) : (
                        <span className="text-xs">#{i + 1}</span>
                      )}
                    </div>
                    <span className={`font-medium ${currentIndex === i ? 'text-white' : 'text-white/90'}`}>
                      {m.name}
                    </span>
                  </div>
                  {/* DURAÇÃO REMOVIDA - SÓ FICA O NOME DA MÚSICA */}
                </div>
                
                {/* Barra de progresso sutil para a música atual */}
                {currentIndex === i && (
                  <div className="absolute bottom-0 left-0 h-1 bg-white/30 rounded-b-xl w-full">
                    <div 
                      className="h-full bg-white rounded-b-xl transition-all duration-100"
                      style={{ width: `${progress}%` }}
                    ></div>
                  </div>
                )}
              </div>
            ))}
          </div>

          {/* Player personalizado futurista com background */}
          <div className="relative rounded-2xl p-8 shadow-2xl border border-white/10 overflow-hidden">
            {/* Background do player - imagem ou GIF */}
            <div 
              className="absolute inset-0 bg-cover bg-center"
              style={{ 
                backgroundImage: `url(${playerBackgrounds[currentBg]})`,
                filter: 'brightness(0.4) contrast(1.1)'
              }}
            ></div>
            
            {/* Overlay gradiente para melhor legibilidade */}
            <div className="absolute inset-0 bg-gradient-to-br from-purple-900/60 to-pink-900/40"></div>
            
            {/* Efeitos visuais */}
            <div className="absolute inset-0">
              <div className="absolute -inset-10 bg-gradient-to-r from-[#7c3aed]/20 to-[#a855f7]/20 blur-3xl"></div>
              <div className="absolute top-0 right-0 w-32 h-32 bg-[#00f0ff]/10 rounded-full blur-2xl"></div>
              <div className="absolute bottom-0 left-0 w-40 h-40 bg-[#ff00cc]/10 rounded-full blur-2xl"></div>
            </div>

            <div className="relative z-10">
              {/* Botão para trocar background */}
              <button 
                onClick={nextBackground}
                className="absolute top-4 right-4 p-2 rounded-full bg-white/10 hover:bg-white/20 transition-all duration-300 group"
                title="Trocar visual"
              >
                <svg className="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 6v3l4-4-4-4v3c-4.42 0-8 3.58-8 8 0 1.57.46 3.03 1.24 4.26L6.7 14.8c-.45-.83-.7-1.79-.7-2.8 0-3.31 2.69-6 6-6zm6.76 1.74L17.3 9.2c.44.84.7 1.79.7 2.8 0 3.31-2.69 6-6 6v-3l-4 4 4 4v-3c4.42 0 8-3.58 8-8 0-1.57-.46-3.03-1.24-4.26z"/>
                </svg>
              </button>

              {/* Informação da música atual */}
              <div className="text-center mb-8">
                <div className="text-white/60 text-sm mb-2">TOCANDO AGORA</div>
                <div className="text-2xl font-bold bg-gradient-to-r from-white to-white/80 bg-clip-text text-transparent">
                  {currentIndex !== null ? musics[currentIndex].name : "Selecione uma música"}
                </div>
                <div className="text-white/40 text-sm mt-1">{name}</div>
              </div>

              {/* Barra de progresso - CORRIGIDA E VISÍVEL */}
              <div className="mb-8">
                <div className="flex justify-between text-white/80 text-sm mb-3 font-medium">
                  <span>{formatTime(currentTime)}</span>
                  <span>{formatTime(duration)}</span>
                </div>
                <div className="range-container">
                  <div className="range-track"></div>
                  <div 
                    className="range-progress" 
                    style={{ width: `${progress}%` }}
                  ></div>
                  <input
                    type="range"
                    min="0"
                    max="100"
                    value={progress}
                    onChange={handleProgressChange}
                    className="range-input"
                  />
                </div>
              </div>

              {/* Controles */}
              <div className="flex items-center justify-center gap-6 mb-8">
                <button 
                  onClick={() => playMusic((currentIndex - 1 + musics.length) % musics.length)}
                  className="p-3 rounded-full bg-white/10 hover:bg-white/20 transition-all duration-300 hover:scale-110 group"
                  disabled={currentIndex === null}
                >
                  <PrevIcon className="w-5 h-5 group-hover:text-purple-300" />
                </button>

                <button 
                  onClick={togglePlayPause}
                  disabled={currentIndex === null}
                  className="p-5 rounded-full bg-gradient-to-r from-[#7c3aed] to-[#a855f7] hover:from-[#a855f7] hover:to-[#c084fc] transition-all duration-300 hover:scale-110 shadow-lg group"
                >
                  {isPlaying ? (
                    <PauseIcon className="w-7 h-7 group-hover:text-white" />
                  ) : (
                    <PlayIcon className="w-7 h-7 group-hover:text-white" />
                  )}
                </button>

                <button 
                  onClick={() => playMusic((currentIndex + 1) % musics.length)}
                  className="p-3 rounded-full bg-white/10 hover:bg-white/20 transition-all duration-300 hover:scale-110 group"
                  disabled={currentIndex === null}
                >
                  <NextIcon className="w-5 h-5 group-hover:text-purple-300" />
                </button>
              </div>

              {/* Volume - CORRIGIDO E VISÍVEL */}
              <div className="flex items-center justify-center gap-3">
                <VolumeIcon level={volume} className="w-5 h-5 text-white/70" />
                <div className="volume-container">
                  <div className="volume-track"></div>
                  <div 
                    className="volume-progress" 
                    style={{ width: `${volume * 100}%` }}
                  ></div>
                  <input
                    type="range"
                    min="0"
                    max="1"
                    step="0.1"
                    value={volume}
                    onChange={handleVolumeChange}
                    className="volume-input"
                  />
                </div>
                <div className="text-white/70 text-xs font-medium min-w-8 text-right">
                  {Math.round(volume * 100)}%
                </div>
              </div>
            </div>
          </div>

          {/* Audio element escondido */}
          <audio ref={audioRef} className="hidden" />
        </section>
      </main>

      <Footer />
    </div>
  );
}