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
| `PORT` | Injetada pelo Render | Não configurar; sem ela, a aplicação usa 8080 |

A aplicação não sobe se faltar alguma variável sem observação de padrão acima.

## URL JDBC do Neon

O painel do Neon mostra a conexão no formato `postgresql://usuario:senha@...`, que não funciona aqui. Use o formato JDBC, sem usuário e senha na URL:

```
jdbc:postgresql://<host>/<banco>?sslmode=require
```

Usuário e senha ficam em `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.
