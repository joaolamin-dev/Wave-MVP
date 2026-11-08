package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Objeto de resposta padrão para erros da API")
public record ErroResponseDTO(

        @Schema(description = "Momento em que o erro ocorreu", example = "2026-11-02T18:30:00Z")
        Instant timestamp,

        @Schema(description = "Código de status HTTP", example = "404")
        int status,

        @Schema(description = "Descrição do status HTTP", example = "Not Found")
        String error,

        @Schema(description = "Mensagem explicando o erro", example = "Usuario não encontrado com o id: 9999")
        String message,

        @Schema(description = "Caminho da requisição", example = "/usuarios/9999")
        String path
) {
}
