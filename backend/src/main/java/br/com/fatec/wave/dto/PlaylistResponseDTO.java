package br.com.fatec.wave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Dados de resposta da playlist do usuário")
public record PlaylistResponseDTO(
        Long id,
        String nome,
        List<MusicaResponseDTO> musicas
) {}