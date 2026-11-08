package br.com.fatec.wave.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CorsApiTests {

    private static final String ORIGEM_PERMITIDA = "https://front.wave.test";
    private static final String ORIGEM_NAO_PERMITIDA = "https://outro-site.test";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void origemPermitidaRecebeAllowOriginSemCredentials() throws Exception {
        mockMvc.perform(get("/usuarios").header(HttpHeaders.ORIGIN, ORIGEM_PERMITIDA))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ORIGEM_PERMITIDA))
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS));
    }

    @Test
    void origemNaoPermitidaNaoRecebeAllowOrigin() throws Exception {
        mockMvc.perform(get("/usuarios").header(HttpHeaders.ORIGIN, ORIGEM_NAO_PERMITIDA))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }

    @Test
    void preflightDeOrigemPermitidaFunciona() throws Exception {
        mockMvc.perform(options("/login")
                        .header(HttpHeaders.ORIGIN, ORIGEM_PERMITIDA)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "authorization,content-type"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ORIGEM_PERMITIDA))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "authorization, content-type"));
    }

    @Test
    void preflightDeOrigemNaoPermitidaERecusado() throws Exception {
        mockMvc.perform(options("/login")
                        .header(HttpHeaders.ORIGIN, ORIGEM_NAO_PERMITIDA)
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}
