package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.AtualizarUsuarioDTO;
import br.com.fatec.wave.dto.CriarUsuarioDTO;
import br.com.fatec.wave.dto.UsuarioResponseDTO;
import br.com.fatec.wave.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        List<UsuarioResponseDTO> usuarioDTO = service.getAllUsuarios();
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioResponseDTO usuarioDTO = service.getUsuarioById(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        UsuarioResponseDTO usuarioExiste = service.criarUsuario(criarUsuarioDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioExiste.id())
                .toUri();
        return ResponseEntity.created(location).body(usuarioExiste);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) {
        UsuarioResponseDTO usuarioAtualizado = service.atualizarUsuario(id, atualizarUsuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}