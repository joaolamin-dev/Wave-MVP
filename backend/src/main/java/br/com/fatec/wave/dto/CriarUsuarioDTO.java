package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de requisição para criar um novo usuário")
public record CriarUsuarioDTO(

        @Schema(description = "Nome que será registrado", example = "João Lamin")
        String nome,

        @Schema(description = "E-mail válido e único", example = "joao@wave.com")
        String email,

        @Schema(description = "Senha de acesso do usuário", example = "senhaForte123")
        String senha
) {
}