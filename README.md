Wave - Plataforma de Streaming Musical

Wave é um sistema completo de streaming musical desenvolvido como projeto acadêmico, composto por um backend em Spring Boot e um frontend em React. A aplicação oferece autenticação segura, gerenciamento de usuários e interface moderna com layout responsivo.

Sumário

Visão Geral

Tecnologias Utilizadas

Funcionalidades

Arquitetura do Projeto

Instalação e Execução

Endpoints Principais

Contribuição

Autor

Visão Geral

O Wave foi projetado para simular uma experiência completa de streaming musical. O projeto utiliza padrões modernos de desenvolvimento, integração entre serviços e autenticação baseada em JWT, garantindo segurança e escalabilidade.

Tecnologias Utilizadas
Backend

Java 17

Spring Boot 3.5+

Spring Security

JWT (JSON Web Token)

PostgreSQL

H2 Database (ambiente de desenvolvimento)

Maven

Frontend

React 18

Vite

Tailwind CSS

Axios

React Router

Funcionalidades
Implementadas

Autenticação via JWT

Cadastro e login de usuários

CRUD completo de usuários

Header dinâmico baseado no estado de autenticação

Interface responsiva desenvolvida em Tailwind CSS

API REST documentada via SpringDoc

Previstas

Sistema de playlists

Upload e gerenciamento de músicas

Perfis personalizados

Transmissões ao vivo

Arquitetura do Projeto
Wave-MVP/
├── backend/                 API REST em Spring Boot
│   ├── controller/          Endpoints
│   ├── service/             Regras de negócio
│   ├── model/               Entidades JPA
│   ├── repository/          Acesso ao banco
│   ├── security/            Autenticação e JWT
│   └── dto/                 Objetos de transferência
│
└── frontend/                Aplicação React
    ├── components/          Componentes reutilizáveis
    ├── pages/               Páginas da aplicação
    ├── services/            Integração com API
    └── data/                Dados estáticos

Instalação e Execução
Requisitos

Java 17+

Node.js 18+

PostgreSQL (apenas para ambiente de produção)

Backend (Spring Boot)
cd backend
./mvnw spring-boot:run


API disponível em:
http://localhost:8080

Frontend (React + Vite)
cd frontend
npm install
npm run dev


Aplicação disponível em:
http://localhost:5173

Endpoints Principais
Autenticação

POST /login – Autentica usuário

POST /usuarios – Cadastra novo usuário

Usuários

GET /usuarios – Lista todos os usuários

GET /usuarios/{id} – Detalhes de um usuário

PUT /usuarios/{id} – Atualiza usuário

DELETE /usuarios/{id} – Remove usuário (somente ADMIN)

Contribuição

Contribuições são bem-vindas. Para colaborar:

Crie um fork

Crie sua feature branch

Envie seu pull request

Autor

João Victor (joaolamin-dev)
Desenvolvimento Full Stack
Arquitetura e integração dos módulos frontend e backend
