# 🎉 Bem-vindo à API de Agência de Viagem!

## 🚀 Início Rápido

### Opção 1: Executar com Script
```bash
./run.sh
```

### Opção 2: Executar com Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Opção 3: Executar JAR
```bash
mvn clean package
java -jar target/api-viagem-1.0.0.jar
```

---

## 📚 Documentação Disponível

| Arquivo | Descrição | Quando Usar |
|---------|-----------|-------------|
| **README.md** | Documentação completa da API | Primeiro arquivo a ler - visão geral completa |
| **EXEMPLOS.md** | Requisições prontas para copiar/colar | Testar a API rapidamente |
| **INICIO.md** | Este arquivo | Começar a usar o projeto |

---

## 🎯 Fluxo de Trabalho Recomendado

### 1️⃣ Primeira Vez Usando o Projeto

1. **Ler a documentação principal:**
   ```bash
   cat README.md
   ```

2. **Verificar se tem Java e Maven:**
   ```bash
   java -version  # Deve ser 17 ou superior
   mvn -version   # Deve estar instalado
   ```

3. **Compilar e executar:**
   ```bash
   ./run.sh
   ```

4. **Testar em outro terminal:**
   ```bash
   # Abrir novo terminal
   curl http://localhost:8080/api/destinos
   ```

### 2️⃣ Testando a API

1. **Usar exemplos prontos:**
   ```bash
   # Abrir EXEMPLOS.md e copiar comandos
   cat EXEMPLOS.md
   ```

2. **Cadastrar primeiro destino:**
   ```bash
   curl -X POST http://localhost:8080/api/destinos \
     -H "Content-Type: application/json" \
     -d '{"nome":"Paris","localizacao":"França","descricao":"Cidade Luz"}'
   ```

3. **Ver o resultado:**
   ```bash
   curl http://localhost:8080/api/destinos
   ```

### 3️⃣ Preparando para Entrega

1. **Ler instruções de entrega:**
   ```bash
   cat ENTREGA.md
   ```

2. **Inicializar Git:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit - API Agência de Viagem"
   ```

3. **Conectar ao GitHub/GitLab e enviar:**
   ```bash
   git remote add origin https://github.com/SEU_USUARIO/api-agencia-viagem.git
   git push -u origin main
   ```

---

## 🗂️ Estrutura do Projeto

```
desafio-2-dsw/
│
├── 📄 README.md              ← Documentação principal
├── 📄 EXEMPLOS.md            ← Requests prontas
├── 📄 TESTES.md              ← Guia de testes
├── 📄 ENTREGA.md             ← Como entregar
├── 📄 RESUMO.md              ← Checklist
├── 📄 INICIO.md              ← Este arquivo
├── 📄 pom.xml                ← Configuração Maven
├── 📄 .gitignore             ← Arquivos ignorados
├── 🔧 run.sh                 ← Script de execução
│
└── 📁 src/
    └── 📁 main/
        ├── 📁 java/
        │   └── 📁 com/agenciaviagem/
        │       ├── ApiViagemApplication.java     ← Classe principal
        │       ├── 📁 controller/
        │       │   └── DestinoController.java     ← Endpoints REST
        │       ├── 📁 service/
        │       │   └── DestinoService.java        ← Lógica de negócios
        │       ├── 📁 model/
        │       │   └── Destino.java               ← Modelo de dados
        │       └── 📁 dto/
        │           └── AvaliacaoRequest.java      ← DTO
        │
        └── 📁 resources/
            └── application.properties              ← Configurações
```

---

## 🎓 Endpoints da API

### Resumo Visual

```
┌─────────────────────────────────────────────────────────────┐
│                    API ENDPOINTS                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  POST    /api/destinos              → Cadastrar            │
│  GET     /api/destinos              → Listar todos         │
│  GET     /api/destinos/pesquisar    → Pesquisar            │
│  GET     /api/destinos/{id}         → Buscar por ID        │
│  PATCH   /api/destinos/{id}/avaliar → Avaliar              │
│  PUT     /api/destinos/{id}         → Atualizar            │
│  DELETE  /api/destinos/{id}         → Excluir              │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Exemplo de Uso Completo

```bash
# 1. Cadastrar
curl -X POST http://localhost:8080/api/destinos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Paris","localizacao":"França","descricao":"Cidade Luz"}'

# 2. Listar
curl http://localhost:8080/api/destinos

# 3. Pesquisar
curl "http://localhost:8080/api/destinos/pesquisar?termo=Paris"

# 4. Buscar
curl http://localhost:8080/api/destinos/1

# 5. Avaliar
curl -X PATCH http://localhost:8080/api/destinos/1/avaliar \
  -H "Content-Type: application/json" \
  -d '{"nota": 10}'

# 6. Atualizar
curl -X PUT http://localhost:8080/api/destinos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Paris","localizacao":"França - Europa","descricao":"..."}'

# 7. Excluir
curl -X DELETE http://localhost:8080/api/destinos/1
```

---

## 🛠️ Requisitos do Sistema

### Obrigatórios:
- ✅ Java 17 ou superior
- ✅ Maven 3.6 ou superior

### Opcionais (para testes):
- 🔧 curl (linha de comando)
- 🔧 Postman ou Insomnia (interface gráfica)
- 🔧 jq (formatar JSON no terminal)

### Instalar Requisitos (Ubuntu/Debian):
```bash
# Java
sudo apt update
sudo apt install openjdk-17-jdk

# Maven
sudo apt install maven

# Ferramentas opcionais
sudo apt install curl jq
```

---

## ❓ FAQ - Perguntas Frequentes

### 1. Como sei se a aplicação está rodando?

```bash
curl http://localhost:8080/api/destinos
```

Se retornar `[]` (array vazio) ou lista de destinos, está rodando!

### 2. Porta 8080 já está em uso, como mudar?

Edite `src/main/resources/application.properties`:
```properties
server.port=8081
```

### 3. Como parar a aplicação?

No terminal onde está rodando, pressione: `Ctrl + C`

### 4. Como limpar os dados?

Reinicie a aplicação (dados são armazenados em memória).

### 5. Posso usar outro IDE além do VS Code?

Sim! Funciona em:
- IntelliJ IDEA
- Eclipse
- NetBeans
- Qualquer editor de texto + terminal

### 6. Como importar no IntelliJ?

1. File → Open
2. Selecione a pasta do projeto
3. IntelliJ detectará automaticamente o pom.xml
4. Aguarde o Maven baixar dependências
5. Run → Run 'ApiViagemApplication'

### 7. Preciso de banco de dados?

Não! Conforme especificado, usa armazenamento em memória.

---

## 🎯 Checklist Antes de Entregar

- [ ] Código compila sem erros
- [ ] Aplicação inicia sem erros
- [ ] Todos os 7 endpoints funcionam
- [ ] Testes básicos realizados
- [ ] README.md revisado
- [ ] Git configurado
- [ ] Repositório criado no GitHub/GitLab
- [ ] Código enviado para o repositório
- [ ] Link do repositório copiado
- [ ] Link enviado no AVA

---

## 🆘 Problemas Comuns

### Erro: "JAVA_HOME not set"
```bash
# Descobrir onde está o Java
which java

# Configurar JAVA_HOME (adicionar ao ~/.bashrc)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### Erro: "mvn command not found"
```bash
# Instalar Maven
sudo apt install maven
```

### Erro: "Port 8080 already in use"
```bash
# Descobrir o que está usando a porta
sudo lsof -i :8080

# Matar o processo
sudo kill -9 PID

# Ou mudar a porta (ver FAQ #2)
```

### Erro ao compilar: "cannot find symbol"
```bash
# Limpar e recompilar
mvn clean
mvn install
```

---

## 📞 Recursos Adicionais

### Documentação Oficial:
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### Ferramentas de Teste:
- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- [curl](https://curl.se/)

---

## ✨ Próximos Passos

1. ✅ Executar a aplicação
2. ✅ Testar todos os endpoints
3. ✅ Ler toda a documentação
4. ✅ Enviar para o Git
5. ✅ Submeter no AVA

---

## 🎉 Pronto!

Você tem tudo que precisa para:
- ✅ Executar a API
- ✅ Testar todas as funcionalidades
- ✅ Entender o código
- ✅ Entregar o projeto

**Boa sorte com o projeto! 🚀**

---

*Desenvolvido para o Desafio 2 - Desenvolvimento de Aplicações Web*
