package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de resposta contendo os dados públicos do usuário")
public record UsuarioResponseDTO(

        @Schema(description = "ID único do usuário no banco de dados", example = "1")
        Long id,

        @Schema(description = "Nome completo do usuário", example = "João Lamin")
        String nome,

        @Schema(description = "Endereço de e-mail do usuário", example = "joao@wave.com")
        String email
) {
}