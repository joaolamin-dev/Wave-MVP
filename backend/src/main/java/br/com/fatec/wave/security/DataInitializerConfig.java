package br.com.fatec.wave.security;

import br.com.fatec.wave.model.*;
import br.com.fatec.wave.repository.ArtistaRepository;
import br.com.fatec.wave.repository.MusicaRepository;
import br.com.fatec.wave.repository.PlaylistRepository;
import br.com.fatec.wave.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializerConfig.class);

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initializeData(
            UsuarioRepository usuarioRepository,
            ArtistaRepository artistaRepository,
            MusicaRepository musicaRepository,
            PlaylistRepository playlistRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Usuario adminMaster = null;

            // --- 1. INICIALIZAÇÃO DO USUÁRIO ADMIN ---
            if (!usuarioRepository.existsByEmail(adminEmail)) {
                Usuario admin = new Usuario();
                admin.setNome("Admin Master");
                admin.setEmail(adminEmail);
                admin.setSenha(passwordEncoder.encode(adminPassword));
                admin.setRole(UserRole.ADMIN);
                adminMaster = usuarioRepository.save(admin);
                logger.info(">>> Usuário Admin criado com sucesso!");
            } else {
                adminMaster = usuarioRepository.findByEmail(adminEmail).orElse(null);
            }

            // --- 2. POPULAR DADOS DE TESTE (ARTISTAS E MÚSICAS) ---
            Musica musica1 = null;
            Musica musica2 = null;

            if (artistaRepository.count() == 0) {
                Artista artista1 = new Artista();
                artista1.setNome("System Of A Down");
                artista1.setNacionalidade("Armênia/EUA");
                artista1.setTipo(TipoArtista.BANDA);

                Artista artista2 = new Artista();
                artista2.setNome("Linkin Park");
                artista2.setNacionalidade("EUA");
                artista2.setTipo(TipoArtista.BANDA);

                artistaRepository.saveAll(List.of(artista1, artista2));
                logger.info(">>> Artistas iniciais populados com sucesso!");
            }

            if (musicaRepository.count() == 0) {
                Musica m1 = new Musica();
                m1.setTitulo("Chop Suey");
                m1.setArtistaNome("System Of A Down");
                m1.setGenero(GeneroMusical.ROCK);
                m1.setAnoLancamento(2001);

                Musica m2 = new Musica();
                m2.setTitulo("Toxicity");
                m2.setArtistaNome("System Of A Down");
                m2.setGenero(GeneroMusical.ROCK);
                m2.setAnoLancamento(2001);

                Musica m3 = new Musica();
                m3.setTitulo("Numb");
                m3.setArtistaNome("Linkin Park");
                m3.setGenero(GeneroMusical.ROCK);
                m3.setAnoLancamento(2003);

                musicaRepository.saveAll(List.of(m1, m2, m3));

                // Guarda as referências para usar na playlist abaixo
                musica1 = m1;
                musica2 = m3;

                logger.info(">>> Músicas iniciais populadas com sucesso!");
            } else {
                List<Musica> musicas = musicaRepository.findAll();
                if(musicas.size() >= 2) {
                    musica1 = musicas.get(0);
                    musica2 = musicas.get(1);
                }
            }

            // --- 3. POPULAR PLAYLIST PARA O PERFIL DO ADMIN ---
            // Se o admin existir, tivermos músicas, e ele não tiver nenhuma playlist
            if (adminMaster != null && musica1 != null && playlistRepository.findByUsuarioEmail(adminEmail).isEmpty()) {

                Playlist playlistRock = new Playlist();
                playlistRock.setNome("Clássicos do Nu Metal");
                playlistRock.setUsuario(adminMaster);
                playlistRock.setMusicas(List.of(musica1, musica2)); // Adiciona Chop Suey e Numb

                Playlist playlistTreino = new Playlist();
                playlistTreino.setNome("Workout Mix");
                playlistTreino.setUsuario(adminMaster);
                playlistTreino.setMusicas(List.of(musica1)); // Adiciona só Chop Suey

                playlistRepository.saveAll(List.of(playlistRock, playlistTreino));
                logger.info(">>> Playlists iniciais para o Admin Master criadas com sucesso!");
            }
        };
    }
}