# API de Agência de Viagem 🌍✈️

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de destinos de viagem.

## 📋 Descrição

Esta API foi desenvolvida para auxiliar clientes a planejar suas viagens, permitindo o gerenciamento completo de destinos turísticos. A aplicação oferece funcionalidades de cadastro, consulta, pesquisa, avaliação e exclusão de destinos.

## 🚀 Tecnologias Utilizadas

- **Java 17+** (testado com Java 21)
- **Spring Boot 3.2.0**
- **Maven**
- **Lombok**
- **Spring Boot Validation**
- **Spring Boot Web**

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── agenciaviagem/
│   │           ├── ApiViagemApplication.java
│   │           ├── controller/
│   │           │   └── DestinoController.java
│   │           ├── service/
│   │           │   └── DestinoService.java
│   │           ├── model/
│   │           │   └── Destino.java
│   │           └── dto/
│   │               └── AvaliacaoRequest.java
│   └── resources/
│       └── application.properties
└── pom.xml
```

## 🔧 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

### Passos para executar

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd desafio-2-dsw
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Endpoints da API

### 1. Cadastrar Destino
**POST** `/api/destinos`

Cadastra um novo destino de viagem.

**Request Body:**
```json
{
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus",
  "avaliacaoMedia": 0.0,
  "totalAvaliacoes": 0
}
```

### 2. Listar Todos os Destinos
**GET** `/api/destinos`

Retorna a lista de todos os destinos cadastrados.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Paris",
    "localizacao": "França",
    "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus",
    "avaliacaoMedia": 8.5,
    "totalAvaliacoes": 10
  }
]
```

### 3. Pesquisar Destinos
**GET** `/api/destinos/pesquisar?termo={termo}`

Pesquisa destinos por nome ou localização.

**Parâmetros:**
- `termo` (query parameter): Termo de pesquisa

**Exemplo:** `/api/destinos/pesquisar?termo=paris`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Paris",
    "localizacao": "França",
    "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus",
    "avaliacaoMedia": 8.5,
    "totalAvaliacoes": 10
  }
]
```

### 4. Buscar Destino por ID
**GET** `/api/destinos/{id}`

Retorna informações detalhadas de um destino específico.

**Parâmetros:**
- `id` (path parameter): ID do destino

**Exemplo:** `/api/destinos/1`

**Response:** `200 OK` ou `404 Not Found`
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus",
  "avaliacaoMedia": 8.5,
  "totalAvaliacoes": 10
}
```

### 5. Avaliar Destino
**PATCH** `/api/destinos/{id}/avaliar`

Adiciona uma avaliação ao destino e recalcula a média.

**Parâmetros:**
- `id` (path parameter): ID do destino

**Request Body:**
```json
{
  "nota": 9
}
```

**Nota:** A nota deve estar entre 1 e 10.

**Response:** `200 OK` ou `400 Bad Request`
```json
{
  "id": 1,
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "A Cidade Luz, famosa pela Torre Eiffel e museus",
  "avaliacaoMedia": 8.6,
  "totalAvaliacoes": 11
}
```

### 6. Atualizar Destino
**PUT** `/api/destinos/{id}`

Atualiza as informações de um destino existente.

**Parâmetros:**
- `id` (path parameter): ID do destino

**Request Body:**
```json
{
  "nome": "Paris",
  "localizacao": "França - Europa",
  "descricao": "A Cidade Luz, famosa pela Torre Eiffel, Louvre e gastronomia"
}
```

**Response:** `200 OK` ou `404 Not Found`

### 7. Excluir Destino
**DELETE** `/api/destinos/{id}`

Exclui um destino do sistema.

**Parâmetros:**
- `id` (path parameter): ID do destino

**Response:** `204 No Content` ou `404 Not Found`

## 🧪 Testando a API

### Usando cURL

**Cadastrar destino:**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rio de Janeiro",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa com praias famosas"
  }'
```

**Listar destinos:**
```bash
curl http://localhost:8080/api/destinos
```

**Pesquisar destinos:**
```bash
curl "http://localhost:8080/api/destinos/pesquisar?termo=Rio"
```

**Buscar por ID:**
```bash
curl http://localhost:8080/api/destinos/1
```

**Avaliar destino:**
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'
```

**Atualizar destino:**
```bash
curl -X PUT http://localhost:8080/api/destinos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rio de Janeiro",
    "localizacao": "Brasil - América do Sul",
    "descricao": "Cidade maravilhosa com praias famosas e Cristo Redentor"
  }'
```

**Excluir destino:**
```bash
curl -X DELETE http://localhost:8080/api/destinos/1
```

## 📊 Modelo de Dados

### Destino
```java
{
  "id": Long,              // Gerado automaticamente
  "nome": String,          // Obrigatório
  "localizacao": String,   // Obrigatório
  "descricao": String,     // Opcional
  "avaliacaoMedia": Double,     // Calculado automaticamente
  "totalAvaliacoes": Integer    // Calculado automaticamente
}
```

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.
