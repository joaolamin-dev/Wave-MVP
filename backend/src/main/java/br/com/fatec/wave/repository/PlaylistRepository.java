package br.com.fatec.wave.repository;

import br.com.fatec.wave.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    // Busca apenas as playlists cujo usuário dono possui o e-mail informado
    List<Playlist> findByUsuarioEmail(String email);
}