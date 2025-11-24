#!/bin/bash

echo "======================================"
echo "🧪 Teste Completo PostgreSQL + JWT"
echo "======================================"
echo ""

# 1. Teste endpoint público
echo "1️⃣ Testando endpoint público..."
curl -s http://localhost:8080/api/destinos
echo ""
echo ""

# 2. Login
echo "2️⃣ Fazendo login..."
RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@test.com","senha":"admin123"}')

TOKEN=$(echo $RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "❌ Erro: Token não obtido"
    exit 1
fi

echo "✅ Token obtido: ${TOKEN:0:20}..."
echo ""

# 3. Criar destino
echo "3️⃣ Criando destino como ADMIN..."
DESTINO_RESPONSE=$(curl -s -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"nome":"Tokyo","localizacao":"Japão","descricao":"Capital tecnológica do Japão"}')

echo "$DESTINO_RESPONSE"
echo ""

# 4. Listar destinos
echo "4️⃣ Listando destinos após criação..."
curl -s http://localhost:8080/api/destinos | python3 -m json.tool || curl -s http://localhost:8080/api/destinos
echo ""
echo ""

# 5. Verificar no banco
echo "5️⃣ Verificando diretamente no PostgreSQL..."
sudo -u postgres psql agencia_viagem -c "SELECT id, nome, localizacao FROM destinos;"
echo ""

echo "======================================"
echo "✅ Teste Completo!"
echo "======================================"
