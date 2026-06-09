package br.com.fatec.wave.dto;
import br.com.fatec.wave.model.GeneroMusical;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de uma Música")
public record CriarMusicaDTO(String titulo, String artistaNome, GeneroMusical genero, Integer anoLancamento) {}