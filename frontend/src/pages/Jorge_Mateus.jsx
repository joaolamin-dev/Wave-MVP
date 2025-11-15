import ArtistTemplate from "./_ArtistTemplate";

export default function Jorge_Mateus() {
  return (
    <ArtistTemplate
      name="Jorge & Mateus"
      description={`Jorge & Mateus são uma das duplas sertanejas mais populares do Brasil. Com sucessos românticos e animados, conquistaram o público com vozes marcantes e letras que falam de amor e cotidiano.`}
      images={[
        "/assets/img/Jorge01.jpg",
        "/assets/img/Jorge02.jpeg",
        "/assets/img/Jorge03.webp"
      ]}
      albums={[
        { img: "/assets/img/AHora.jpeg", name: "A Hora é Agora" },
        { img: "/assets/img/10Anos.jpg", name: "10 Anos" },
        { img: "/assets/img/goiania.jpg", name: "Ao vivo em Goiânia" },
        { img: "/assets/img/comosempre.jpg", name: "Como. Sempre Feito. Nunca" },
        { img: "/assets/img/anjos.jpg", name: "Os anjos cantam" },
        { img: "/assets/img/tudopaz.jpg", name: "Tudo em Paz" },
        { img: "/assets/img/simplesassim.jpg", name: "É Simples Assim" }
      ]}
      musics={[
        { name: "Pra Sempre com Você", src: "/assets/mp3/Pra_Sempre_Com_Voce.mp3" },
        { name: "Amo Noite E Dia", src: "/assets/mp3/Amo_Noite_E_Dia.mp3" },
        { name: "Cantada Boba", src: "/assets/mp3/Cantada_Boba.mp3" }
      ]}
    />
  );
}
