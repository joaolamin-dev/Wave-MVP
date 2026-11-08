package br.com.fatec.wave.controller;

import br.com.fatec.wave.model.Artista;
import br.com.fatec.wave.model.TipoArtista;
import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.ArtistaRepository;
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
class ArtistaApiTests {

    private static final long ID_INEXISTENTE = Long.MAX_VALUE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private Artista artista;
    private String token;

    @BeforeEach
    void criarDados() {
        artista = new Artista();
        artista.setNome("Artista Teste");
        artista.setNacionalidade("Brasileira");
        artista.setTipo(TipoArtista.BANDA);
        artista = artistaRepository.save(artista);

        Usuario usuario = usuarioRepository.save(new Usuario(null, "Usuario Teste", "artista.teste@wave.com", "senha-de-teste", UserRole.USER));
        token = "Bearer " + tokenService.gerarToken(usuario);
    }

    @Test
    void buscarPorIdComTokenResponde200ComODto() throws Exception {
        mockMvc.perform(get("/artistas/" + artista.getId()).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.id").value(artista.getId()))
                .andExpect(jsonPath("$.nome").value("Artista Teste"))
                .andExpect(jsonPath("$.nacionalidade").value("Brasileira"))
                .andExpect(jsonPath("$.tipo").value("BANDA"));
    }

    @Test
    void buscarPorIdInexistenteResponde404EmJson() throws Exception {
        mockMvc.perform(get("/artistas/" + ID_INEXISTENTE).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Artista não encontrado"))
                .andExpect(jsonPath("$.path").value("/artistas/" + ID_INEXISTENTE))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void buscarPorIdNaoNumericoResponde400EmJson() throws Exception {
        mockMvc.perform(get("/artistas/abc").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Parâmetro 'id' inválido"))
                .andExpect(jsonPath("$.path").value("/artistas/abc"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void buscarPorIdSemTokenResponde401() throws Exception {
        mockMvc.perform(get("/artistas/" + artista.getId()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Token ausente ou inválido"));
    }
}
