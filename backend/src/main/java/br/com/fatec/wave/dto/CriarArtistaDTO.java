package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.TipoArtista;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de um Artista")
public record CriarArtistaDTO(String nome, String nacionalidade, TipoArtista tipo) {}