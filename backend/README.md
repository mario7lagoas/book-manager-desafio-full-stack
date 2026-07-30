# 📘 Book Manager — Backend (books)

## 1) Visão geral
Este módulo é o serviço backend (API REST) do gerenciador de livros — Desafio VGX. É uma aplicação Java Spring Boot que expõe endpoints para autenticação (JWT) e CRUD de livros, persiste dados em MySQL via JPA e aplica migrações com Flyway. A documentação OpenAPI/Swagger está incluída.

## 2) Tecnologias usadas
- Java 17
- Spring Boot 3.2.x (Web, Data JPA, Security)
- Maven (com Maven Wrapper: `mvnw` / `mvnw.cmd`)
- MySQL 8 (driver: mysql-connector-j)
- Flyway (migrações de banco)
- MapStruct, Lombok
- springdoc-openapi (Swagger UI)
- JWT (autenticação)

## 3) Pré-requisitos (versões recomendadas)
- Java JDK 17+
- Maven 3.6+ (preferir o Maven Wrapper incluso)
- MySQL 8.x (ou Docker + imagem oficial MySQL 8)
- (Opcional) Docker Engine, se quiser executar um container de banco local

## 4) Como instalar dependências
As dependências são gerenciadas por Maven. Usar o wrapper garante a mesma versão do Maven usada pelo projeto.

No Windows (PowerShell), a partir da raiz do repositório:

cd backend\books
.\mvnw.cmd -q clean package -DskipTests

O comando baixa dependências e gera o JAR em `target\`.

## 5) Passo a passo exato de execução no terminal (Windows - PowerShell)
Siga os comandos abaixo na ordem indicada.

1) Entrar na pasta do backend (a partir da raiz do repositório):

cd backend\books

2) (Opcional) Subir um MySQL local com Docker (exemplo):

docker run --name bookdb -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=bookdb -p 3306:3306 -d mysql:8.0

3) (Opcional) Exportar variáveis de ambiente (PowerShell) para alterar conexão com o banco:

$env:DB_HOST = "localhost"
$env:DB_PORT = "3306"
$env:DB_NAME = "bookdb"
$env:DB_USER = "root"
$env:DB_PASSWORD = "1234"

OBS: Valores acima coincidem com os defaults em `src/main/resources/application.yml` (password padrão `0381`).

4) Build (usar o Maven Wrapper):

.\mvnw.cmd -q clean package -DskipTests

5a) Executar o JAR gerado:

java -jar target\books-0.0.1-SNAPSHOT.jar

5b) Ou executar diretamente com Maven (modo de desenvolvimento):

.\mvnw.cmd spring-boot:run

6) Testes (opcional):

.\mvnw.cmd test

7) Acessos úteis após a aplicação subir:
- Base da API: http://localhost:8080/book-api/v1
- Swagger UI: http://localhost:8080/book-api/v1/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/book-api/v1/api-docs

## Observações importantes
- As migrações Flyway estão em `src/main/resources/db/migration/` e são aplicadas automaticamente ao iniciar a aplicação.
- Ajuste variáveis de ambiente para senhas e host em ambientes de produção; não use o segredo embutido no `application.yml`.
- Para ambientes UNIX/macOS, substituir `.\mvnw.cmd` por `./mvnw` e os separadores de caminho conforme necessário.

---
Arquivo atualizado com instruções de execução e pré-requisitos. Ajustar conforme políticas de segurança antes de deploy em produção.
