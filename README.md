# CP03-MS-Livrarias

Microsserviço REST para gerenciamento de **livrarias** e **livros**, desenvolvido com **Java 17 + Spring Boot**, persistência em **Microsoft SQL Server** (Spring Data JPA), documentação **OpenAPI/Swagger**, imagem **Docker** publicada no Docker Hub, entrega contínua com **GitHub Actions** e implantação no **Microsoft Azure**.

**Autor:** Marcus Lucas

## 🔗 Links de avaliação

| Item | URL |
|---|---|
| Repositório GitHub | `COLOCAR_URL_DO_REPOSITORIO` |
| Imagem no Docker Hub | `https://hub.docker.com/r/SEU_USUARIO/cp03-ms-livrarias` |
| Aplicação no Azure | `https://SEU_APP.azurewebsites.net` |
| Swagger UI (Azure) | `https://SEU_APP.azurewebsites.net/` |
| Swagger UI (local) | `http://localhost:8080/` |
| OpenAPI JSON | `/v3/api-docs` |

## 🛠️ Tecnologias

Java 17 · Spring Boot 4 (Web MVC, Data JPA, Validation) · SQL Server (`mssql-jdbc`) · springdoc-openapi · ModelMapper · Lombok · Maven · Docker · GitHub Actions · Azure

## 🗂️ Arquitetura em camadas

```text
src/main/java/com/github/marcuslucas/livraria
├── controller   # Endpoints REST (/api/v1/...)
├── service      # Regras de negócio
├── repository   # Spring Data JPA
├── model        # Entidades JPA (Livraria, Livro)
├── dto          # Requests/Responses e mappers
├── config       # Configuração do OpenAPI
└── exception    # Tratamento global de erros de validação (400)
```

Entidades:

- **Livraria** (tabela `livrarias`): `id`, `nome`, `cidade`, `especialidade`, `endereco`
- **Livro** (tabela `livros`): `id`, `titulo`, `anoPublicacao`, `genero`, `autor`

## 🗄️ 1. Configuração da conexão com o SQL Server

A conexão é configurada por variáveis de ambiente (nenhuma senha fica no código):

| Variável | Obrigatória | Descrição | Padrão |
|---|---|---|---|
| `DB_HOST` | Sim (em `prd`) | Host do SQL Server | `localhost` |
| `DB_PORT` | Não | Porta | `1433` |
| `DB_NAME` | Sim (em `prd`) | Nome do database | `api` |
| `DB_USER` | Sim (em `prd`) | Usuário | `sa` |
| `DB_PASSWORD` | **Sim** | Senha | *(sem padrão)* |
| `DB_ENCRYPT` | Não | Criptografia na conexão | `true` |
| `DB_TRUST_CERT` | Não | Confiar no certificado do servidor | `true` |
| `DDL_AUTO` | Não | `update` ou `validate` | `update` |
| `SPRING_PROFILES_ACTIVE` | Não | `default` ou `prd` | `default` |

> O SQL Server **não cria o database sozinho**: crie-o antes de subir a API (`CREATE DATABASE api;`). As **tabelas** são criadas pelo Hibernate (`ddl-auto=update`).

SQL Server local com Docker (opcional):

```bash
docker run --name sqlserver-livraria \
  -e "ACCEPT_EULA=Y" \
  -e "MSSQL_SA_PASSWORD=<SUA_SENHA>" \
  -p 1433:1433 \
  -d mcr.microsoft.com/mssql/server:2022-latest
```

## 🚀 2. Executando localmente

**Linux / macOS**

```bash
export DB_HOST=localhost DB_PORT=1433 DB_NAME=api DB_USER=sa DB_PASSWORD='<SUA_SENHA>'
./mvnw spring-boot:run
```

**Windows PowerShell**

```powershell
$env:DB_HOST="localhost"; $env:DB_PORT="1433"; $env:DB_NAME="api"
$env:DB_USER="sa"; $env:DB_PASSWORD="<SUA_SENHA>"
.\mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080` (Swagger UI na raiz).

## 🐳 3. Executando com Docker

Usando a imagem publicada no Docker Hub:

```bash
docker run --name livraria-app -p 8080:8080 \
  -e DB_HOST=host.docker.internal -e DB_PORT=1433 -e DB_NAME=api \
  -e DB_USER=sa -e DB_PASSWORD='<SUA_SENHA>' \
  SEU_USUARIO/cp03-ms-livrarias:latest
```

Ou construindo a imagem localmente:

```bash
docker build -t cp03-ms-livrarias .
```

> `host.docker.internal` permite acessar o SQL Server que roda na máquina host. Para banco remoto (ex.: Azure SQL), use o host dele em `DB_HOST`.

## ⚙️ 4. GitHub Actions (Continuous Delivery)

Workflow: `.github/workflows/docker-publish.yml`. A cada `push` na branch `main` (ou execução manual) ele:

1. faz checkout do código;
2. faz login no Docker Hub;
3. constrói a imagem a partir do `Dockerfile`;
4. publica `SEU_USUARIO/cp03-ms-livrarias` com as tags `latest` e o SHA do commit.

Secrets necessários em *Settings → Secrets and variables → Actions*:

| Secret | Conteúdo |
|---|---|
| `DOCKERHUB_USERNAME` | Usuário do Docker Hub |
| `DOCKERHUB_TOKEN` | Access Token do Docker Hub (Account Settings → Security) |

## ☁️ 5. Implantação no Azure

Aplicação implantada como **Azure App Service (Web App for Containers)** usando a imagem do Docker Hub, com banco **Azure SQL Database**.

Application settings configuradas no App Service:

| Nome | Valor |
|---|---|
| `SPRING_PROFILES_ACTIVE` | `prd` |
| `DB_HOST` | `<servidor>.database.windows.net` |
| `DB_NAME` | nome do database no Azure SQL |
| `DB_USER` / `DB_PASSWORD` | credenciais do Azure SQL |
| `WEBSITES_PORT` | `8080` |

No firewall do Azure SQL, habilite *Allow Azure services and resources to access this server*.

**URL da aplicação:** `https://SEU_APP.azurewebsites.net`

## 📖 6. Testando a API

Swagger UI: `/` · OpenAPI: `/v3/api-docs`

**Livrarias** — base `/api/v1/livrarias`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/api/v1/livrarias` | Lista todas | 200 |
| `GET` | `/api/v1/livrarias/{id}` | Busca por id | 200 / 404 |
| `POST` | `/api/v1/livrarias` | Cria | 201 / 400 |
| `PUT` | `/api/v1/livrarias/{id}` | Atualiza | 200 / 400 / 404 |
| `DELETE` | `/api/v1/livrarias/{id}` | Remove | 204 / 404 |

**Livros** — base `/api/v1/livros` (mesmas operações e respostas)

Exemplos:

```bash
curl -X POST http://localhost:8080/api/v1/livrarias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Livraria Cultura","cidade":"São Paulo","especialidade":"Generalista","endereco":"Av. Paulista, 2073"}'

curl http://localhost:8080/api/v1/livrarias

curl -X PUT http://localhost:8080/api/v1/livrarias/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Livraria Cultura SP","cidade":"São Paulo","especialidade":"Generalista","endereco":"Av. Paulista, 2073"}'

curl -X DELETE http://localhost:8080/api/v1/livrarias/1

curl -X POST http://localhost:8080/api/v1/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Dom Casmurro","anoPublicacao":1899,"genero":"Romance","autor":"Machado de Assis"}'
```
