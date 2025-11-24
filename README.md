# 🌍 API de Agência de Viagem - Desafio 3

API REST completa desenvolvida com Spring Boot para gerenciamento de destinos de viagem, com **autenticação JWT** e **integração com PostgreSQL**.

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Auth-red)](https://jwt.io/)

---

## 📋 Índice

- [Requisitos](#-requisitos)
- [Configuração do Banco de Dados](#-configuração-do-banco-de-dados)
- [Como Executar](#-como-executar)
- [Autenticação e Segurança](#-autenticação-e-segurança)
- [Endpoints da API](#-endpoints-da-api)
- [Credenciais de Teste](#-credenciais-de-teste)
- [Tecnologias](#-tecnologias)
- [Troubleshooting](#-troubleshooting)

---

## 🔧 Requisitos

- **Java 21** ou superior
- **Maven 3.8+**
- **PostgreSQL 12+**
- **Git**

### Verificar Instalações

```bash
java -version   # Deve mostrar Java 21
mvn -version    # Maven 3.8+
psql --version  # PostgreSQL
```

---

## 🗄️ Configuração do Banco de Dados

### 1. Instalar PostgreSQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

**macOS:**
```bash
brew install postgresql@16
brew services start postgresql@16
```

**Windows:**
Baixar instalador em: https://www.postgresql.org/download/windows/

### 2. Criar Banco de Dados

```bash
# Acessar PostgreSQL como superusuário
sudo -u postgres psql

# Dentro do psql, executar:
CREATE DATABASE agencia_viagem;
CREATE USER agencia_user WITH PASSWORD 'agencia123';
GRANT ALL PRIVILEGES ON DATABASE agencia_viagem TO agencia_user;
\q
```

### 3. Verificar Configuração

Edite `src/main/resources/application.properties` se necessário:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/agencia_viagem
spring.datasource.username=agencia_user
spring.datasource.password=agencia123

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=YXZpc28tc2VjcmV0by1qdHctcGFyYS1hdXRlbnRpY2FjYW8tZGEtYXBpLWRlLWFnZW5jaWEtZGUtdmlhZ2Vu
jwt.expiration=86400000
```

---

## 🚀 Como Executar

### Método 1: Maven Wrapper (Recomendado)

```bash
# Clonar o repositório
git clone https://github.com/gabschlemper/travel-agency-api.git
cd travel-agency-api

# Compilar e executar
./mvnw clean install
./mvnw spring-boot:run
```

### Método 2: Maven Instalado

```bash
mvn clean install
mvn spring-boot:run
```

### Método 3: JAR Executável

```bash
mvn clean package
java -jar target/agencia-viagem-api-1.0.0.jar
```

A API estará disponível em: **http://localhost:8080**

---

## 🔒 Autenticação e Segurança

### Sistema de Autenticação JWT

A API utiliza **JSON Web Tokens (JWT)** para autenticação stateless.

### Perfis de Acesso

| Perfil | Permissões |
|--------|------------|
| **ADMIN** | Criar, ler, atualizar, excluir destinos + avaliar |
| **USER** | Ler destinos + avaliar apenas |

### Fluxo de Autenticação

1. **Registrar Usuário**
```bash
POST /api/auth/registro
Content-Type: application/json

{
  "nome": "Admin User",
  "email": "admin@agencia.com",
  "senha": "admin123",
  "perfil": "ADMIN"
}
```

2. **Fazer Login**
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@agencia.com",
  "senha": "admin123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "admin@agencia.com",
  "nome": "Admin User",
  "perfil": "ADMIN"
}
```

3. **Usar Token nas Requisições**
```bash
GET /api/destinos
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 📡 Endpoints da API

### 🔓 Públicos (Sem Autenticação)

#### Registrar Novo Usuário
```http
POST /api/auth/registro
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123",
  "perfil": "USER"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "senha123"
}
```

#### Listar Todos os Destinos
```http
GET /api/destinos
```

#### Pesquisar Destinos
```http
GET /api/destinos/pesquisar?termo=Paris
```

#### Buscar Destino por ID
```http
GET /api/destinos/1
```

---

### 🔐 Protegidos (Requerem Autenticação)

#### Cadastrar Destino (ADMIN apenas)
```http
POST /api/destinos
Authorization: Bearer {seu-token-jwt}
Content-Type: application/json

{
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade Luz",
  "avaliacao": 0.0
}
```

#### Atualizar Destino (ADMIN apenas)
```http
PUT /api/destinos/1
Authorization: Bearer {seu-token-jwt}
Content-Type: application/json

{
  "nome": "Paris",
  "localizacao": "França - Europa",
  "descricao": "A Cidade Luz, capital da França",
  "avaliacao": 4.8
}
```

#### Excluir Destino (ADMIN apenas)
```http
DELETE /api/destinos/1
Authorization: Bearer {seu-token-jwt}
```

#### Avaliar Destino (ADMIN ou USER)
```http
PATCH /api/destinos/1/avaliar?nota=4.5
Authorization: Bearer {seu-token-jwt}
```

---

## 🔑 Credenciais de Teste

### Usuário ADMIN
```json
{
  "email": "admin@agencia.com",
  "senha": "admin123",
  "perfil": "ADMIN"
}
```
**Pode:** Criar, editar, excluir e avaliar destinos

### Usuário Comum
```json
{
  "email": "user@agencia.com",
  "senha": "user123",
  "perfil": "USER"
}
```
**Pode:** Visualizar e avaliar destinos apenas

---

## 🧪 Testando a API

### ⚡ Teste Automático Completo

Execute o script de teste que valida PostgreSQL + JWT:

```bash
./teste_postgres.sh
```

Este script testa automaticamente:
- ✅ Conexão com PostgreSQL
- ✅ Criação de tabelas
- ✅ Registro de usuário
- ✅ Login e geração de token JWT
- ✅ Criação de destino via API
- ✅ Persistência no banco de dados
- ✅ Autorização por perfil

**Resultado esperado:**
```
======================================
🧪 Teste Completo PostgreSQL + JWT
======================================

1️⃣ Testando endpoint público...
[]

2️⃣ Fazendo login...
✅ Token obtido: eyJhbGciOiJIUzI1NiJ9...

3️⃣ Criando destino como ADMIN...
{"id":1,"nome":"Tokyo","localizacao":"Japão",...}

4️⃣ Listando destinos após criação...
[{"id":1,"nome":"Tokyo",...}]

5️⃣ Verificando diretamente no PostgreSQL...
 id | nome  | localizacao 
----+-------+-------------
  1 | Tokyo | Japão

======================================
✅ Teste Completo!
======================================
```

---

### 🔧 Testes Manuais Passo a Passo

#### 1. Cadastrar Usuário ADMIN
```bash
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Admin Teste",
    "email": "admin@test.com",
    "senha": "admin123",
    "perfil": "ADMIN"
  }'
```

**Resposta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "admin@test.com",
  "nome": "Admin Teste",
  "perfil": "ADMIN"
}
```

#### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "senha": "admin123"
  }'
```

**📋 Copie o token retornado!** Você precisará dele nos próximos passos.

#### 3. Criar Destino (ADMIN - use o token)
```bash
# Salve o token em uma variável
TOKEN="cole_seu_token_aqui"

# Crie um destino
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "nome": "Paris",
    "localizacao": "França",
    "descricao": "Cidade Luz com a Torre Eiffel"
  }'
```

**Resposta esperada:**
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade Luz com a Torre Eiffel",
  "avaliacaoMedia": 0.0,
  "totalAvaliacoes": 0
}
```

#### 4. Listar Destinos (público - sem token)
```bash
curl http://localhost:8080/api/destinos
```

**Resposta esperada:**
```json
[
  {
    "id": 1,
    "nome": "Paris",
    "localizacao": "França",
    "descricao": "Cidade Luz com a Torre Eiffel",
    "avaliacaoMedia": 0.0,
    "totalAvaliacoes": 0
  }
]
```

#### 5. Avaliar Destino (autenticado)
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "avaliacao": 4.5
  }'
```

**Resposta esperada:**
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade Luz com a Torre Eiffel",
  "avaliacaoMedia": 4.5,
  "totalAvaliacoes": 1
}
```

#### 6. Verificar no PostgreSQL
```bash
# Ver todos os destinos
sudo -u postgres psql agencia_viagem -c "SELECT * FROM destinos;"

# Ver todos os usuários
sudo -u postgres psql agencia_viagem -c "SELECT id, nome, email, perfil FROM usuarios;"
```

---

### 🎯 Teste de Autorização

#### Testar USER (não pode criar destinos)

1. **Registrar usuário comum:**
```bash
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "User Comum",
    "email": "user@test.com",
    "senha": "user123",
    "perfil": "USER"
  }'
```

2. **Fazer login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","senha":"user123"}'
```

3. **Tentar criar destino (DEVE FALHAR com 403):**
```bash
USER_TOKEN="cole_token_do_user"

curl -v -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{
    "nome": "Teste",
    "localizacao": "Teste",
    "descricao": "Não deve funcionar"
  }'
```

**Resposta esperada:** `HTTP/1.1 403 Forbidden` ✅

4. **Avaliar destino (DEVE FUNCIONAR):**
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $USER_TOKEN" \
  -d '{"avaliacao": 5.0}'
```

**Resposta esperada:** `HTTP/1.1 200 OK` ✅

---

### 📊 Matriz de Testes

| Operação | Público | USER | ADMIN | Código Esperado |
|----------|---------|------|-------|-----------------|
| GET /api/destinos | ✅ | ✅ | ✅ | 200 |
| GET /api/destinos/{id} | ✅ | ✅ | ✅ | 200 |
| POST /api/destinos | ❌ | ❌ | ✅ | 401/403/201 |
| PUT /api/destinos/{id} | ❌ | ❌ | ✅ | 401/403/200 |
| DELETE /api/destinos/{id} | ❌ | ❌ | ✅ | 401/403/204 |
| PATCH /api/destinos/{id}/avaliar | ❌ | ✅ | ✅ | 401/200 |
| POST /api/auth/registro | ✅ | ✅ | ✅ | 200 |
| POST /api/auth/login | ✅ | ✅ | ✅ | 200 |

---

## 🛠️ Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Hibernate** - ORM (Object-Relational Mapping)

### Segurança
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **BCrypt** - Hash de senhas
- **JJWT 0.11.5** - Biblioteca JWT

### Banco de Dados
- **PostgreSQL** - Banco relacional
- **HikariCP** - Connection pool

### Build & Deploy
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate

---

## 🐛 Troubleshooting

### Erro: "Connection refused" ao conectar PostgreSQL

**Problema:** PostgreSQL não está rodando

**Solução:**
```bash
# Linusudo systemctl start postgresql
sudo systemctl status postgresql

# macOS
brew services start postgresql@16
```

### Erro: "FATAL: database does not exist"

**Problema:** Banco de dados não foi criado

**Solução:**
```bash
sudo -u postgres psql
CREATE DATABASE agencia_viagem;
\q
```

### Erro: "org.postgresql.util.PSQLException: password authentication failed"

**Problema:** Credenciais incorretas em `application.properties`

**Solução:** Verifique usuário e senha no arquivo de configuração

### Erro: "JWT signature does not match"

**Problema:** Secret key do JWT mudou ou token expirado

**Solução:** Faça login novamente para obter novo token

### Token JWT expirou

**Problema:** Token válido por 24 horas

**Solução:** Fazer novo login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "seu@email.com", "senha": "suaSenha"}'
```

### Acesso negado (403 Forbidden)

**Problema:** Usuário não tem permissão para a operação

**Soluções:**
- Usuário USER tentando criar/editar/excluir → Use conta ADMIN
- Token ausente → Adicione header `Authorization: Bearer {token}`
- Token inválido → Faça novo login

### Porta 8080 já em uso

**Problema:** Outra aplicação usando a porta

**Solução:** Alterar porta em `application.properties`:
```properties
server.port=8081
```

---

## 📦 Estrutura do Projeto

```
src/main/java/com/agencia/
├── controller/
│   ├── AuthController.java      # Login, registro
│   └── DestinoController.java   # CRUD destinos
├── dto/
│   ├── AuthResponse.java
│   ├── LoginRequest.java
│   └── RegistroRequest.java
├── model/
│   ├── Destino.java             # Entidade JPA
│   ├── Usuario.java             # Entidade JPA
│   └── Perfil.java              # Enum (ADMIN/USER)
├── repository/
│   ├── DestinoRepository.java   # JPA Repository
│   └── UsuarioRepository.java   # JPA Repository
├── security/
│   ├── JwtService.java          # Geração/validação JWT
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java      # Configuração Spring Security
└── service/
    ├── DestinoService.java      # Lógica de negócio
    └── UsuarioService.java      # UserDetailsService
```

---

## ✅ Checklist de Funcionalidades

### Desafio 3 - Banco de Dados
- [x] Integração com PostgreSQL
- [x] Entidades JPA (Destino, Usuario)
- [x] Repositories com Spring Data JPA
- [x] Persistência automática com Hibernate
- [x] Queries customizadas

### Desafio 3 - Segurança
- [x] Autenticação JWT
- [x] Spring Security configurado
- [x] Senhas criptografadas (BCrypt)
- [x] Autorização por perfil (ADMIN/USER)
- [x] Endpoints protegidos
- [x] Registro de novos usuários
- [x] Login com geração de token

### Funcionalidades da API
- [x] CRUD completo de destinos
- [x] Pesquisa de destinos
- [x] Avaliação de destinos
- [x] Validação de dados
- [x] Tratamento de erros

---

## 📝 Licença

Este projeto foi desenvolvido como parte do **Desafio 3** da disciplina de Desenvolvimento de Software para Web.

---

## 👥 Autor

**Gabriela Silva**  
Disciplina: Desenvolvimento de Software para Web  
Instituição: [Sua Universidade]

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique se PostgreSQL está rodando
2. Confirme que o banco de dados foi criado
3. Valide as credenciais em `application.properties`
4. Certifique-se de usar Java 21
5. Veja a seção [Troubleshooting](#-troubleshooting)

**Logs da aplicação:** Os erros aparecem no console onde você executou `mvn spring-boot:run`

---

🎉 **Projeto pronto para uso!** Siga os passos de [Como Executar](#-como-executar) e bons testes!
