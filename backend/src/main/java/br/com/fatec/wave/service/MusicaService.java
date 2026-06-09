package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.model.GeneroMusical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicaService {
    Page<MusicaResponseDTO> getAllMusicas(Pageable pageable);
    Page<MusicaResponseDTO> buscarPorTitulo(String titulo, Pageable pageable);
    Page<MusicaResponseDTO> buscarPorGenero(GeneroMusical genero, Pageable pageable);
    MusicaResponseDTO getMusicaById(Long id);
    MusicaResponseDTO criarMusica(CriarMusicaDTO dto);
    MusicaResponseDTO atualizarMusica(Long id, AtualizarMusicaDTO dto);
    void deletarMusica(Long id);
}