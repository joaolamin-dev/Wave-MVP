package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.TipoArtista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para atualização de um Artista")
public record AtualizarArtistaDTO(
        @NotBlank(message = "é obrigatório") String nome,
        String nacionalidade,
        TipoArtista tipo) {}