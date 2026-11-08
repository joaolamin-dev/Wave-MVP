package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.MeuPerfilDTO;
import br.com.fatec.wave.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meu-perfil")
@Tag(name = "Perfil", description = "Endpoints do perfil individual do usuário logado")
public class MeuPerfilController {

    @GetMapping
    @Operation(summary = "Obter dados do usuário logado", description = "Identifica o usuário pelo Token JWT e retorna id, nome, e-mail e role.")
    public MeuPerfilDTO getMeuPerfil(@AuthenticationPrincipal Usuario usuario) {
        return new MeuPerfilDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }
}
