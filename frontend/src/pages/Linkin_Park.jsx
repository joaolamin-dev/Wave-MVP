import ArtistTemplate from "./_ArtistTemplate";

export default function Linkin_Park() {
  return (
    <ArtistTemplate
      name="Linkin Park"
      description={`Linkin Park foi uma das bandas mais influentes do rock alternativo e nu metal, misturando guitarras pesadas, rap e eletrônica. Com hits como “In the End” e “Numb”, marcou gerações com letras intensas e som poderoso.`}
      images={[
        "/assets/img/linkin01.jpg",
        "/assets/img/linkin02.jpg",
        "/assets/img/linkin03.jpg"
      ]}
      albums={[
        { img: "/assets/img/HybridTheory.jpg", name: "Hybrid Theory" },
        { img: "/assets/img/FromZero.jpg", name: "From Zero" },
        { img: "/assets/img/meteora.jpg", name: "Meteora" },
        { img: "/assets/img/onemorelight.jpg", name: "One More Light" },
        { img: "/assets/img/minutesto.jpg", name: "Minutes to Midnight" },
        { img: "/assets/img/liveintexas.jpg", name: "Live in Texas" },
        { img: "/assets/img/livingthings.jpg", name: "Living Things" }
      ]}
      musics={[
        { name: "The Emptiness Machine", src: "/assets/mp3/The_Emptiness_Machine.mp3" },
        { name: "Heavy Is The Crown", src: "/assets/mp3/Heavy_Is_the_Crown.mp3" },
        { name: "Faint", src: "/assets/mp3/Faint.mp3" }
      ]}
    />
  );
}
