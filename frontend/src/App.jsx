import { Routes, Route } from "react-router-dom";

import Homepage from "./pages/Homepage";
import Billie_Eilish from "./pages/Billie_Eilish";
import Iza from "./pages/Iza";
import Linkin_Park from "./pages/Linkin_Park";
import Jorge_Mateus from "./pages/Jorge_Mateus";
import Livinho from "./pages/Livinho";
import System from "./pages/System";

import Sobre from "./pages/Sobre";
import Empregos from "./pages/Empregos";
import Suporte from "./pages/Suporte";
import Movel from "./pages/Movel";
import Premium from "./pages/Premium";
import Familia from "./pages/Familia";
import Universitario from "./pages/Universitario";
import Free from "./pages/Free";
import Cadastro from "./pages/Cadastro";
import Login from "./pages/Login";

export default function App() {
  return (
    <div className="min-h-screen flex flex-col">
      <Routes>
        <Route path="/" element={<Homepage />} />

        {/* ARTISTAS */}
        <Route path="/Billie_Eilish" element={<Billie_Eilish />} />
        <Route path="/Iza" element={<Iza />} />
        <Route path="/Linkin_Park" element={<Linkin_Park />} />
        <Route path="/Jorge_Mateus" element={<Jorge_Mateus />} />
        <Route path="/Livinho" element={<Livinho />} />
        <Route path="/System" element={<System />} />

        {/* OUTRAS PÁGINAS */}
        <Route path="/Sobre" element={<Sobre />} />
        <Route path="/Empregos" element={<Empregos />} />
        <Route path="/Suporte" element={<Suporte />} />
        <Route path="/Movel" element={<Movel />} />
        <Route path="/Premium" element={<Premium />} />
        <Route path="/Familia" element={<Familia />} />
        <Route path="/Universitario" element={<Universitario />} />
        <Route path="/Free" element={<Free />} />
        <Route path="/Cadastro" element={<Cadastro />} />
        <Route path="/Login" element={<Login />} />
      </Routes>
    </div>
  );
}
