# CandyShop

## Sistema de Gerenciamento de Clientes, Produtos e Compras
Este projeto consiste em um sistema web para gerenciamento de clientes, produtos e compras. Seu propósito é simplificar o processo de gerenciamento proporcionando uma experiência intuitiva para os administradores da loja.
Ele é dividido em duas partes principais: o backend, que fornece a API REST, e o frontend, que é a interface web para interação com o sistema.

## O sistema cria um usuário admin padrão na primeira execução, com as credenciais:

Username: admin
Password: 123g

## Tecnologias Utilizadas:

## Backend:
Spring Boot: Framework para construção do aplicativo Java.
Spring Security: Para autenticação e autorização.
JWT: Para gerenciamento de tokens de autenticação.
Maven: Gerenciamento de dependências e construção do projeto.
Banco de Dados H2: Banco de dados em memória para desenvolvimento e testes.

## Frontend:
HTML/CSS: Estrutura e estilização das páginas web.
JavaScript: Lógica de interação com a API e manipulação do DOM.
Fetch API: Para requisições HTTP assíncronas.
Endpoints da API

## Autenticação
POST /login: Autentica um usuário e retorna um token JWT.

## Clientes
GET /client: Lista todos os clientes.
POST /client: Adiciona um novo cliente.
PUT /client/{id}: Atualiza um cliente existente.
DELETE /client/{id}: Exclui um cliente.

## Produtos
GET /product: Lista todos os produtos.
POST /product: Adiciona um novo produto.
PUT /product/{id}: Atualiza um produto existente.
DELETE /product/{id}: Exclui um produto.

## Compras
POST /purchase: Adiciona uma nova compra.
GET /purchase/client/{clientId}: Lista todas as compras de um cliente.
