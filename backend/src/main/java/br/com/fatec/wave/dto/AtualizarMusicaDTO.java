package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.GeneroMusical;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para atualização de uma Música")
public record AtualizarMusicaDTO(
        @NotBlank(message = "é obrigatório") String titulo,
        @NotBlank(message = "é obrigatório") String artistaNome,
        GeneroMusical genero,
        @Min(value = 1900, message = "deve ser 1900 ou posterior") Integer anoLancamento) {}