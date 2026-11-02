package br.com.fatec.wave.exception;

import br.com.fatec.wave.dto.ErroResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest("POST", "/usuarios");

    @Test
    void conflitoDeEmailInformaEmailJaCadastrado() {
        var ex = new DataIntegrityViolationException("Email já existente na plataforma: a@wave.com");

        assertErro(handler.tratarConflito(ex, request), 409, "Email já cadastrado na plataforma");
    }

    @Test
    void conflitoDeChaveEstrangeiraNaoFalaEmEmailNemExpoeSql() {
        var ex = new DataIntegrityViolationException("Referential integrity constraint violation: delete from musicas");

        assertErro(handler.tratarConflito(ex, request), 409, "Operação não permitida: o registro está vinculado a outros dados");
    }

    @Test
    void erroInesperadoNaoExpoeDetalhesInternos() {
        assertErro(handler.tratarErroInesperado(new IllegalStateException("segredo interno"), request), 500, "Erro interno no servidor");
    }

    private void assertErro(ResponseEntity<Object> resposta, int status, String mensagem) {
        var corpo = (ErroResponseDTO) resposta.getBody();
        assertEquals(status, resposta.getStatusCode().value());
        assertEquals(mensagem, corpo.message());
        assertEquals("/usuarios", corpo.path());
    }
}
