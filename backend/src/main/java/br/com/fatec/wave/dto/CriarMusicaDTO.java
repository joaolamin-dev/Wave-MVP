package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.GeneroMusical;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de uma Música")
public record CriarMusicaDTO(
        @NotBlank(message = "é obrigatório") String titulo,
        @NotBlank(message = "é obrigatório") String artistaNome,
        @NotNull(message = "é obrigatório") GeneroMusical genero,
        @Min(value = 1900, message = "deve ser 1900 ou posterior") Integer anoLancamento) {}