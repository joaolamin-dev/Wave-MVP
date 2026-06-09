package br.com.fatec.wave.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String nacionalidade;

    @Enumerated(EnumType.STRING)
    private TipoArtista tipo;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public TipoArtista getTipo() { return tipo; }
    public void setTipo(TipoArtista tipo) { this.tipo = tipo; }
}