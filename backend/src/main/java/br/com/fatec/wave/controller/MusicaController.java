package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.model.GeneroMusical;
import br.com.fatec.wave.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/musicas")
@Tag(name = "Músicas", description = "Endpoints para gerenciamento do catálogo musical")
public class MusicaController {

    private final MusicaService service;

    public MusicaController(MusicaService service) { this.service = service; }

    @GetMapping
    @Cacheable(value = "listaMusicas")
    @Operation(summary = "Listar todas as músicas", description = "Retorna lista paginada e cacheada de músicas")
    public ResponseEntity<Page<MusicaResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAllMusicas(pageable));
    }

    @GetMapping("/buscar-titulo")
    @Operation(summary = "Buscar música por título")
    public ResponseEntity<Page<MusicaResponseDTO>> buscarTitulo(@RequestParam String titulo, Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorTitulo(titulo, pageable));
    }

    @GetMapping("/buscar-genero")
    @Operation(summary = "Buscar música por gênero")
    public ResponseEntity<Page<MusicaResponseDTO>> buscarGenero(@RequestParam GeneroMusical genero, Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorGenero(genero, pageable));
    }

    @PostMapping
    @CacheEvict(value = "listaMusicas", allEntries = true)
    @Operation(summary = "Cadastrar uma música")
    public ResponseEntity<MusicaResponseDTO> criar(@RequestBody CriarMusicaDTO dto) {
        MusicaResponseDTO response = service.criarMusica(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaMusicas", allEntries = true)
    @Operation(summary = "Atualizar música")
    public ResponseEntity<MusicaResponseDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarMusicaDTO dto) {
        return ResponseEntity.ok(service.atualizarMusica(id, dto));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaMusicas", allEntries = true)
    @Operation(summary = "Deletar música")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarMusica(id);
        return ResponseEntity.noContent().build();
    }
}