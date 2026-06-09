package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.exception.ResourceNotFoundException;
import br.com.fatec.wave.model.Musica;
import br.com.fatec.wave.model.GeneroMusical;
import br.com.fatec.wave.repository.MusicaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicaServiceImpl implements MusicaService {

    private final MusicaRepository repository;

    public MusicaServiceImpl(MusicaRepository repository) {
        this.repository = repository;
    }

    private MusicaResponseDTO convertToDTO(Musica musica) {
        return new MusicaResponseDTO(musica.getId(), musica.getTitulo(), musica.getArtistaNome(), musica.getGenero(), musica.getAnoLancamento());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MusicaResponseDTO> getAllMusicas(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MusicaResponseDTO> buscarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MusicaResponseDTO> buscarPorGenero(GeneroMusical genero, Pageable pageable) {
        return repository.findByGenero(genero, pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public MusicaResponseDTO getMusicaById(Long id) {
        return repository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Musica não encontrada"));
    }

    @Override
    @Transactional
    public MusicaResponseDTO criarMusica(CriarMusicaDTO dto) {
        Musica musica = new Musica();
        musica.setTitulo(dto.titulo());
        musica.setArtistaNome(dto.artistaNome());
        musica.setGenero(dto.genero());
        musica.setAnoLancamento(dto.anoLancamento());
        return convertToDTO(repository.save(musica));
    }

    @Override
    @Transactional
    public MusicaResponseDTO atualizarMusica(Long id, AtualizarMusicaDTO dto) {
        Musica musica = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Musica não encontrada"));
        if(dto.titulo() != null) musica.setTitulo(dto.titulo());
        if(dto.artistaNome() != null) musica.setArtistaNome(dto.artistaNome());
        if(dto.genero() != null) musica.setGenero(dto.genero());
        if(dto.anoLancamento() != null) musica.setAnoLancamento(dto.anoLancamento());
        return convertToDTO(repository.save(musica));
    }

    @Override
    @Transactional
    public void deletarMusica(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Musica não encontrada");
        repository.deleteById(id);
    }
}