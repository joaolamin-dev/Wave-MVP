package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto contendo as credenciais para realizar o login na aplicação")
public record DadosAutenticacao(

        @Schema(description = "E-mail cadastrado do usuário", example = "admin@wave.com")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        String senha
) {
}