package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de requisição para atualizar os dados de um usuário existente")
public record AtualizarUsuarioDTO(

        @Schema(description = "Novo nome do usuário", example = "João Lamin Atualizado")
        String nome,

        @Schema(description = "Novo e-mail do usuário", example = "joao.novo@wave.com")
        String email,

        @Schema(description = "Nova senha do usuário (caso queira alterar)", example = "novaSenha321")
        String senha
) {
}