import ArtistTemplate from "./_ArtistTemplate";

export default function System() {
  return (
    <ArtistTemplate
      name="System Of A Down"
      description={`System of a Down é uma banda de metal alternativo conhecida por seu som único que mistura metal, punk e influências do Oriente Médio.`}
      images={[
        "/assets/img/soad01.jpg",
        "/assets/img/soad02.jpg",
        "/assets/img/soad03.jpg"
      ]}
      albums={[
        { img: "/assets/img/Toxicity.jpg", name: "Toxicity" },
        { img: "/assets/img/Hypnotyze.jpg", name: "Hypnotize" },
        { img: "/assets/img/systemalbum.jpg", name: "System of a Down" },
        { img: "/assets/img/mezmerize.jpg", name: "Mezmerize" },
        { img: "/assets/img/steal.jpg", name: "Steal This Album!" }
      ]}
      musics={[
        { name: "Chop Suey", src: "/assets/mp3/Chop_Suey.mp3" },
        { name: "Toxicity", src: "/assets/mp3/Toxicity.mp3" },
        { name: "B.Y.O.B.", src: "/assets/mp3/B_Y_O_B.mp3" }
      ]}
    />
  );
}
