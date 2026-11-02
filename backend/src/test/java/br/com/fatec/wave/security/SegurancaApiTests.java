package br.com.fatec.wave.security;

import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SegurancaApiTests {

    private static final String EMAIL = "usuario.teste@wave.com";
    private static final String SENHA = "senha-de-teste";
    private static final String TOKEN_INVALIDO = "Bearer token-invalido";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    private Usuario usuario;

    @BeforeEach
    void criarUsuario() {
        usuario = usuarioRepository.save(new Usuario(null, "Usuario Teste", EMAIL, passwordEncoder.encode(SENHA), UserRole.USER));
    }

    @Test
    void semTokenResponde401EmJson() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Token ausente ou inválido"))
                .andExpect(jsonPath("$.path").value("/usuarios"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void tokenInvalidoResponde401() throws Exception {
        mockMvc.perform(get("/usuarios").header(HttpHeaders.AUTHORIZATION, TOKEN_INVALIDO))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Token ausente ou inválido"));
    }

    @Test
    void loginComTokenInvalidoNoHeaderECredenciaisCertasResponde200() throws Exception {
        mockMvc.perform(post("/login")
                        .header(HttpHeaders.AUTHORIZATION, TOKEN_INVALIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"%s\",\"senha\":\"%s\"}".formatted(EMAIL, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void usuarioComumEmRotaDeAdminResponde403EmJson() throws Exception {
        mockMvc.perform(delete("/artistas/1").header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.gerarToken(usuario)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.error").value("Forbidden"))
                .andExpect(jsonPath("$.message").value("Sem permissão para esta operação"))
                .andExpect(jsonPath("$.path").value("/artistas/1"));
    }
}
