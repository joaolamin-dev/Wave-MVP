package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto que encapsula o token JWT retornado após o login")
public record DadosTokenJWT(

        @Schema(description = "Token JWT no formato Bearer", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}