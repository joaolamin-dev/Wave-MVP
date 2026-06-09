package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.exception.ResourceNotFoundException;
import br.com.fatec.wave.model.Artista;
import br.com.fatec.wave.model.TipoArtista;
import br.com.fatec.wave.repository.ArtistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository repository;

    public ArtistaServiceImpl(ArtistaRepository repository) {
        this.repository = repository;
    }

    private ArtistaResponseDTO convertToDTO(Artista artista) {
        return new ArtistaResponseDTO(artista.getId(), artista.getNome(), artista.getNacionalidade(), artista.getTipo());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistaResponseDTO> getAllArtistas(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistaResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistaResponseDTO> buscarPorTipo(TipoArtista tipo, Pageable pageable) {
        return repository.findByTipo(tipo, pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistaResponseDTO getArtistaById(Long id) {
        return repository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Artista não encontrado"));
    }

    @Override
    @Transactional
    public ArtistaResponseDTO criarArtista(CriarArtistaDTO dto) {
        Artista artista = new Artista();
        artista.setNome(dto.nome());
        artista.setNacionalidade(dto.nacionalidade());
        artista.setTipo(dto.tipo());
        return convertToDTO(repository.save(artista));
    }

    @Override
    @Transactional
    public ArtistaResponseDTO atualizarArtista(Long id, AtualizarArtistaDTO dto) {
        Artista artista = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista não encontrado"));
        if(dto.nome() != null) artista.setNome(dto.nome());
        if(dto.nacionalidade() != null) artista.setNacionalidade(dto.nacionalidade());
        if(dto.tipo() != null) artista.setTipo(dto.tipo());
        return convertToDTO(repository.save(artista));
    }

    @Override
    @Transactional
    public void deletarArtista(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Artista não encontrado");
        repository.deleteById(id);
    }
}