package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.GeneroMusical;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com os dados da Música")
public record MusicaResponseDTO(Long id, String titulo, String artistaNome, GeneroMusical genero, Integer anoLancamento) {}