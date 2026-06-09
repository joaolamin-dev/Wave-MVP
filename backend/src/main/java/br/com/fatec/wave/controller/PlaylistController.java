package br.com.fatec.wave.controller;

import br.com.fatec.wave.dto.PlaylistResponseDTO;
import br.com.fatec.wave.dto.MusicaResponseDTO;
import br.com.fatec.wave.model.Playlist;
import br.com.fatec.wave.repository.PlaylistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meu-perfil/playlists")
@Tag(name = "Perfil", description = "Endpoints do perfil individual do usuário logado")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    public PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    @Operation(summary = "Obter playlists do usuário logado", description = "Identifica o usuário pelo Token JWT e retorna apenas as playlists dele.")
    public ResponseEntity<List<PlaylistResponseDTO>> getMinhasPlaylists(
            @AuthenticationPrincipal UserDetails usuarioLogado) {


        String emailDoUsuario = usuarioLogado.getUsername();

        List<Playlist> playlists = playlistRepository.findByUsuarioEmail(emailDoUsuario);

        List<PlaylistResponseDTO> response = playlists.stream()
                .map(p -> new PlaylistResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getMusicas().stream()
                                .map(m -> new MusicaResponseDTO(m.getId(), m.getTitulo(), m.getArtistaNome(), m.getGenero(), m.getAnoLancamento()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}