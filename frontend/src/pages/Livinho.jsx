import ArtistTemplate from "./_ArtistTemplate";

export default function Livinho() {
  return (
    <ArtistTemplate
      name="MC Livinho"
      description={`MC Livinho é um dos maiores nomes do funk paulista, conhecido por sua versatilidade e presença de palco.`}
      images={[
        "/assets/img/Livinho01.jpg",
        "/assets/img/livinho02.jpg",
        "/assets/img/Livinho03.jpg"
      ]}
      albums={[
        { img: "/assets/img/Fruto.jpg", name: "Fruto Proibido" },
        { img: "/assets/img/Vagabundo.jpg", name: "Vagabundo Romântico" },
        { img: "/assets/img/magico.jpg", name: "Mágico dos Flows" },
        { img: "/assets/img/vemdepois.jpg", name: "O Que Vem Depois" },
        { img: "/assets/img/restrito.jpg", name: "Restrito" },
        { img: "/assets/img/amorioesp.jpg", name: "Eu Amo Rio, Eu Amo SP" },
        { img: "/assets/img/playsomsexo.jpg", name: "Play, Som e Sexo" }
      ]}
      musics={[
        { name: "Tenebrosa", src: "/assets/mp3/Tenebrosa.mp3" },
        { name: "Sem Chantagem", src: "/assets/mp3/Sem_Chantagem.mp3" },
        { name: "Cheia de Marra", src: "/assets/mp3/Cheia_de_marra.mp3" }
      ]}
    />
  );
}
