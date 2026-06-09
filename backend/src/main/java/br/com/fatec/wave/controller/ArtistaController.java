package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.*;
import br.com.fatec.wave.model.TipoArtista;
import br.com.fatec.wave.service.ArtistaService;
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
@RequestMapping("/artistas")
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de artistas")
public class ArtistaController {

    private final ArtistaService service;

    public ArtistaController(ArtistaService service) { this.service = service; }

    @GetMapping
    @Cacheable(value = "listaArtistas")
    @Operation(summary = "Listar todos os artistas", description = "Retorna lista paginada e cacheada de artistas")
    public ResponseEntity<Page<ArtistaResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAllArtistas(pageable));
    }

    @GetMapping("/buscar-nome")
    @Operation(summary = "Buscar artista por nome")
    public ResponseEntity<Page<ArtistaResponseDTO>> buscarNome(@RequestParam String nome, Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorNome(nome, pageable));
    }

    @GetMapping("/buscar-tipo")
    @Operation(summary = "Buscar artista por tipo (SOLO, DUO, BANDA)")
    public ResponseEntity<Page<ArtistaResponseDTO>> buscarTipo(@RequestParam TipoArtista tipo, Pageable pageable) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo, pageable));
    }

    @PostMapping
    @CacheEvict(value = "listaArtistas", allEntries = true)
    @Operation(summary = "Criar um novo artista")
    public ResponseEntity<ArtistaResponseDTO> criar(@RequestBody CriarArtistaDTO dto) {
        ArtistaResponseDTO response = service.criarArtista(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaArtistas", allEntries = true)
    @Operation(summary = "Atualizar artista")
    public ResponseEntity<ArtistaResponseDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarArtistaDTO dto) {
        return ResponseEntity.ok(service.atualizarArtista(id, dto));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaArtistas", allEntries = true)
    @Operation(summary = "Deletar artista")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarArtista(id);
        return ResponseEntity.noContent().build();
    }
}