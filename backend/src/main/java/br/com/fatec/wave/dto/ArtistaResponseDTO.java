package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.TipoArtista;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com os dados do Artista")
public record ArtistaResponseDTO(Long id, String nome, String nacionalidade, TipoArtista tipo) {}