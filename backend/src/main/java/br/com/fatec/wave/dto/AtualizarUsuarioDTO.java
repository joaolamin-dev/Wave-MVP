package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Nulo mantém o valor atual. @Pattern recusa texto em branco, que @Email e @Size(max) deixariam passar e o service gravaria.
@Schema(description = "Objeto de requisição para atualizar os dados de um usuário existente")
public record AtualizarUsuarioDTO(

        @Schema(description = "Novo nome do usuário", example = "João Lamin Atualizado")
        @Pattern(regexp = "(?s).*\\S.*", message = "não pode ficar em branco")
        @Size(max = 100, message = "máximo de 100 caracteres")
        String nome,

        @Schema(description = "Novo e-mail do usuário", example = "joao.novo@wave.com")
        @Pattern(regexp = "(?s).*\\S.*", message = "não pode ficar em branco")
        @Email(message = "deve ser um email válido")
        String email,

        @Schema(description = "Nova senha do usuário (caso queira alterar)", example = "novaSenha321")
        @Pattern(regexp = "(?s).*\\S.*", message = "não pode ficar em branco")
        @Size(min = 6, max = 72, message = "deve ter entre 6 e 72 caracteres") // 72 é o limite do BCrypt
        String senha
) {
}