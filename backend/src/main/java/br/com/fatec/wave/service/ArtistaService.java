package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.model.TipoArtista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistaService {
    Page<ArtistaResponseDTO> getAllArtistas(Pageable pageable);
    Page<ArtistaResponseDTO> buscarPorNome(String nome, Pageable pageable);
    Page<ArtistaResponseDTO> buscarPorTipo(TipoArtista tipo, Pageable pageable);
    ArtistaResponseDTO getArtistaById(Long id);
    ArtistaResponseDTO criarArtista(CriarArtistaDTO dto);
    ArtistaResponseDTO atualizarArtista(Long id, AtualizarArtistaDTO dto);
    void deletarArtista(Long id);
}