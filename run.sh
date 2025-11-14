#!/bin/bash

# Script para executar a API de Agência de Viagem
# Uso: ./run.sh

echo "🚀 Iniciando API de Agência de Viagem..."
echo ""

# Verificar se Maven está instalado
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven não encontrado. Por favor, instale o Maven primeiro."
    echo "   Ubuntu/Debian: sudo apt install maven"
    echo "   Fedora: sudo dnf install maven"
    exit 1
fi

# Verificar se Java está instalado
if ! command -v java &> /dev/null
then
    echo "❌ Java não encontrado. Por favor, instale o Java 17 ou superior."
    echo "   Ubuntu/Debian: sudo apt install openjdk-21-jdk"
    exit 1
fi

echo "✅ Maven encontrado: $(mvn -version | head -1)"
echo "✅ Java encontrado: $(java -version 2>&1 | head -1)"
echo ""

# Compilar o projeto
echo "📦 Compilando o projeto..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Compilação concluída com sucesso!"
    echo ""
    echo "🚀 Iniciando a aplicação..."
    echo "📡 A API estará disponível em: http://localhost:8080"
    echo ""
    echo "📚 Endpoints disponíveis:"
    echo "   POST   /api/destinos              - Cadastrar destino"
    echo "   GET    /api/destinos              - Listar todos"
    echo "   GET    /api/destinos/pesquisar    - Pesquisar destinos"
    echo "   GET    /api/destinos/{id}         - Buscar por ID"
    echo "   PATCH  /api/destinos/{id}/avaliar - Avaliar destino"
    echo "   PUT    /api/destinos/{id}         - Atualizar destino"
    echo "   DELETE /api/destinos/{id}         - Excluir destino"
    echo ""
    echo "🛑 Para parar: Ctrl+C"
    echo ""
    echo "----------------------------------------"
    echo ""
    
    # Executar a aplicação
    mvn spring-boot:run
else
    echo ""
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi
