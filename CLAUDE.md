# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Projeto

Wave — plataforma de streaming musical (projeto acadêmico FATEC). Monorepo com dois apps independentes: `backend/` (Spring Boot 3.5 / Java 17) e `frontend/` (React 19 + Vite + Tailwind). O código, os nomes de classes/variáveis, DTOs, rotas da API e comentários são **em português** — mantenha essa convenção ao adicionar código.

## Comandos

### Backend (`cd backend`)
```bash
./mvnw spring-boot:run              # sobe a API em http://localhost:8080
./mvnw clean package                # build do jar
./mvnw test                         # roda todos os testes
./mvnw test -Dtest=WaveApplicationTests          # um teste específico (classe)
./mvnw test -Dtest=NomeClasse#nomeDoMetodo       # um método específico
```
No Windows use `mvnw.cmd` no PowerShell; o `./mvnw` funciona no Bash tool.

Existe apenas `WaveApplicationTests` (smoke test de contexto). Não há suíte de testes real ainda.

### Frontend (`cd frontend`)
```bash
npm install
npm run dev        # http://localhost:5173
npm run build
npm run lint       # eslint com --max-warnings 0 (qualquer warning quebra)
```

### URLs úteis com o backend rodando
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 console (só no perfil `dev`): http://localhost:8080/h2-console (JDBC `jdbc:h2:file:./wavedb`, user/senha `sa`/`sa`)

## Arquitetura do backend

Camadas em `br.com.fatec.wave`: `controller` → `service` (interface + `*Impl`) → `repository` (Spring Data JPA) → `model` (entidades JPA). Controllers nunca expõem entidades: todo tráfego passa por **DTOs record** em `dto/` (`Criar*DTO`, `Atualizar*DTO`, `*ResponseDTO`); a conversão entidade↔DTO vive em métodos `convertToDTO` dentro dos `*ServiceImpl`.

Exceção a esse padrão: `PlaylistController` fala direto com `PlaylistRepository` (não tem camada de service).

### Autenticação (JWT stateless)
1. `POST /login` (`AutenticacaoController`) autentica via `AuthenticationManager` e devolve `DadosTokenJWT`.
2. `TokenService` assina/valida HMAC256 com issuer `"API Wave"`, expiração de 2h, segredo em `api.security.token.secret`.
3. `SecurityFilter` (`OncePerRequestFilter`, registrado antes do `UsernamePasswordAuthenticationFilter`) lê o header `Authorization`, extrai o subject (e-mail) e popula o `SecurityContextHolder`.
4. `Usuario` **implementa `UserDetails`** diretamente (`getUsername()` retorna o e-mail, `getPassword()` retorna `senha`). `UsuarioServiceImpl` também implementa `UserDetailsService`.
5. `UserRole.ADMIN` recebe `ROLE_ADMIN` **e** `ROLE_USER` em `Usuario.getAuthorities()`.

Toda a autorização está centralizada nas regras de `SecurityFilterChain` em `SecurityConfig` (não há `@PreAuthorize` nos controllers): públicos são `POST /login`, `POST /usuarios`, Swagger e, só com o console H2 ligado, `/h2-console/**`; POST/PUT em `/artistas/**` e `/musicas/**` exigem ADMIN; qualquer DELETE exige ADMIN; todo o resto exige autenticação. Ao criar um endpoint novo, a regra tem que ser adicionada lá.

### Persistência e dados iniciais
Perfis: `dev` é o padrão (`spring.profiles.default`), `prod` é ativado por `SPRING_PROFILES_ACTIVE=prod` e `test` é ativado em `src/test/resources/config/application.properties`. Nenhum valor real fica versionado.

- `dev`: H2 em **arquivo** (`backend/wavedb.mv.db`, não versionado), console H2 ligado, segredo e senha do admin falsos (`dev-only-nao-usar-em-producao`).
- `prod`: PostgreSQL no Neon, console H2 desligado. Exige as variáveis `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, `ADMIN_EMAIL` e `ADMIN_PASSWORD`, sem valor padrão. A URL do Neon precisa estar no formato JDBC: `jdbc:postgresql://host/db?sslmode=require`, com usuário e senha nas variáveis separadas (não use o `postgresql://usuario:senha@...` do painel).
- `test`: H2 em memória.

`dev` e `prod` usam `ddl-auto=update` — o schema evolui pelas entidades, não há migrations. `DataInitializerConfig` é um `CommandLineRunner` idempotente que cria um único admin (de `app.admin.*`), artistas, músicas e playlists de exemplo. Admin já existente não é alterado: mudar `ADMIN_PASSWORD` depois do primeiro start não troca a senha. Apagar `wavedb.mv.db` recria tudo do zero em `dev`.

### Outros pontos
- Cache: `@EnableCaching` no `WaveApplication`; `UsuarioController` usa `@Cacheable("listaDeUsuarios")` no GET e `@CacheEvict(allEntries = true)` em POST/PUT/DELETE. Mantenha o evict ao adicionar mutações.
- Listagens e buscas são **paginadas** (`Page`/`Pageable`); as buscas seguem o padrão `/{recurso}/buscar-*` (ex. `/musicas/buscar-titulo`, `/artistas/buscar-tipo`) exceto `/usuarios/buscar`.
- Erros 404 vêm de `ResourceNotFoundException` anotada com `@ResponseStatus(NOT_FOUND)`; não há `@ControllerAdvice`.
- `SpringDocConfig` define o security scheme `bearer-key`; use `@Tag`/`@Operation` nos endpoints novos como nos existentes.
- CORS libera todas as origens (`setAllowedOriginPatterns(List.of("*"))`).

## Arquitetura do frontend

`main.jsx` → `App.jsx`, que é só uma tabela de `<Route>` em `react-router-dom` v7. Cada página mora em `src/pages/` e renderiza `Header` + `Footer` por conta própria (não há layout compartilhado).

- **`src/services/api.js`** — instância única do axios. `baseURL` alterna por `import.meta.env.DEV`: `http://localhost:8080` em dev, Render em produção. Um interceptor injeta `Authorization: Bearer <token>` lendo `localStorage`. **Use sempre esse `api`, nunca `axios` ou `fetch` direto.**
- **Sessão** — não existe context/store de auth. O estado de login é `localStorage.token` + `localStorage.userEmail`, lido direto por `Header.jsx`, `Login.jsx` e `Perfil.jsx`. Como a API não tem endpoint "usuário atual", esses componentes fazem `GET /usuarios?size=100` e filtram pelo e-mail salvo; playlists vêm de `GET /meu-perfil/playlists` (o backend resolve o usuário pelo token).
- **Páginas de artista** — `_ArtistTemplate.jsx` concentra toda a UI e o player de áudio (`useRef` em `<audio>`, play/pause/next/prev/volume/progresso, ícones SVG inline). Cada artista é um wrapper fino (ex. `Linkin_Park.jsx`) que passa `name`, `description`, `images`, `albums`, `musics` como props literais. **Para adicionar um artista: criar o wrapper, registrar a rota em `App.jsx` e adicionar a entrada em `src/data/artistas.js`** (essa lista alimenta a busca do `Header`).
- **Assets** — mp3 e imagens ficam em `frontend/public/assets/{mp3,img}` e são referenciados por caminho absoluto (`/assets/mp3/Faint.mp3`). `src/data/artistsData.js` é legado e usa caminhos `/mp3/...` e `/images/...` que não existem; não é importado por nada — prefira `data/artistas.js`.
- **Estilo** — Tailwind v3 com a paleta custom `wave-*` e `bg-wave-gradient` em `tailwind.config.js`, embora boa parte das páginas use cores arbitrárias (`#2c003e`, `#ff00cc`) inline.
- O proxy `/api` em `vite.config.js` aponta para o backend do Render e não é usado pelo `api.js`.

## Deploy

`backend/Dockerfile` faz build multi-stage (Maven 3.8.5/JDK 17 → temurin 17) e expõe 8080; o backend está publicado em `https://wave-backend-v5zn.onrender.com`.

## Regras de commit (obrigatórias)
- Nunca faça commit, push ou merge sem eu pedir. Ao terminar uma mudança, proponha a mensagem e espere.
- Commits atômicos: uma mudança lógica por commit, com o menor número de arquivos possível. Não misture correção, refatoração e formatação.
- Formato Conventional Commits, em português: `tipo(escopo): descrição no imperativo` (máx. 72 caracteres).
  - tipos: feat, fix, refactor, docs, test, chore, ci, style
  - escopos: backend, frontend, repo
  - exemplo: `fix(backend): retornar 401 para token inválido`
- Se precisar, use o corpo do commit para explicar o porquê da mudança.
- Antes de propor o commit, mostre um resumo do `git diff` e confirme que o código ainda compila.
- Nunca use `git add .`; adicione os arquivos pelo nome.