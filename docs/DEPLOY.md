# Deploy do backend

O Render builda a imagem a partir de `backend/Dockerfile` (Root Directory = `backend`). O banco é PostgreSQL no Neon.

## Variáveis de ambiente de produção

Configure os valores no painel do Render. Nunca versione valores reais.

| Variável | Onde obter | Observação |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Já definida na imagem como `prod` | Não precisa configurar no Render |
| `SPRING_DATASOURCE_URL` | Painel do Neon, convertida para JDBC | Formato abaixo |
| `SPRING_DATASOURCE_USERNAME` | Painel do Neon | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Painel do Neon | Senha do banco |
| `JWT_SECRET` | Gerar um valor aleatório e longo | Assina os tokens; trocar invalida os tokens em uso |
| `ADMIN_EMAIL` | Definido por você | E-mail do admin criado no primeiro start |
| `ADMIN_PASSWORD` | Definido por você | Só vale no primeiro start: mudar depois não troca a senha do admin já criado |
| `CORS_ALLOWED_ORIGINS` | URL do Static Site no Render | Lista separada por vírgula; ver regras abaixo |
| `PORT` | Injetada pelo Render | Não configurar; sem ela, a aplicação usa 8080 |

A aplicação não sobe se faltar alguma variável sem observação de padrão acima. Por isso, defina `CORS_ALLOWED_ORIGINS` antes de publicar o backend.

## CORS

**Nunca use `*` em `CORS_ALLOWED_ORIGINS`.** Informe a URL exata do Static Site, com `https://` e sem barra no final:

```
CORS_ALLOWED_ORIGINS=https://<seu-site>.onrender.com
```

## Swagger atrás do proxy do Render

O perfil `prod` usa `server.forward-headers-strategy=framework` para que a aplicação reconheça o `https` e o host públicos informados pelo proxy. Sem isso, um POST feito pelo próprio Swagger pareceria cross-origin. Essa configuração não foi testada localmente.

Plano B: se o "Try it out" falhar com 403 em produção, inclua a URL do backend em `CORS_ALLOWED_ORIGINS`, junto com a do Static Site.

## URL JDBC do Neon

O painel do Neon mostra a conexão no formato `postgresql://usuario:senha@...`, que não funciona aqui. Use o formato JDBC, sem usuário e senha na URL:

```
jdbc:postgresql://<host>/<banco>?sslmode=require
```

Usuário e senha ficam em `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.

# Deploy do frontend

Crie um Static Site no Render com:

| Campo | Valor |
| --- | --- |
| Root Directory | `frontend` |
| Build Command | `npm ci && npm run build` |
| Publish Directory | `dist` |

## Variável de ambiente

| Variável | Onde obter | Observação |
| --- | --- | --- |
| `VITE_API_URL` | URL do Web Service do backend no Render | Obrigatória em produção |

O valor entra no bundle durante o build, então mudar a variável exige um novo deploy. O `npm run build` passa sem ela, mas o site publicado para ao carregar, com o erro `VITE_API_URL não definida` no console do navegador.

## Rewrite do react-router

Em **Redirects/Rewrites**, adicione uma regra para que abrir ou recarregar uma rota como `/Login` não dê 404:

| Source | Destination | Action |
| --- | --- | --- |
| `/*` | `/index.html` | Rewrite |
