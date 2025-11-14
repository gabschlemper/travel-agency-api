# 🧪 Exemplos de Requisições - Pronto para Usar

Este arquivo contém exemplos de requisições prontas para testar a API.

## 📝 Preparação

1. Inicie a aplicação:
```bash
./run.sh
# ou
mvn spring-boot:run
```

2. Aguarde a mensagem: "Started ApiViagemApplication"

3. Execute os comandos abaixo em outro terminal

---

## ✈️ CENÁRIO COMPLETO - Agência de Viagens

### Passo 1: Cadastrar Destinos Populares

**Destino 1 - Paris, França**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Paris",
    "localizacao": "França",
    "descricao": "A Cidade Luz, famosa pela Torre Eiffel, Louvre, Champs-Élysées e gastronomia excepcional"
  }'
```

**Destino 2 - Rio de Janeiro, Brasil**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Rio de Janeiro",
    "localizacao": "Brasil",
    "descricao": "Cidade maravilhosa com praias icônicas, Cristo Redentor e Pão de Açúcar"
  }'
```

**Destino 3 - Tóquio, Japão**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Tóquio",
    "localizacao": "Japão",
    "descricao": "Capital japonesa que mescla tradição milenar com tecnologia de ponta"
  }'
```

**Destino 4 - Nova York, EUA**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Nova York",
    "localizacao": "Estados Unidos",
    "descricao": "A cidade que nunca dorme, com Times Square, Estátua da Liberdade e Central Park"
  }'
```

**Destino 5 - Barcelona, Espanha**
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Barcelona",
    "localizacao": "Espanha",
    "descricao": "Cidade com arquitetura única de Gaudí, praias mediterrâneas e vida noturna vibrante"
  }'
```

### Passo 2: Listar Todos os Destinos

```bash
curl http://localhost:8080/api/destinos
```

**Resultado esperado:** Array com 5 destinos cadastrados

---

### Passo 3: Clientes Avaliando os Destinos

**Cliente 1 avalia Paris com nota 10**
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'
```

**Cliente 2 avalia Paris com nota 9**
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 9}'
```

**Cliente 3 avalia Paris com nota 10**
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'
```

**Média de Paris:** (10 + 9 + 10) / 3 = 9.67

**Avaliar Rio de Janeiro**
```bash
curl -X PATCH http://localhost:8080/api/destinos/2/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'

curl -X PATCH http://localhost:8080/api/destinos/2/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'

curl -X PATCH http://localhost:8080/api/destinos/2/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 9}'
```

**Média do Rio:** (10 + 10 + 9) / 3 = 9.67

**Avaliar Tóquio**
```bash
curl -X PATCH http://localhost:8080/api/destinos/3/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 8}'

curl -X PATCH http://localhost:8080/api/destinos/3/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 9}'
```

**Média de Tóquio:** (8 + 9) / 2 = 8.5

---

### Passo 4: Buscar Destino Específico

**Ver detalhes de Paris:**
```bash
curl http://localhost:8080/api/destinos/1
```

**Ver detalhes do Rio:**
```bash
curl http://localhost:8080/api/destinos/2
```

---

### Passo 5: Pesquisar Destinos

**Pesquisar por "Paris":**
```bash
curl "http://localhost:8080/api/destinos/pesquisar?termo=Paris"
```

**Pesquisar por "Brasil":**
```bash
curl "http://localhost:8080/api/destinos/pesquisar?termo=Brasil"
```

**Pesquisar por "Japão":**
```bash
curl "http://localhost:8080/api/destinos/pesquisar?termo=Japão"
```

**Pesquisar por "América" (nenhum resultado):**
```bash
curl "http://localhost:8080/api/destinos/pesquisar?termo=América"
```

---

### Passo 6: Atualizar Informações

**Atualizar descrição de Barcelona:**
```bash
curl -X PUT http://localhost:8080/api/destinos/5 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Barcelona",
    "localizacao": "Espanha - Europa",
    "descricao": "Cidade com arquitetura única de Gaudí (Sagrada Família, Park Güell), praias mediterrâneas, Las Ramblas e vida noturna vibrante"
  }'
```

---

### Passo 7: Excluir Destino

**Remover Nova York (ID 4):**
```bash
curl -X DELETE http://localhost:8080/api/destinos/4
```

**Verificar exclusão (deve retornar 404):**
```bash
curl http://localhost:8080/api/destinos/4
```

**Listar novamente (deve ter 4 destinos):**
```bash
curl http://localhost:8080/api/destinos
```

---

## 🎯 TESTES DE VALIDAÇÃO

### Teste 1: Cadastrar sem nome (deve dar erro)
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "localizacao": "Brasil"
  }'
```

**Esperado:** Erro 400 - "Nome é obrigatório"

### Teste 2: Cadastrar sem localização (deve dar erro)
```bash
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "São Paulo"
  }'
```

**Esperado:** Erro 400 - "Localização é obrigatória"

### Teste 3: Avaliar com nota inválida (>10)
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 15}'
```

**Esperado:** Erro 400 - "A nota deve estar entre 1 e 10"

### Teste 4: Avaliar com nota inválida (<1)
```bash
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 0}'
```

**Esperado:** Erro 400 - "A nota deve estar entre 1 e 10"

### Teste 5: Buscar destino inexistente
```bash
curl http://localhost:8080/api/destinos/999
```

**Esperado:** 404 Not Found

### Teste 6: Atualizar destino inexistente
```bash
curl -X PUT http://localhost:8080/api/destinos/999 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste",
    "localizacao": "Teste"
  }'
```

**Esperado:** 404 Not Found

### Teste 7: Excluir destino inexistente
```bash
curl -X DELETE http://localhost:8080/api/destinos/999
```

**Esperado:** 404 Not Found

---

## 🚀 TESTE RÁPIDO - Um Comando

Execute tudo de uma vez (Linux/Mac):

```bash
# Cadastrar 3 destinos
for i in {1..3}; do
  curl -X POST http://localhost:8080/api/destinos \
    -H "Content-Type: application/json" \
    -d "{\"nome\":\"Destino $i\",\"localizacao\":\"País $i\",\"descricao\":\"Descrição do destino $i\"}"
  echo ""
done

# Listar todos
echo "=== LISTANDO TODOS ==="
curl http://localhost:8080/api/destinos
echo ""

# Avaliar primeiro destino
echo "=== AVALIANDO DESTINO 1 ==="
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'
echo ""

# Pesquisar
echo "=== PESQUISANDO ==="
curl "http://localhost:8080/api/destinos/pesquisar?termo=Destino"
echo ""
```


## 💡 DICAS

1. **Para formatar a saída JSON**, instale `jq`:
   ```bash
    sudo apt install jq  # Ubuntu/Debian
    curl -s http://localhost:8080/api/destinos | jq
   ```

2. **Para ver headers HTTP**:
   ```bash
   curl -i http://localhost:8080/api/destinos
   ```

3. **Para ver apenas o status HTTP**:
   ```bash
   curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/destinos
   ```

4. **Para salvar resposta em arquivo**:
   ```bash
   curl http://localhost:8080/api/destinos > destinos.json
   ```
