# Projeto AWPAG

O Projeto AWPAG é uma aplicação Spring Boot que gerencia parcelamentos de clientes.

## Funcionalidades

- Listar todos os parcelamentos ou clientes
- Buscar um parcelamento ou clientes por ID
- Cadastrar um novo parcelamento ou cliente

## Tecnologias Utilizadas

- Java 11
- Spring Boot
- Spring Data JPA
- Spring Web
- Banco de dados : MySql
- Lombok
- ModelMapper

- Acesse a aplicação em http://localhost:8080
Uso
Para listar todos os parcelamentos: GET em http://localhost:8080/parcelamentos
Para buscar um parcelamento por ID: GET em http://localhost:8080/parcelamentos/{id}
Para cadastrar um novo parcelamento: POST em http://localhost:8080/parcelamentos com o corpo da requisição contendo os dados do parcelamento

Para listar todos os clientes: GET em http://localhost:8080/clientess
Para buscar um cliente por ID: GET em http://localhost:8080/clientes/{id}
Para cadastrar um novo cliente: POST em http://localhost:8080/clientes com o corpo da requisição contendo os dados do parcelamento
