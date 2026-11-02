package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto de requisição para criar um novo usuário")
public record CriarUsuarioDTO(

        @Schema(description = "Nome que será registrado", example = "João Lamin")
        @NotBlank(message = "é obrigatório")
        @Size(max = 100, message = "máximo de 100 caracteres")
        String nome,

        @Schema(description = "E-mail válido e único", example = "joao@wave.com")
        @NotBlank(message = "é obrigatório")
        @Email(message = "deve ser um email válido")
        String email,

        @Schema(description = "Senha de acesso do usuário", example = "senhaForte123")
        @NotBlank(message = "é obrigatória")
        @Size(min = 6, max = 72, message = "deve ter entre 6 e 72 caracteres") // 72 é o limite do BCrypt
        String senha
) {
}