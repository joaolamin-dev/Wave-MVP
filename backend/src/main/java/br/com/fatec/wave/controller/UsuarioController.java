package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.AtualizarUsuarioDTO;
import br.com.fatec.wave.dto.CriarUsuarioDTO;
import br.com.fatec.wave.dto.UsuarioResponseDTO;
import br.com.fatec.wave.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @Cacheable(value = "listaDeUsuarios")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista paginada de todos os usuários cadastrados. Utiliza cache para otimização.")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsuarios(Pageable pageable) {
        Page<UsuarioResponseDTO> usuariosDTO = service.getAllUsuarios(pageable);
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuário por nome", description = "Realiza uma busca parametrizada pelo nome do usuário, ignorando letras maiúsculas ou minúsculas.")
    public ResponseEntity<Page<UsuarioResponseDTO>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        Page<UsuarioResponseDTO> usuariosDTO = service.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário específico através do seu identificador único.")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        UsuarioResponseDTO usuarioDTO = service.getUsuarioById(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping
    @CacheEvict(value = "listaDeUsuarios", allEntries = true) // Limpa o cache ao criar um novo usuário
    @Operation(summary = "Criar um novo usuário", description = "Cadastra um novo usuário no sistema retornando a URI de criação.")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        UsuarioResponseDTO usuarioCriado = service.criarUsuario(criarUsuarioDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioCriado.id())
                .toUri();
        return ResponseEntity.created(location).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaDeUsuarios", allEntries = true) // Limpa o cache ao atualizar dados
    @Operation(summary = "Atualizar usuário existente", description = "Atualiza parcialmente ou totalmente os dados de um usuário.")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) {
        UsuarioResponseDTO usuarioAtualizado = service.atualizarUsuario(id, atualizarUsuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaDeUsuarios", allEntries = true) // Limpa o cache ao deletar
    @Operation(summary = "Deletar um usuário", description = "Remove permanentemente um usuário da base de dados através do seu ID.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}