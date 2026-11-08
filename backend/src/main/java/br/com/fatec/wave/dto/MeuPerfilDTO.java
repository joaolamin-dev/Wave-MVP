package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do usuário logado")
public record MeuPerfilDTO(

        @Schema(description = "ID único do usuário no banco de dados", example = "1")
        Long id,

        @Schema(description = "Nome completo do usuário", example = "João Lamin")
        String nome,

        @Schema(description = "Endereço de e-mail do usuário", example = "joao@wave.com")
        String email,

        @Schema(description = "Papel do usuário", example = "USER", allowableValues = {"ADMIN", "USER"})
        String role
) {
}
