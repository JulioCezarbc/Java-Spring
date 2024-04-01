Cadastro e Listagem de Produtos
Este projeto é um exemplo de uma aplicação web para cadastro e listagem de produtos, utilizando Spring Boot no backend e JavaScript no frontend.

Instalação e Configuração
Certifique-se de ter o Java JDK e o Maven instalados em sua máquina.

Configuração do Backend
O backend da aplicação utiliza o Spring Boot e possui a seguinte estrutura:

WebConfig: Configuração do CORS para permitir requisições de diferentes origens.
ProdutoControle: Controlador REST para operações relacionadas a produtos.
ProdutoService: Serviço para realizar operações de CRUD no banco de dados.
ProdutoRepositorio: Interface do Spring Data JPA para acesso ao banco de dados.
Produto: Entidade JPA que representa um produto.

Configuração do Frontend
O frontend utiliza JavaScript para interação com o usuário e possui os seguintes arquivos:

index.html: Página HTML principal com o formulário de cadastro e listagem de produtos.
style.css: Estilos CSS para a página.
script.js: Script JavaScript para interação com a API REST do backend.

Execução do Projeto
Execute o backend da aplicação:


mvn spring-boot:run
Abra o arquivo index.html em seu navegador para acessar a aplicação.

Funcionalidades
Cadastro de novos produtos com nome, descrição, valor e disponibilidade.
Listagem de produtos cadastrados.
Edição e exclusão de produtos existentes.

Tecnologias Utilizadas
Java 11
Spring Boot 2.x
JavaScript
HTML5
CSS3