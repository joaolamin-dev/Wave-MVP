# Wave --- Plataforma de Streaming Musical

## Sumário

-   [Visão Geral](#visão-geral)
-   [Tecnologias](#tecnologias)
-   [Funcionalidades](#funcionalidades)
-   [Arquitetura](#arquitetura)
-   [Instalação](#instalação)
    -   [Backend](#backend)
    -   [Frontend](#frontend)
-   [Endpoints](#endpoints)
-   [Autor](#autor)

## Visão Geral

Wave é um sistema completo de streaming musical, desenvolvido como
projeto acadêmico. A aplicação combina um backend em Spring Boot com um
frontend em React, oferecendo autenticação JWT, CRUD de usuários e
interface moderna responsiva.

## Tecnologias

### Backend

-   Java 17
-   Spring Boot
-   Spring Security
-   JWT (Auth0)
-   PostgreSQL / H2
-   Maven

### Frontend

-   React
-   Vite
-   Tailwind CSS
-   Axios
-   React Router

## Funcionalidades

-   Autenticação via JWT
-   Cadastro e login
-   CRUD de usuários
-   Header dinâmico
-   Interface responsiva

## Arquitetura

    Wave/
    ├── backend/          # API em Spring Boot
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── model/
    │   ├── security/
    │   └── dto/
    └── frontend/         # Interface em React
        ├── components/
        ├── pages/
        ├── services/
        └── data/

## Instalação

### Backend

``` bash
cd backend
./mvnw spring-boot:run
```

API em: http://localhost:8080

### Frontend

``` bash
cd frontend
npm install
npm run dev
```

Interface em: http://localhost:5173

## Endpoints

### Autenticação

-   POST /login\
-   POST /usuarios

### Usuários

-   GET /usuarios\
-   GET /usuarios/{id}\
-   PUT /usuarios/{id}\
-   DELETE /usuarios/{id}

## Autor

João Victor (joaolamin-dev)

