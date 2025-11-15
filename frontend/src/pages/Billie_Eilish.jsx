import ArtistTemplate from "./_ArtistTemplate";

export default function Billie_Eilish() {
  return (
    <ArtistTemplate
      name="Billie Eilish"
      description={`Billie Eilish é uma cantora e compositora norte-americana conhecida por seu estilo único e atmosférico. Desde o lançamento de "Ocean Eyes", ela tem dominado as paradas com sua estética sombria, vocais suaves e letras introspectivas. Com apenas 17 anos, ganhou 5 Grammys, consolidando-se como um fenômeno da música pop alternativa.`}
      images={[
        "/assets/img/billie1.jpg",
        "/assets/img/billie2.jpg",
        "/assets/img/billie3.jpg"
      ]}
      albums={[
        { img: "/assets/img/When01.png", name: "When We All Fall Asleep, Where Do We Go?" },
        { img: "/assets/img/Happier01.jpg", name: "Happier Than Ever" },
        { img: "/assets/img/hitmehard.jpg", name: "Hit Me Hard and Soft" },
        { img: "/assets/img/dontsmile.jpg", name: "dont smile at me" },
        { img: "/assets/img/guitarsongs.jpg", name: "Guitar Songs" },
        { img: "/assets/img/liveat.jpg", name: "Live at Third Man Records" },
        { img: "/assets/img/applemusic.jpg", name: "Apple Music Live: Billie Eilish" }
      ]}
      musics={[
        { name: "Birds of a Feather", src: "/assets/mp3/Birds_of_a_Feather.mp3" },
        { name: "Bad Guy", src: "/assets/mp3/Bad_Guy.mp3" },
        { name: "Happier Than Ever", src: "/assets/mp3/Happier_Than_Ever.mp3" }
      ]}
    />
  );
}
