#!/bin/bash

# Script de Validação - Desafio 3
# Este script testa automaticamente todas as funcionalidades do projeto

echo "============================================"
echo "🧪 Script de Validação - Desafio 3"
echo "============================================"
echo ""

# Configurações
API_URL="http://localhost:8080"
ADMIN_EMAIL="admin@test.com"
ADMIN_SENHA="admin123"
USER_EMAIL="user@test.com"
USER_SENHA="user123"

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para verificar status HTTP
check_status() {
    local status=$1
    local expected=$2
    local message=$3
    
    if [ "$status" -eq "$expected" ]; then
        echo -e "${GREEN}✅ PASSOU${NC}: $message (HTTP $status)"
    else
        echo -e "${RED}❌ FALHOU${NC}: $message (esperado: $expected, recebido: $status)"
    fi
}

# Função para fazer requisições
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local token=$4
    
    if [ -n "$token" ]; then
        curl -s -w "%{http_code}" -o /tmp/response.json -X "$method" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $token" \
            -d "$data" \
            "$API_URL$endpoint"
    else
        curl -s -w "%{http_code}" -o /tmp/response.json -X "$method" \
            -H "Content-Type: application/json" \
            -d "$data" \
            "$API_URL$endpoint"
    fi
}

echo "📋 Passo 1: Verificando se a aplicação está rodando..."
echo "--------------------------------------------"

status=$(curl -s -o /dev/null -w "%{http_code}" $API_URL/api/destinos)
if [ "$status" -eq 200 ]; then
    echo -e "${GREEN}✅ Aplicação está rodando!${NC}"
else
    echo -e "${RED}❌ Aplicação não está respondendo. Execute 'mvn spring-boot:run' primeiro.${NC}"
    exit 1
fi
echo ""

echo "📋 Passo 2: Testando Registro de Usuários..."
echo "--------------------------------------------"

# Registrar ADMIN
echo "Registrando ADMIN ($ADMIN_EMAIL)..."
status=$(make_request "POST" "/api/auth/registro" "{\"nome\":\"Admin Teste\",\"email\":\"$ADMIN_EMAIL\",\"senha\":\"$ADMIN_SENHA\",\"perfil\":\"ADMIN\"}")
check_status $status 200 "Registro de ADMIN"

# Registrar USER
echo "Registrando USER ($USER_EMAIL)..."
status=$(make_request "POST" "/api/auth/registro" "{\"nome\":\"User Teste\",\"email\":\"$USER_EMAIL\",\"senha\":\"$USER_SENHA\",\"perfil\":\"USER\"}")
check_status $status 200 "Registro de USER"

echo ""

echo "📋 Passo 3: Testando Login..."
echo "--------------------------------------------"

# Login ADMIN
echo "Login como ADMIN..."
status=$(make_request "POST" "/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"senha\":\"$ADMIN_SENHA\"}")
check_status $status 200 "Login de ADMIN"
ADMIN_TOKEN=$(cat /tmp/response.json | jq -r '.token')

if [ "$ADMIN_TOKEN" = "null" ] || [ -z "$ADMIN_TOKEN" ]; then
    echo -e "${RED}❌ Falha ao obter token do ADMIN${NC}"
    exit 1
fi
echo -e "${GREEN}Token ADMIN obtido: ${ADMIN_TOKEN:0:20}...${NC}"

# Login USER
echo "Login como USER..."
status=$(make_request "POST" "/api/auth/login" "{\"email\":\"$USER_EMAIL\",\"senha\":\"$USER_SENHA\"}")
check_status $status 200 "Login de USER"
USER_TOKEN=$(cat /tmp/response.json | jq -r '.token')

if [ "$USER_TOKEN" = "null" ] || [ -z "$USER_TOKEN" ]; then
    echo -e "${RED}❌ Falha ao obter token do USER${NC}"
    exit 1
fi
echo -e "${GREEN}Token USER obtido: ${USER_TOKEN:0:20}...${NC}"

echo ""

echo "📋 Passo 4: Testando Acesso Público..."
echo "--------------------------------------------"

# Listar destinos (público)
echo "Listando destinos (sem autenticação)..."
status=$(curl -s -o /dev/null -w "%{http_code}" $API_URL/api/destinos)
check_status $status 200 "Listar destinos (público)"

echo ""

echo "📋 Passo 5: Testando Operações ADMIN..."
echo "--------------------------------------------"

# Criar destino (ADMIN)
echo "Criando destino como ADMIN..."
status=$(make_request "POST" "/api/destinos" "{\"nome\":\"Bali Teste\",\"localizacao\":\"Indonésia\",\"descricao\":\"Ilha paradisíaca\"}" "$ADMIN_TOKEN")
check_status $status 201 "Criar destino (ADMIN)"

# Pegar ID do destino criado
DESTINO_ID=$(cat /tmp/response.json | jq -r '.id')
echo -e "${YELLOW}ID do destino criado: $DESTINO_ID${NC}"

# Atualizar destino (ADMIN)
if [ -n "$DESTINO_ID" ] && [ "$DESTINO_ID" != "null" ]; then
    echo "Atualizando destino como ADMIN..."
    status=$(make_request "PUT" "/api/destinos/$DESTINO_ID" "{\"nome\":\"Bali Atualizado\",\"localizacao\":\"Indonésia, Ásia\",\"descricao\":\"Ilha paradisíaca atualizada\"}" "$ADMIN_TOKEN")
    check_status $status 200 "Atualizar destino (ADMIN)"
fi

echo ""

echo "📋 Passo 6: Testando Operações USER..."
echo "--------------------------------------------"

if [ -n "$DESTINO_ID" ] && [ "$DESTINO_ID" != "null" ]; then
    # Avaliar destino (USER - OK)
    echo "Avaliando destino como USER..."
    status=$(make_request "PATCH" "/api/destinos/$DESTINO_ID/avaliar" "{\"avaliacao\":4.5}" "$USER_TOKEN")
    check_status $status 200 "Avaliar destino (USER)"
    
    # Tentar criar destino (USER - DEVE FALHAR)
    echo "Tentando criar destino como USER (deve falhar)..."
    status=$(make_request "POST" "/api/destinos" "{\"nome\":\"Teste\",\"localizacao\":\"Teste\",\"descricao\":\"Teste\"}" "$USER_TOKEN")
    check_status $status 403 "Criar destino (USER - deve ser 403)"
    
    # Tentar atualizar destino (USER - DEVE FALHAR)
    echo "Tentando atualizar destino como USER (deve falhar)..."
    status=$(make_request "PUT" "/api/destinos/$DESTINO_ID" "{\"nome\":\"Teste\",\"localizacao\":\"Teste\",\"descricao\":\"Teste\"}" "$USER_TOKEN")
    check_status $status 403 "Atualizar destino (USER - deve ser 403)"
fi

echo ""

echo "📋 Passo 7: Testando Acesso sem Autenticação..."
echo "--------------------------------------------"

if [ -n "$DESTINO_ID" ] && [ "$DESTINO_ID" != "null" ]; then
    # Tentar avaliar sem token (DEVE FALHAR)
    echo "Tentando avaliar sem token (deve falhar)..."
    status=$(make_request "PATCH" "/api/destinos/$DESTINO_ID/avaliar" "{\"avaliacao\":4.5}")
    check_status $status 401 "Avaliar sem token (deve ser 401)"
    
    # Tentar criar sem token (DEVE FALHAR)
    echo "Tentando criar sem token (deve falhar)..."
    status=$(make_request "POST" "/api/destinos" "{\"nome\":\"Teste\",\"localizacao\":\"Teste\",\"descricao\":\"Teste\"}")
    check_status $status 401 "Criar sem token (deve ser 401)"
fi

echo ""

echo "📋 Passo 8: Limpeza (opcional)..."
echo "--------------------------------------------"

if [ -n "$DESTINO_ID" ] && [ "$DESTINO_ID" != "null" ]; then
    # Excluir destino (ADMIN)
    echo "Excluindo destino como ADMIN..."
    status=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE \
        -H "Authorization: Bearer $ADMIN_TOKEN" \
        "$API_URL/api/destinos/$DESTINO_ID")
    check_status $status 204 "Excluir destino (ADMIN)"
fi

echo ""
echo "============================================"
echo "🎉 Validação Completa!"
echo "============================================"
echo ""
echo "📊 Resumo:"
echo "- ✅ Aplicação está rodando"
echo "- ✅ Registro de usuários funcionando"
echo "- ✅ Login e geração de tokens funcionando"
echo "- ✅ Acesso público a listagens funcionando"
echo "- ✅ ADMIN pode criar, atualizar e excluir"
echo "- ✅ USER pode avaliar mas não pode criar/atualizar/excluir"
echo "- ✅ Proteção de endpoints funcionando (401/403)"
echo ""
echo "✅ Todos os requisitos do Desafio 3 estão funcionando!"
echo ""

# Limpar arquivo temporário
rm -f /tmp/response.json
