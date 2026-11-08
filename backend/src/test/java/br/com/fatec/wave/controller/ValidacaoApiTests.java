package br.com.fatec.wave.controller;

import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.UsuarioRepository;
import br.com.fatec.wave.security.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ValidacaoApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    void cadastroComNomeVazioResponde400() throws Exception {
        cadastrar("", "valido.teste@wave.com", "123456")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("nome: é obrigatório"))
                .andExpect(jsonPath("$.path").value("/usuarios"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void cadastroComEmailInvalidoResponde400() throws Exception {
        cadastrar("Usuario Teste", "naoehemail", "123456")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: deve ser um email válido"));
    }

    @Test
    void cadastroComSenhaCurtaResponde400() throws Exception {
        cadastrar("Usuario Teste", "valido.teste@wave.com", "12345")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("senha: deve ter entre 6 e 72 caracteres"));
    }

    @Test
    void cadastroComVariosCamposInvalidosListaTodosEmOrdem() throws Exception {
        cadastrar("", "naoehemail", "12345")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "email: deve ser um email válido; nome: é obrigatório; senha: deve ter entre 6 e 72 caracteres"));
    }

    @Test
    void cadastroValidoResponde201() throws Exception {
        cadastrar("Usuario Teste", "valido.teste@wave.com", "123456")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("valido.teste@wave.com"));
    }

    @Test
    void loginComCamposVaziosResponde400() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\",\"senha\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: é obrigatório; senha: é obrigatória"));
    }

    @Test
    void musicaComAnoNegativoComoAdminResponde400() throws Exception {
        mockMvc.perform(post("/musicas")
                        .header(HttpHeaders.AUTHORIZATION, bearer(salvarUsuario("admin.validacao@wave.com", UserRole.ADMIN)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Faixa\",\"artistaNome\":\"Artista\",\"genero\":\"ROCK\",\"anoLancamento\":-5}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("anoLancamento: deve ser 1900 ou posterior"));
    }

    // @Email aceita "", e gravar e-mail vazio deixaria a pessoa sem conseguir entrar.
    @Test
    void edicaoComEmailVazioResponde400ENaoAltera() throws Exception {
        Usuario usuario = salvarUsuario("edicao.validacao@wave.com", UserRole.USER);

        mockMvc.perform(put("/usuarios/" + usuario.getId())
                        .header(HttpHeaders.AUTHORIZATION, bearer(usuario))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: não pode ficar em branco"));

        assertEquals("edicao.validacao@wave.com", usuarioRepository.findById(usuario.getId()).orElseThrow().getEmail());
    }

    private ResultActions cadastrar(String nome, String email, String senha) throws Exception {
        return mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"%s\",\"email\":\"%s\",\"senha\":\"%s\"}".formatted(nome, email, senha)));
    }

    private Usuario salvarUsuario(String email, UserRole role) {
        return usuarioRepository.save(new Usuario(null, "Usuario Teste", email, "hash-de-teste", role));
    }

    private String bearer(Usuario autor) {
        return "Bearer " + tokenService.gerarToken(autor);
    }
}
