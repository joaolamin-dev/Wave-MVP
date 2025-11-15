Wave — Plataforma de Streaming Musical

<a name="sumario"></a>

Sumário

Visão Geral

Tecnologias Utilizadas

Funcionalidades

Arquitetura do Projeto

Instalação e Execução

Backend

Frontend

Endpoints Principais

Contribuição

Autor

Licença

<a name="visao-geral"></a>

Visão Geral

Wave é um sistema acadêmico de streaming musical composto por backend (Spring Boot) e frontend (React + Vite). O projeto implementa autenticação segura via JWT, operações CRUD para usuários e uma UI responsiva construída com Tailwind CSS.

<a name="tecnologias-utilizadas"></a>

Tecnologias Utilizadas

Backend

Java 17

Spring Boot (versão usada no projeto)

Spring Security

JWT (com java-jwt)

PostgreSQL / H2 (desenvolvimento)

Maven

Frontend

React

Vite

Tailwind CSS

Axios

React Router

<a name="funcionalidades"></a>

Funcionalidades

Implementadas

Autenticação JWT (login seguro)

CRUD de usuários (criar, listar, atualizar, deletar)

Header dinâmico conforme estado de autenticação

Layout responsivo com Tailwind CSS

Documentação da API via OpenAPI / SpringDoc

Planejadas

Upload de músicas

Sistema de playlists

Perfis de usuário

Transmissões ao vivo

<a name="arquitetura-do-projeto"></a>

Arquitetura do Projeto
Wave-MVP/
├── backend/                 # API Spring Boot
│   ├── src/
│   │   ├── main/java/br/com/fatec/wave/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── security/
│   │   │   └── dto/
│   └── pom.xml
└── frontend/                # Aplicação React + Vite
    ├── src/
    │   ├── components/
    │   ├── pages/
    │   ├── services/
    │   └── data/
    └── package.json


<a name="instalacao-e-execucao"></a>

Instalação e Execução
Pré-requisitos

Java 17+

Node.js 18+

(Opcional) PostgreSQL para ambiente de produção

<a name="backend"></a>

Backend

Entre na pasta do backend:

cd backend


Execute com o wrapper do Maven:

./mvnw spring-boot:run


A API ficará disponível por padrão em http://localhost:8080.

Arquivo application.properties fornece configuração de H2 para desenvolvimento — preencha api.security.token.secret e credenciais de admin se necessário.

<a name="frontend"></a>

Frontend

Entre na pasta do frontend:

cd frontend


Instale dependências e rode em modo de desenvolvimento:

npm install
npm run dev


O frontend estará disponível por padrão em http://localhost:5173.

O vite.config.js já tem proxy para o backend configurado (path /api), ajuste conforme necessário.

<a name="endpoints-principais"></a>

Endpoints Principais

Autenticação

POST /login — autenticar (retorna JWT)

POST /usuarios — criar usuário

Usuários

GET /usuarios — listar todos os usuários (autenticado)

GET /usuarios/{id} — obter usuário por id

PUT /usuarios/{id} — atualizar usuário (roles: ADMIN ou USER)

DELETE /usuarios/{id} — deletar usuário (necessita role ADMIN)

<a name="contribuicao"></a>

Contribuição

Faça um fork do repositório.

Crie uma branch para sua feature: git checkout -b feature/minha-feature.

Faça commits claros e envie um pull request.

Siga o estilo do código existente e adicione testes quando pertinente.

<a name="autor"></a>

Autor

João Victor (joaolamin-dev) — desenvolvimento full-stack, integração frontend/backend e arquitetura do projeto.

<a name="licenca"></a>

Licença

Projeto desenvolvido para fins acadêmicos na FATEC Mauá. Adicione aqui a licença desejada (por exemplo, MIT) se quiser permitir contribuições externas com termos claros.
