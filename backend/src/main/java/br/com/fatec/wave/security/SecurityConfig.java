package br.com.fatec.wave.security;

import br.com.fatec.wave.dto.ErroResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleHabilitado;

    @Value("${app.cors.allowed-origins}")
    private List<String> origensPermitidas;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 1. PERMITE O USO DE FRAMES PARA O H2 CONSOLE
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas públicas (Login, Cadastro, Swagger e, só com o console ligado, H2)
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").access((autenticacao, contexto) -> new AuthorizationDecision(h2ConsoleHabilitado))
                        .requestMatchers("/error").permitAll()

                        // Apenas ADMIN pode criar ou editar Artistas e Músicas
                        .requestMatchers(HttpMethod.POST, "/artistas/**", "/musicas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/artistas/**", "/musicas/**").hasRole("ADMIN")

                        // Apenas ADMIN lista, busca ou consulta usuários; o próprio perfil vem de /meu-perfil
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")

                        // Regras gerais
                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN") // Apenas Admin deleta qualquer coisa
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasAnyRole("ADMIN", "USER") // Usuário pode editar próprio perfil
                        .requestMatchers(HttpMethod.GET).authenticated() // Qualquer um logado pode fazer buscas (GET)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(erros -> erros
                        .authenticationEntryPoint((request, response, ex) ->
                                escreverErro(request, response, HttpStatus.UNAUTHORIZED, "Token ausente ou inválido"))
                        .accessDeniedHandler((request, response, ex) ->
                                escreverErro(request, response, HttpStatus.FORBIDDEN, "Sem permissão para esta operação"))
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Erros do Spring Security acontecem antes dos controllers, fora do alcance do GlobalExceptionHandler.
    private void escreverErro(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String mensagem) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(),
                new ErroResponseDTO(Instant.now(), status.value(), status.getReasonPhrase(), mensagem, request.getRequestURI()));
    }

    // CONFIGURAÇÃO CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(origensPermitidas);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}