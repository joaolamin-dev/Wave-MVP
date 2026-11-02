package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.TipoArtista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de um Artista")
public record CriarArtistaDTO(
        @NotBlank(message = "é obrigatório") String nome,
        String nacionalidade,
        @NotNull(message = "é obrigatório") TipoArtista tipo) {}