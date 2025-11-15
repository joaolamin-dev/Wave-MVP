import ArtistTemplate from "./_ArtistTemplate";

export default function Iza() {
  return (
    <ArtistTemplate
      name="Iza"
      description={`IZA é uma cantora e compositora brasileira que se destacou com sua voz poderosa e presença marcante. Misturando pop, R&B e influências do soul, ela transmite força, representatividade e atitude em músicas como "Dona de Mim" e "Brisa".`}
      images={[
        "/assets/img/Iza01.jpg",
        "/assets/img/Iza02.jpg",
        "/assets/img/Iza03.jpg"
      ]}
      albums={[
        { img: "/assets/img/Afrodhit.jpg", name: "Afrodhit" },
        { img: "/assets/img/Dona.jpg", name: "Dona de Mim" },
        { img: "/assets/img/meutalisma.jpg", name: "Meu Talismã" },
        { img: "/assets/img/mopaz.jpg", name: "Mó Paz" },
        { img: "/assets/img/ginga.jpg", name: "Ginga" },
        { img: "/assets/img/pesadao.jpg", name: "Pesadão" },
        { img: "/assets/img/briza.jpg", name: "Briza" }
      ]}
      musics={[
        { name: "Dona de Mim", src: "/assets/mp3/Dona_de_Mim.mp3" },
        { name: "Meu Talismã", src: "/assets/mp3/Meu_Talisma.mp3" },
        { name: "Pesadão", src: "/assets/mp3/Pesadao.mp3" }
      ]}
    />
  );
}
