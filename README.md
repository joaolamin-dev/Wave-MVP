🌊 Wave - Sistema de Streaming Musical
https://img.shields.io/badge/Java-17-orange
https://img.shields.io/badge/Spring%2520Boot-3.5.6-brightgreen
https://img.shields.io/badge/React-18-blue
https://img.shields.io/badge/PostgreSQL-15-blue

Sistema completo de streaming musical desenvolvido como projeto acadêmico.

🎯 Funcionalidades
✅ Implementadas
Autenticação JWT - Login seguro com tokens

CRUD de Usuários - Cadastro, edição e exclusão

Header Dinâmico - Interface adaptável ao login

Design Responsivo - Interface moderna com Tailwind CSS

API REST - Backend completo com Spring Boot

🔜 Futuras Implementações
API de transmissões ao vivo

Sistema de playlists

Perfis individuais

Upload de músicas

🛠️ Tecnologias
Backend
Java 17 - Linguagem principal

Spring Boot 3.5.6 - Framework backend

Spring Security - Autenticação e autorização

JWT - Tokens de acesso

PostgreSQL - Banco de dados produção

H2 Database - Banco de dados desenvolvimento

Maven - Gerenciamento de dependências

Frontend
React 18 - Biblioteca frontend

Vite - Build tool e dev server

Tailwind CSS - Framework CSS

Axios - Cliente HTTP

React Router - Roteamento

🚀 Como Executar
Pré-requisitos
Java 17+

Node.js 18+

PostgreSQL (opcional para desenvolvimento)

Backend

cd backend
./mvnw spring-boot:run

API disponível em: http://localhost:8080

Frontend

cd frontend
npm install
npm run dev

Frontend disponível em: http://localhost:5173

📡 Endpoints da API
Autenticação
POST /login - Autenticar usuário

POST /usuarios - Criar novo usuário

Usuários
GET /usuarios - Listar usuários

GET /usuarios/{id} - Buscar usuário por ID

PUT /usuarios/{id} - Atualizar usuário

DELETE /usuarios/{id} - Excluir usuário

🗃️ Estrutura do Projeto

Wave-MVP/
├── backend/                 # API Spring Boot
│   ├── src/
│   │   └── main/java/br/com/fatec/wave/
│   │       ├── controller/     # Endpoints REST
│   │       ├── service/        # Lógica de negócio
│   │       ├── repository/     # Camada de dados
│   │       ├── model/          # Entidades JPA
│   │       ├── security/       # Configurações de segurança
│   │       └── dto/            # Objetos de transferência
│   └── pom.xml
├── frontend/                # Aplicação React
│   ├── src/
│   │   ├── components/      # Componentes reutilizáveis
│   │   ├── pages/           # Páginas da aplicação
│   │   ├── services/        # Integração com API
│   │   └── data/            # Dados estáticos
│   └── package.json
└── README.md

👥 Autores
João Victor - joaolamin-dev

Desenvolvimento full-stack

Arquitetura do sistema

Integração frontend/backend

📄 Licença
Este projeto foi desenvolvido para fins acadêmicos na FATEC Mauá.
