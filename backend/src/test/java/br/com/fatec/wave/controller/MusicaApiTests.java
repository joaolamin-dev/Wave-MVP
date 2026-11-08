package br.com.fatec.wave.controller;

import br.com.fatec.wave.model.GeneroMusical;
import br.com.fatec.wave.model.Musica;
import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.MusicaRepository;
import br.com.fatec.wave.repository.UsuarioRepository;
import br.com.fatec.wave.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MusicaApiTests {

    private static final long ID_INEXISTENTE = Long.MAX_VALUE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private Musica musica;
    private String token;

    @BeforeEach
    void criarDados() {
        musica = new Musica();
        musica.setTitulo("Musica Teste");
        musica.setArtistaNome("Artista Teste");
        musica.setGenero(GeneroMusical.ROCK);
        musica.setAnoLancamento(2001);
        musica = musicaRepository.save(musica);

        Usuario usuario = usuarioRepository.save(new Usuario(null, "Usuario Teste", "musica.teste@wave.com", "senha-de-teste", UserRole.USER));
        token = "Bearer " + tokenService.gerarToken(usuario);
    }

    @Test
    void buscarPorIdComTokenResponde200ComODto() throws Exception {
        mockMvc.perform(get("/musicas/" + musica.getId()).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.id").value(musica.getId()))
                .andExpect(jsonPath("$.titulo").value("Musica Teste"))
                .andExpect(jsonPath("$.artistaNome").value("Artista Teste"))
                .andExpect(jsonPath("$.genero").value("ROCK"))
                .andExpect(jsonPath("$.anoLancamento").value(2001));
    }

    @Test
    void buscarPorIdInexistenteResponde404EmJson() throws Exception {
        mockMvc.perform(get("/musicas/" + ID_INEXISTENTE).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Musica não encontrada"))
                .andExpect(jsonPath("$.path").value("/musicas/" + ID_INEXISTENTE))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void buscarPorIdNaoNumericoResponde400EmJson() throws Exception {
        mockMvc.perform(get("/musicas/abc").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Parâmetro 'id' inválido"))
                .andExpect(jsonPath("$.path").value("/musicas/abc"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void buscarPorIdSemTokenResponde401() throws Exception {
        mockMvc.perform(get("/musicas/" + musica.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Token ausente ou inválido"));
    }
}
