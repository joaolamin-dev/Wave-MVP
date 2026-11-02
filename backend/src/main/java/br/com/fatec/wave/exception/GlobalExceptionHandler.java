package br.com.fatec.wave.exception;

import br.com.fatec.wave.dto.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

// Herda de ResponseEntityExceptionHandler para que erros do Spring (rota inexistente, JSON inválido)
// mantenham o status 4xx em vez de cair no tratamento genérico de 500.
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> tratarNaoEncontrado(ResourceNotFoundException ex, HttpServletRequest request) {
        return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> tratarConflito(DataIntegrityViolationException ex, HttpServletRequest request) {
        // shortcut: identifica o e-mail pelo texto da exceção; trocar por uma exceção própria se surgirem outros campos únicos
        boolean emailDuplicado = String.valueOf(ex.getMessage()).toLowerCase().contains("email");
        String mensagem = emailDuplicado
                ? "Email já cadastrado na plataforma"
                : "Operação não permitida: o registro está vinculado a outros dados";
        return criarResposta(HttpStatus.CONFLICT, mensagem, request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> tratarCredenciaisInvalidas(BadCredentialsException ex, HttpServletRequest request) {
        return criarResposta(HttpStatus.UNAUTHORIZED, "Email ou senha incorretos", request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> tratarAcessoNegado(AccessDeniedException ex, HttpServletRequest request) {
        return criarResposta(HttpStatus.FORBIDDEN, "Sem permissão para esta operação", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> tratarErroInesperado(Exception ex, HttpServletRequest request) {
        logger.error("Erro inesperado em " + request.getRequestURI(), ex);
        return criarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus status = HttpStatus.valueOf(statusCode.value());
        String mensagem = body instanceof ProblemDetail problema ? problema.getDetail() : status.getReasonPhrase();
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(status).headers(headers).body(criarCorpo(status, mensagem, path));
    }

    private ResponseEntity<Object> criarResposta(HttpStatus status, String mensagem, String path) {
        return ResponseEntity.status(status).body(criarCorpo(status, mensagem, path));
    }

    private ErroResponseDTO criarCorpo(HttpStatus status, String mensagem, String path) {
        return new ErroResponseDTO(Instant.now(), status.value(), status.getReasonPhrase(), mensagem, path);
    }
}
