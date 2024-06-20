#Gerenciamento de Produtos - README
Este projeto é um sistema de gerenciamento de produtos desenvolvido com Spring Boot (Java) para o back-end e HTML, CSS e JavaScript para o front-end. Ele permite adicionar produtos, listar produtos existentes e exibir esses produtos em uma tabela paginada.

##Configurações
Certifique-se de ter o JDK (Java Development Kit) e o Maven instalados em seu sistema.
Clone este repositório em sua máquina local.
Executando o Back-end
Abra o projeto em sua IDE (Eclipse, IntelliJ, etc.).
Execute a classe principal OakApplication para iniciar o servidor.
O servidor estará em execução em http://localhost:8080.
Banco de Dados
O projeto usa um banco de dados H2 em memória por padrão. Você pode configurar o banco de dados em application.properties se preferir usar outro banco de dados (como MySQL, PostgreSQL, etc.).

##Executando o Front-end
Abra o arquivo index.html em um navegador da web para acessar a interface do usuário.
Você pode adicionar novos produtos usando o formulário na página.
A lista de produtos existentes será exibida na tabela abaixo do formulário.

##Funcionalidades
Adicionar produtos com nome, descrição, preço e disponibilidade.
Listar produtos existentes em uma tabela paginada.
Ordenar produtos por preço em ordem crescente ou decrescente.
Navegar entre as páginas da tabela de produtos.

##Estrutura do Projeto
src/main/java/com/julio/Oak: Contém os arquivos Java para o back-end.
config: Configurações do Spring Boot.
controller: Controladores REST para lidar com requisições HTTP.
domain: Classes de domínio (DTOs e entidades).
repository: Repositório JPA para interagir com o banco de dados.
service: Lógica de negócio e serviços.
src/main/resources: Recursos estáticos, arquivos de propriedades e templates.

##Dependências
Spring Boot
Spring Data JPA
H2 Database
HTML, CSS, JavaScript

##Este README fornece uma visão geral do projeto, instruções básicas de configuração e execução,
##além de detalhes sobre a estrutura do projeto e suas funcionalidades principais. Personalize-o conforme necessário para atender às especificidades do seu projeto
