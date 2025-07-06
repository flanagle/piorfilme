# Pior Filme API

## Sobre
API RESTful que lê um arquivo CSV com dados dos indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards e fornece estatísticas sobre os produtores com maior e menor intervalo entre prêmios consecutivos. A aplicação inicia populando a base com o CSV fornecido para o teste. É possível atualizar o arquivo antes de iniciar a aplicação ou atualizar a base após o início através de um endpoint.

## Tecnologias
- Java 24
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database (em memória)
- Maven

## Como executar

```bash
mvn clean install
mvn spring-boot:run
```

Acesse a aplicação em:
```
http://localhost:8080
```

Console do H2:
```
http://localhost:8080/h2-console
```

- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (deixe em branco)

## Endpoint para consulta

```http
GET /api/pior-filme
```

Retorna os produtores com o maior e menor intervalo entre vitórias consecutivas.

## Atualizar dados CSV

```http
POST /api/atualizar-base
```

Enviar novo arquivo CSV para recarregar os dados:

### Exemplo com cURL:

```bash
curl -X POST http://localhost:8080/api/atualizar-base \
     -H "Content-Type: multipart/form-data" \
     -F "file=@caminho/para/arquivo.csv"
```

## Executar testes

```bash
mvn test
```

Testes de integração validados para garantir a consistência dos dados retornados pela API.

## Requisitos atendidos
- Leitura e carga de dados a partir de CSV
- Banco em memória com H2
- API RESTful nível 2 (Richardson)
- Testes de integração