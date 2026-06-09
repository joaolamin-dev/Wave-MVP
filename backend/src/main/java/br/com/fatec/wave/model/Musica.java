package br.com.fatec.wave.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artistaNome;

    @Enumerated(EnumType.STRING)
    private GeneroMusical genero;

    private Integer anoLancamento;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtistaNome() { return artistaNome; }
    public void setArtistaNome(String artistaNome) { this.artistaNome = artistaNome; }

    public GeneroMusical getGenero() { return genero; }
    public void setGenero(GeneroMusical genero) { this.genero = genero; }

    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }
}