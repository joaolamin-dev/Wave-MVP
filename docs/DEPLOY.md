# Deploy do backend

API publicada em <https://wave-api-16zu.onrender.com>. O banco é PostgreSQL 18 no Neon (`us-east-1`), na mesma região do Render (Virginia).

O Render builda a imagem a partir de `backend/Dockerfile`. Estes caminhos são relativos à raiz do repositório:

| Campo | Valor |
| --- | --- |
| Dockerfile Path | `backend/Dockerfile` |
| Docker Build Context Directory | `backend` |
| Build Filters (Included Paths) | `backend/**` |

## Variáveis de ambiente de produção

Configure os valores no painel do Render. Nunca versione valores reais.

| Variável | Onde obter | Observação |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Já definida na imagem como `prod` | Não precisa configurar no Render |
| `SPRING_DATASOURCE_URL` | Painel do Neon, convertida para JDBC | Formato abaixo |
| `SPRING_DATASOURCE_USERNAME` | Painel do Neon | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Painel do Neon | Senha do banco |
| `JWT_SECRET` | Gerar um valor aleatório e longo | Assina os tokens; trocar invalida os tokens em uso |
| `ADMIN_EMAIL` | Definido por você | E-mail do admin criado na primeira subida; ver nota abaixo |
| `ADMIN_PASSWORD` | Definido por você | Só vale na primeira subida; ver nota abaixo |
| `CORS_ALLOWED_ORIGINS` | URL do Static Site no Render | Lista separada por vírgula; ver regras abaixo |
| `PORT` | Injetada pelo Render | Não configurar; sem ela, a aplicação usa 8080 |

A aplicação não sobe se faltar alguma variável sem observação de padrão acima. Por isso, defina `CORS_ALLOWED_ORIGINS` antes de publicar o backend.

## Admin inicial

`ADMIN_EMAIL` e `ADMIN_PASSWORD` só valem na primeira subida. Se já existe um usuário com esse e-mail no banco, o inicializador não o atualiza: mudar `ADMIN_PASSWORD` depois não troca a senha. Mudar `ADMIN_EMAIL` para outro endereço não renomeia o admin: cria um segundo admin e mantém o anterior.

## CORS

**Nunca use `*` em `CORS_ALLOWED_ORIGINS`.** Informe a URL exata do Static Site, com `https://` e sem barra no final:

```
CORS_ALLOWED_ORIGINS=https://<seu-site>.onrender.com
```

Verificado em produção: login e perfil funcionam no site com essa variável apontando para o endereço dele.

## Swagger atrás do proxy do Render

O perfil `prod` usa `server.forward-headers-strategy=framework` para que a aplicação reconheça o `https` e o host públicos informados pelo proxy. Sem isso, um POST feito pelo próprio Swagger pareceria cross-origin.

Verificado em produção: login, `GET /meu-perfil` e o "Try it out" do Swagger funcionam, inclusive POST. Não é preciso incluir a URL do backend em `CORS_ALLOWED_ORIGINS`.

## Plano grátis do Render

Com 0,1 CPU e 512 MB, a primeira subida levou cerca de 58 s e não houve falta de memória nela. O uso de memória sob carga não foi medido.

Depois de 15 minutos ocioso, o serviço dorme, e o primeiro acesso seguinte leva cerca de 1 minuto.

## URL JDBC do Neon

O painel do Neon mostra a conexão no formato `postgresql://usuario:senha@...`, que não funciona aqui. Use o formato JDBC, sem usuário e senha na URL:

```
jdbc:postgresql://<host>/<banco>?sslmode=require
```

Usuário e senha ficam em `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.

# Deploy do frontend

Site publicado em <https://wave-web-s3te.onrender.com>.

Crie um Static Site no Render com:

| Campo | Valor |
| --- | --- |
| Root Directory | `frontend` |
| Build Command | `npm ci && npm run build` |
| Publish Directory | `dist` |

## Variáveis de ambiente

| Variável | Onde obter | Observação |
| --- | --- | --- |
| `VITE_API_URL` | URL do Web Service do backend no Render | Obrigatória em produção |
| `NODE_VERSION` | Use `22` | O Vite 7 exige Node recente |

O valor entra no bundle durante o build, então mudar a variável exige um novo deploy. O `npm run build` passa sem ela, mas o site publicado para ao carregar, com o erro `VITE_API_URL não definida` no console do navegador.

## Rewrite do react-router

Em **Redirects/Rewrites**, adicione uma regra para que abrir ou recarregar uma rota como `/Login` não dê 404:

| Source | Destination | Action |
| --- | --- | --- |
| `/*` | `/index.html` | Rewrite |

Verificado em produção: recarregar uma rota interna funciona, sem 404.
